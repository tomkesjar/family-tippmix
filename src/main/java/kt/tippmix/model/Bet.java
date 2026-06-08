package kt.tippmix.model;

import jakarta.persistence.*;

@Entity
@Table(name="bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="match_id")
    private long matchId;
    @Column(name="player_id")
    private long playerId;
    @Column(name="homegoals")
    private Integer homeGoals;
    @Column(name="awaygoals")
    private Integer awayGoals;
    @Column(name="winner")
    private Integer winner; // 1-0-2
    @Column(name="point")
    private Integer point;
    @Column(name="exact")
    private Boolean exact;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Boolean isExact() {
        return exact;
    }

    public void setExact(Boolean exact) {
        this.exact = exact;
    }

    public static Bet createBetFromMatchBet(MatchBet matchBet) {
        Bet result = new Bet();
        result.setMatchId(matchBet.getMatchId());
        result.setHomeGoals(matchBet.getHomeGoals());
        result.setAwayGoals(matchBet.getAwayGoals());
        result.setWinner(matchBet.getWinner());
        return result;
    }
}
