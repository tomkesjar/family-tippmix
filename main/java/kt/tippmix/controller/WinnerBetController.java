package kt.tippmix.controller;

import kt.tippmix.model.Nation;
import kt.tippmix.model.User;
import kt.tippmix.model.WinnerBet;
import kt.tippmix.repository.UserRepository;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/winnerbet")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*") // React dev server
public class WinnerBetController {
    private final UserRepository userRepository;

    public WinnerBetController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<Pair<String, String>> betForWinnerAndTopGoalScorer(
            Authentication authentication, @RequestBody WinnerBet winnerBet) {
        User user = (User) authentication.getPrincipal();
        String winnerNation = winnerBet.getWinner();
        String topGoalScorerNation = winnerBet.getGoal();

        try {
            if (user.getFavouriteNation() == null) {
                user.setFavouriteNation(winnerNation);
                userRepository.save(user);
            } else {
                winnerNation = user.getFavouriteNation();
            }

            if (user.getGoalScorerNationality() == null) {
                user.setGoalScorerNationality(topGoalScorerNation);
                userRepository.save(user);
            } else {
                topGoalScorerNation = user.getGoalScorerNationality();
            }
            return ResponseEntity.ok().body(Pair.of(winnerNation, topGoalScorerNation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Pair.of("Error message: ", e.getMessage()));
        }


    }
}
