package kt.tippmix.controller;

import kt.tippmix.model.*;
import kt.tippmix.repository.UserRepository;
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
    public ResponseEntity<WinnerBetResponse> betForWinnerAndTopGoalScorer(
            Authentication authentication, @RequestBody WinnerBet winnerBet) {
        User user = (User) authentication.getPrincipal();

        if (user.getFavouriteNation() != null && user.getGoalScorerNationality() != null && user.getMostGoals() != null) {
            WinnerBetResponse response = new WinnerBetResponse(WinnerBetStatus.ERROR,"User already selected both winner country and top goal scorer country", user.getFavouriteNation(), user.getGoalScorerNationality(), user.getMostGoals());
            return ResponseEntity.ok().body(response);
        }

        String winnerNation = winnerBet.getWinner();
        String topGoalScorerNation = winnerBet.getGoal();
        String mostGoals = winnerBet.getMostgoal();

        WinnerBetStatus status = user.getFavouriteNation() == null && user.getGoalScorerNationality() == null
                ? WinnerBetStatus.OK : WinnerBetStatus.PARTIAL;
        String errorMessage = user.getFavouriteNation() == null && user.getGoalScorerNationality() == null
                ? "" : "Partially set - one of them was already selected beforehand";

        try {
            if (user.getFavouriteNation() == null) {
                user.setFavouriteNation(Nation.valueOf(winnerNation).toString());
                userRepository.save(user);
            } else {
                winnerNation = user.getFavouriteNation();
            }

            if (user.getGoalScorerNationality() == null) {
                user.setGoalScorerNationality(Nation.valueOf(topGoalScorerNation).toString());
                userRepository.save(user);
            } else {
                topGoalScorerNation = user.getGoalScorerNationality();
            }

            if (user.getMostGoals() == null) {
                user.setMostGoals(mostGoals);
                userRepository.save(user);
            } else {
                mostGoals = user.getMostGoals();
            }
            WinnerBetResponse response = new WinnerBetResponse(status,errorMessage, winnerNation, topGoalScorerNation, mostGoals);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            WinnerBetResponse failResponse = new WinnerBetResponse(WinnerBetStatus.ERROR,"Error message: " + e.getMessage(), user.getFavouriteNation(), user.getGoalScorerNationality(), user.getMostGoals());
            return ResponseEntity.badRequest().body(failResponse);
        }


    }
}
