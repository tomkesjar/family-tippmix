package kt.tippmix.controller;


import kt.tippmix.model.Bet;
import kt.tippmix.model.BetHistoryRow;
import kt.tippmix.model.MatchBet;
import kt.tippmix.model.Tip;
import kt.tippmix.model.User;
import kt.tippmix.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kt.tippmix.model.Bet.createBetFromMatchBet;

@RestController
@RequestMapping("/api/tip")
@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class BetController {

    private BetService betService;

    public BetController(@Autowired BetService betService) {
        this.betService = betService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tip>> getAll() {
        List<Tip> tipList = (List) betService.getAll();
        return ResponseEntity.ok().body(tipList);
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


    @PostMapping("/save")
    public ResponseEntity<?> save(Authentication authentication, @RequestBody List<MatchBet> matchBets) {
        User user = (User) authentication.getPrincipal();
        try {
            matchBets.forEach(bet -> {
                Bet newBet = createBetFromMatchBet(bet);
                newBet.setPlayerId(user.getId());
                betService.clearPreviousBets(newBet);
                Bet saveResponse = betService.save(newBet);
            });
            return ResponseEntity.ok("Saved " + matchBets.size() + " bets");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not save bets, please try again");
        }
    }
}
