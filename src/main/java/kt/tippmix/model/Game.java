package kt.tippmix.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="gamedate")
    private LocalDateTime gameDate;

    @Column(name="hometeam")
    private String homeTeam;
    @Column(name="awayteam")
    private String awayTeam;
    @Column(name="homegoals")
    private Integer homeGoals;
    @Column(name="awaygoals")
    private Integer awayGoals;
    @Column(name="winner")
    private Integer winner; // 1-0-2

    @Column(name="knockout")
    private Boolean knockout;


    public Game() {
    }

    public Game(Long id, LocalDateTime gameDate, String homeTeam, String awayTeam) {
        this.id = id;
        this.gameDate = gameDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Game(Long id, LocalDateTime gameDate, String homeTeam, String awayTeam, Integer homeGoals, Integer awayGoals, Integer winner, boolean knockout) {
        this.id = id;
        this.gameDate = gameDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.winner = winner;
        this.knockout = knockout;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
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

    public Boolean isKnockout() {
        return knockout;
    }

    public void setKnockout(Boolean knockout) {
        this.knockout = knockout;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", matchDate=" + gameDate +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", winner=" + winner +
                '}';
    }
}
