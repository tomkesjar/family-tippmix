package kt.tippmix.model;

import java.time.LocalDateTime;

/**
 * Read-only DTO returned by GET /api/tip/user.
 * Joins Bet with Game so a single response carries both the user's prediction
 * and the actual match result (nullable if the game hasn't been played yet).
 */
public class BetHistoryRow {

    private Long matchId;
    private LocalDateTime gameDate;
    private String homeTeam;
    private String awayTeam;

    /** Actual result — null when the match hasn't been played yet. */
    private Integer gameHomeGoals;
    private Integer gameAwayGoals;
    private Integer gameWinner; // 1 = home, 2 = away

    /** User's prediction. */
    private Integer betHomeGoals;
    private Integer betAwayGoals;
    private Integer winner; // 1 = home, 2 = away
    private Integer point;

    /** Whether this is a knockout game (qualifier winner is relevant). */
    private Boolean knockout;

    public BetHistoryRow(Long matchId, LocalDateTime gameDate,
                         String homeTeam, String awayTeam,
                         Integer gameHomeGoals, Integer gameAwayGoals, Integer gameWinner,
                         Integer betHomeGoals, Integer betAwayGoals, Integer winner, Integer point,
                         Boolean knockout) {
        this.matchId      = matchId;
        this.gameDate     = gameDate;
        this.homeTeam     = homeTeam;
        this.awayTeam     = awayTeam;
        this.gameHomeGoals = gameHomeGoals != null ? gameHomeGoals : 0;
        this.gameAwayGoals = gameAwayGoals != null ? gameAwayGoals : 0;
        this.gameWinner    = gameWinner;
        this.betHomeGoals  = betHomeGoals != null ? betHomeGoals : 0;
        this.betAwayGoals  = betAwayGoals != null ? betAwayGoals : 0;
        this.winner        = winner != null ? winner : 0;
        this.point         = point != null ? point : 0;
        this.knockout      = Boolean.TRUE.equals(knockout);
    }

    public long getMatchId()          { return matchId; }
    public LocalDateTime getGameDate() { return gameDate; }
    public String getHomeTeam()        { return homeTeam; }
    public String getAwayTeam()        { return awayTeam; }
    public Integer getGameHomeGoals()  { return gameHomeGoals; }
    public Integer getGameAwayGoals()  { return gameAwayGoals; }
    public Integer getGameWinner()     { return gameWinner; }
    public int getBetHomeGoals()       { return betHomeGoals; }
    public int getBetAwayGoals()       { return betAwayGoals; }
    public int getWinner()             { return winner; }
    public int getPoint()              { return point; }
    public boolean isKnockout()        { return knockout; }
}
