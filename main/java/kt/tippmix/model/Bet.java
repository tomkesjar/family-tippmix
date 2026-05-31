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
    private int homeGoals;
    @Column(name="awaygoals")
    private int awayGoals;
    @Column(name="winner")
    private int winner; // 1-0-2
    @Column(name="point")
    private int point;



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

    public static Bet createBetFromMatchBet(MatchBet matchBet) {
        Bet result = new Bet();
        result.setMatchId(matchBet.getMatchId());
        result.setHomeGoals(matchBet.getHomeGoals());
        result.setAwayGoals(matchBet.getAwayGoals());
        result.setWinner(matchBet.getWinner());
        return result;
    }
}
