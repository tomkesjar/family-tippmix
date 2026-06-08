package kt.tippmix.model;

import java.time.LocalDateTime;

/**
 * Read-only DTO returned by GET /api/tip/all.
 * Joins Bet + Game + User (secretName, secretFileName) in a single response row.
 * Only bets where both point and exact have been evaluated (not null) are included.
 */
public class AllBetsRow {

    // --- Game fields ---
    private LocalDateTime gameDate;
    private String homeTeam;
    private String awayTeam;
    private Integer gameHomeGoals;
    private Integer gameAwayGoals;
    private Integer gameWinner;
    private Boolean knockout;

    // --- Bet fields ---
    private long   betId;
    private long   matchId;
    private long   playerId;
    private Integer betHomeGoals;
    private Integer betAwayGoals;
    private Integer betWinner;
    private Integer point;
    private Boolean exact;

    // --- Player fields ---
    private String secretName;
    private String secretFileName;

    public AllBetsRow(LocalDateTime gameDate, String homeTeam, String awayTeam,
                      Integer gameHomeGoals, Integer gameAwayGoals, Integer gameWinner,
                      Boolean knockout,
                      long betId, long matchId, long playerId,
                      Integer betHomeGoals, Integer betAwayGoals, Integer betWinner,
                      Integer point, Boolean exact,
                      String secretName, String secretFileName) {
        this.gameDate      = gameDate;
        this.homeTeam      = homeTeam;
        this.awayTeam      = awayTeam;
        this.gameHomeGoals = gameHomeGoals;
        this.gameAwayGoals = gameAwayGoals;
        this.gameWinner    = gameWinner;
        this.knockout      = Boolean.TRUE.equals(knockout);
        this.betId         = betId;
        this.matchId       = matchId;
        this.playerId      = playerId;
        this.betHomeGoals  = betHomeGoals;
        this.betAwayGoals  = betAwayGoals;
        this.betWinner     = betWinner;
        this.point         = point;
        this.exact         = exact;
        this.secretName     = secretName;
        this.secretFileName = secretFileName;
    }

    public LocalDateTime getGameDate()      { return gameDate; }
    public String        getHomeTeam()      { return homeTeam; }
    public String        getAwayTeam()      { return awayTeam; }
    public Integer       getGameHomeGoals() { return gameHomeGoals; }
    public Integer       getGameAwayGoals() { return gameAwayGoals; }
    public Integer       getGameWinner()    { return gameWinner; }
    public boolean       isKnockout()       { return knockout; }
    public long          getBetId()         { return betId; }
    public long          getMatchId()       { return matchId; }
    public long          getPlayerId()      { return playerId; }
    public Integer       getBetHomeGoals()  { return betHomeGoals; }
    public Integer       getBetAwayGoals()  { return betAwayGoals; }
    public Integer       getBetWinner()     { return betWinner; }
    public Integer       getPoint()         { return point; }
    public Boolean       getExact()         { return exact; }
    public String        getSecretName()    { return secretName; }
    public String        getSecretFileName(){ return secretFileName; }
}
