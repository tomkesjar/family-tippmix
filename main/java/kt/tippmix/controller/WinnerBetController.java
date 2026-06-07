package kt.tippmix.controller;

import kt.tippmix.model.*;
import kt.tippmix.repository.UserRepository;
import kt.tippmix.service.PointCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/winnerbet")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class WinnerBetController {

    private static final DateTimeFormatter DEADLINE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    private static final String DEADLINE_PASSED_MSG =
            "A határidő lejárt, már nem változtathatsz a tippjeiden";

    private final UserRepository  userRepository;
    private final PointCalculator pointCalculator;

    @Value("${WINNER_BET_MODIFICATION_DATE_UNTIL}")
    private String modificationDeadline;

    public WinnerBetController(UserRepository userRepository, PointCalculator pointCalculator) {
        this.userRepository  = userRepository;
        this.pointCalculator = pointCalculator;
    }

    /** Returns the currently saved winner-bet values for the authenticated user. */
    @GetMapping
    public ResponseEntity<WinnerBetResponse> getCurrentBet(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new WinnerBetResponse(
                WinnerBetStatus.OK, "",
                user.getFavouriteNation(),
                user.getGoalScorerNationality(),
                user.getMostGoals()
        ));
    }

    /** Saves (or overwrites) the winner-bet values if the modification deadline has not passed. */
    @PostMapping
    public ResponseEntity<WinnerBetResponse> betForWinnerAndTopGoalScorer(
            Authentication authentication, @RequestBody WinnerBet winnerBet) {

        User user = (User) authentication.getPrincipal();

        // Deadline check
        LocalDateTime deadline = LocalDateTime.parse(modificationDeadline, DEADLINE_FMT);
        if (LocalDateTime.now().isAfter(deadline)) {
            return ResponseEntity.ok(new WinnerBetResponse(
                    WinnerBetStatus.ERROR, DEADLINE_PASSED_MSG,
                    user.getFavouriteNation(),
                    user.getGoalScorerNationality(),
                    user.getMostGoals()
            ));
        }

        try {
            user.setFavouriteNation(Nation.valueOf(winnerBet.getWinner()).toString());
            user.setGoalScorerNationality(Nation.valueOf(winnerBet.getGoal()).toString());
            user.setMostGoals(MostGoals.valueOf(winnerBet.getMostgoal()).toString());
            userRepository.save(user);

            return ResponseEntity.ok(new WinnerBetResponse(
                    WinnerBetStatus.OK, "",
                    user.getFavouriteNation(),
                    user.getGoalScorerNationality(),
                    user.getMostGoals()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new WinnerBetResponse(
                    WinnerBetStatus.ERROR, "Hiba történt: " + e.getMessage(),
                    user.getFavouriteNation(),
                    user.getGoalScorerNationality(),
                    user.getMostGoals()
            ));
        }
    }

    @PostMapping("/final")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WinnerBetResponse> submitFinalResults(
            @RequestBody WinnerBet winnerBet) {
        try {
            pointCalculator.updateWinnerBetPoints(winnerBet);
            return ResponseEntity.ok(new WinnerBetResponse(
                    WinnerBetStatus.OK, "",
                    winnerBet.getWinner(),
                    winnerBet.getGoal(),
                    winnerBet.getMostgoal()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new WinnerBetResponse(
                    WinnerBetStatus.ERROR, "Hiba történt: " + e.getMessage(),
                    null, null, null
            ));
        }
    }

    @PostMapping("/bonus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WinnerBetResponse> submitBonusValues(@RequestBody BonusPointRequest request) {
        try {
            pointCalculator.addBonusPoint(request.secretName(), request.bonusPoint());
            return ResponseEntity.ok(new WinnerBetResponse(WinnerBetStatus.OK, "", null, null, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new WinnerBetResponse(WinnerBetStatus.ERROR, "Hiba: " + e.getMessage(), null, null, null));
        }
    }
}
