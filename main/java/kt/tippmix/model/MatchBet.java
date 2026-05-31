package kt.tippmix.model;

public class MatchBet {
    private Long matchId;
    private int homeGoals;
    private int awayGoals;
    private int winner;

    public MatchBet() {
    }

    public MatchBet(Long matchId, int homeGoals, int awayGoals, int winner) {
        this.matchId = matchId;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.winner = winner;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
