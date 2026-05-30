package kt.tippmix.model;

public class MatchBet {
    private Long id;
    private int homeGoals;
    private int awayGoals;

    public MatchBet() {
    }

    public MatchBet(Long id, int homeGoals, int awayGoals) {
        this.id = id;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
