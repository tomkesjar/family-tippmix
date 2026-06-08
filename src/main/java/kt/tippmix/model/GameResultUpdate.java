package kt.tippmix.model;

/**
 * Payload for POST /api/game/gameresults.
 * Only carries the result fields — the existing game record is fetched by gameId
 * so that date/team names are never accidentally overwritten.
 */
public class GameResultUpdate {

    private Long    gameId;
    private Integer homeGoals;
    private Integer awayGoals;
    private Integer winner; // 1 = home, 0 = draw, 2 = away

    public GameResultUpdate() {}

    public Long    getGameId()    { return gameId; }
    public Integer getHomeGoals() { return homeGoals; }
    public Integer getAwayGoals() { return awayGoals; }
    public Integer getWinner()    { return winner; }

    public void setGameId(Long gameId)       { this.gameId    = gameId; }
    public void setHomeGoals(Integer v)      { this.homeGoals = v; }
    public void setAwayGoals(Integer v)      { this.awayGoals = v; }
    public void setWinner(Integer v)         { this.winner    = v; }
}
