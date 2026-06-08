package kt.tippmix.service;

import kt.tippmix.model.*;
import kt.tippmix.repository.PointRepository;
import kt.tippmix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointCalculator {


    private BetService betService;
    private GameService gameService;

    private PointRepository pointRepository;
    private UserRepository userRepository;

    @Value("${FAVOURITE_MILTIPLIER:2}")
    private int FAVOURITE_TEAM_MULTIPLIER;

    @Value("${MATCH_EXACT_HIT:3}")
    private int exact;
    @Value("${MATCH_GOAL_DIFF:2}")
    private int goalDiff;
    @Value("${MATCH_SIMPLE_WIN:1}")
    private int simpleWinner;

    @Value("${MATCH_KNOCKOUT_WINNER:1}")
    private int knockoutWinner;


    @Value("${WINNER_NATION_POINT:5}")
    private int winnerNationPoint;
    @Value("${GOALSCORER_POINT:3}")
    private int goalScorerPoint;
    @Value("${MOSTGOAL_POINT:3}")
    private int mostGoalPoint;

    public PointCalculator(BetService betService, PointRepository pointRepository, UserRepository userRepository) {
        this.betService = betService;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    //This is used in AuthService when a new player is registered
    public void initializePointEntry(User user) {
        Point newPointEntry = new Point();
        newPointEntry.setPlayerId(user.getId());
        pointRepository.save(newPointEntry);
    }

    private void saveMatchPoint(User actualPlayer, Pair<Boolean, Integer> matchPoint) {
        Point currentPoints = pointRepository.findByUser(actualPlayer.getId()).orElseThrow();
        int delta = matchPoint.getSecond();
        int newMatchPoint = currentPoints.getMatchPoint() + delta;
        int newTotalPoint = currentPoints.getTotal() + delta;
        currentPoints.setMatchPoint(newMatchPoint);
        currentPoints.setTotal(newTotalPoint);
        if (matchPoint.getFirst()) {
            currentPoints.setExactMatch(currentPoints.getExactMatch() + 1);
        }
        Point save = pointRepository.save(currentPoints);

    }

    //Used in GameController when actual results are updated
    public void updatePoints(List<Game> games) {
        List<User> users = ((List) userRepository.findAll());
        users = users.stream().filter(e -> e.getRole() == User.Role.PLAYER).toList();

        for (Game game : games) {
            List<Bet> allBets = betService.getAllByGameId(game.getId());
            for (Bet currentBet : allBets) {
                User actualPlayer = users.stream().filter(e -> e.getId() == currentBet.getPlayerId()).findAny().orElseThrow();
                //TODO KT fallback to weakest team if not set
                Nation favouriteNation = actualPlayer.getFavouriteNation() != null ? Nation.valueOf(actualPlayer.getFavouriteNation()) : Nation.UZBEGISTAN;
                Pair<Boolean, Integer> pointEarned = calculatePoint(currentBet, game, favouriteNation);
                Pair<Boolean, Integer> newPointEarned = Pair.of(pointEarned.getFirst(), pointEarned.getSecond());

                // final validation to not calculate an update twice (only add difference if it was already considered earlier)
                if (currentBet.getPoint() != null && currentBet.getPoint() != 0) {
                    newPointEarned = Pair.of(pointEarned.getFirst(), pointEarned.getSecond() - currentBet.getPoint());
                }
                if (currentBet.isExact() != null && currentBet.isExact()) {
                    newPointEarned = Pair.of(false, pointEarned.getSecond() - currentBet.getPoint());
                }
                currentBet.setPoint(pointEarned.getSecond());
                currentBet.setExact(pointEarned.getFirst());
                betService.save(currentBet);
                saveMatchPoint(actualPlayer, newPointEarned);
            }
        }
    }

    private boolean isWinnerBet(Bet bet, Game game) {
        int betGoalDiff = bet.getHomeGoals() - bet.getAwayGoals();
        int actualGoalDiff = game.getHomeGoals() - game.getAwayGoals();
        boolean homeWin = betGoalDiff > 0 && actualGoalDiff > 0;
        boolean awayWin = betGoalDiff < 0 && actualGoalDiff < 0;
        boolean tie = betGoalDiff == 0 && actualGoalDiff == 0;
        return homeWin || awayWin || tie;
    }

    private Pair<Boolean, Integer> calculatePoint(Bet bet, Game game, Nation favouriteNation) {
        int result = 0;
        boolean isExact = false;
        // exact match
        if (bet.getHomeGoals() == game.getHomeGoals() && bet.getAwayGoals() == game.getAwayGoals()) {
            result = exact;
            isExact = true;
        // goal diff
        } else if ((bet.getHomeGoals() - bet.getAwayGoals()) == (game.getHomeGoals() - game.getAwayGoals())) {
            result = goalDiff;
        // simple winner
        } else if (isWinnerBet(bet, game)) {
            result = simpleWinner;
        }

        // This only applies to the knockout phase
        if (game.isKnockout()) {
            if (game.getWinner() == bet.getWinner()) {
                result += knockoutWinner;
            }
        }

        if (Nation.valueOf(game.getHomeTeam().toUpperCase()) == favouriteNation || Nation.valueOf(game.getAwayTeam().toUpperCase()) == favouriteNation) {
            result *= FAVOURITE_TEAM_MULTIPLIER;
        }

        return Pair.of(isExact, result);
    }

    /**
     * Awards points to every PLAYER whose winner-bet predictions match the
     * final results submitted by an admin.
     *
     * Matching rules:
     *   favouriteNation   == winnerBet.winner   → winnerPoint   += WINNER_NATION_POINT
     *   goalScorerNation  == winnerBet.goal     → goalPoint     += GOALSCORER_POINT
     *   mostGoals         == winnerBet.mostgoal → mostGoalPoint += MOSTGOAL_POINT
     *
     * Each matched category also increments the player's total.
     */
    public void updateWinnerBetPoints(WinnerBet winnerBet) {
        List<User> players = ((List<User>) userRepository.findAll()).stream()
                .filter(u -> u.getRole() == User.Role.PLAYER)
                .toList();

        for (User player : players) {
            Point point = pointRepository.findByUser(player.getId()).orElseThrow();
            boolean changed = false;

            if (winnerBet.getWinner() != null
                    && winnerBet.getWinner().equals(player.getFavouriteNation())) {
                point.setWinnerPoint(safe(point.getWinnerPoint()) + winnerNationPoint);
                point.setTotal(safe(point.getTotal()) + winnerNationPoint);
                changed = true;
            }

            if (winnerBet.getGoal() != null
                    && winnerBet.getGoal().equals(player.getGoalScorerNationality())) {
                point.setGoalPoint(safe(point.getGoalPoint()) + goalScorerPoint);
                point.setTotal(safe(point.getTotal()) + goalScorerPoint);
                changed = true;
            }

            if (winnerBet.getMostgoal() != null
                    && winnerBet.getMostgoal().equals(player.getMostGoals())) {
                point.setMostGoalPoint(safe(point.getMostGoalPoint()) + mostGoalPoint);
                point.setTotal(safe(point.getTotal()) + mostGoalPoint);
                changed = true;
            }

            if (changed) {
                pointRepository.save(point);
            }
        }
    }

    /**
     * Adds a bonus point amount to the player identified by email.
     * Both bonusPoint and total are incremented.
     */
    public void addBonusPoint(String email, int points) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Játékos nem található: " + email));
        Point point = pointRepository.findByUser(user.getId()).orElseThrow();
        point.setBonusPoint(safe(point.getBonusPoint()) + points);
        point.setTotal(safe(point.getTotal()) + points);
        pointRepository.save(point);
    }

    /** Null-safe unboxing for nullable Integer point fields. */
    private static int safe(Integer value) {
        return value == null ? 0 : value;
    }

    public List<RankingResponse> showPointsInRanking() {
        return pointRepository.getRanking(User.Role.PLAYER);
    }
}
