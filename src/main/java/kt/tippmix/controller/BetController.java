package kt.tippmix.controller;


import kt.tippmix.model.*;
import kt.tippmix.service.BetService;
import kt.tippmix.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;

import static kt.tippmix.model.Bet.createBetFromMatchBet;

@RestController
@RequestMapping("/api/tip")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class BetController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    private static final String OVER_DEADLINE_MSG = "A módositási határidő lejárt";
    private BetService betService;
    private GameService gameService;
    private LocalDateTime modificationDeadline;

    public BetController(@Autowired BetService betService, GameService gameService,
    @Value("${GROUP_MATCH_BET_SUBMISSION_DEADLINE}") String modificationDeadline) {
        this.betService = betService;
        this.gameService = gameService;
        this.modificationDeadline = LocalDateTime.parse(modificationDeadline, DATE_TIME_FORMATTER);;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GameBetsGroup>> getAll() {
        Map<GameInfo, Map<PlayerInfo, BetInfo>> grouped = betService.getAllEvaluated();

        List<GameBetsGroup> response = grouped.entrySet().stream()
                .map(gameEntry -> {
                    List<PlayerBetPair> playerBets = gameEntry.getValue().entrySet().stream()
                            .map(playerEntry -> new PlayerBetPair(playerEntry.getKey(), playerEntry.getValue()))
                            .toList();
                    return new GameBetsGroup(gameEntry.getKey(), playerBets);
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<List<BetHistoryRow>> getAllForUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<BetHistoryRow> history = betService.getHistoryByPlayerId(user.getId());
        return ResponseEntity.ok(history);
    }

//    @GetMapping("/fromdate")
//    public ResponseEntity<List<Tip>> getByDate(@RequestParam String date) {
//        List<Tip> upcomingGames = betService.getByDate(date);
//        return ResponseEntity.ok().body(upcomingGames);
//    }

//    @GetMapping("/user")
//    public ResponseEntity<List<Tip>> getByUser(@RequestParam String user) {
//        List<Tip> upcomingGames = betService.getByUser(user);
//        return ResponseEntity.ok().body(upcomingGames);
//    }


    /** Admin endpoint: insert or update a single bet by playerId + matchId. */
    @PostMapping("/modify")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> modifyBet(@RequestBody ModifyBetRequest req) {
        betService.upsertBet(req.matchId(), req.playerId(), req.homeGoals(), req.awayGoals(), req.winner());
        return ResponseEntity.ok("Tipp módosítva");
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(Authentication authentication, @RequestBody List<MatchBet> matchBets) {
        User user = (User) authentication.getPrincipal();
        try {
            matchBets.forEach(bet -> {
                Bet newBet = createBetFromMatchBet(bet);
                Game game = gameService.getById(newBet.getMatchId()).orElseThrow(() -> new IllegalArgumentException("No game id set"));
//                LocalDateTime deadlineDate = game.getGameDate().minusDays(1);
                LocalDateTime deadlineDate = game.getGameDate().minusHours(1);
                if (!game.isKnockout()) {
                    deadlineDate = game.getGameDate().isBefore(modificationDeadline) ? game.getGameDate() : modificationDeadline;
                }
                if (LocalDateTime.now().isAfter(deadlineDate)) {
                    throw new IllegalStateException(OVER_DEADLINE_MSG);
                }
                newBet.setPlayerId(user.getId());
                betService.clearPreviousBets(newBet);
                Bet saveResponse = betService.save(newBet);
            });
            return ResponseEntity.ok("Saved " + matchBets.size() + " bets");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not save bets: " + e.getMessage());
        }
    }
}
