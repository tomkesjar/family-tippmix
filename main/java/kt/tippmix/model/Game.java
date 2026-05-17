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
    private int homeGoals;
    @Column(name="awaygoals")
    private int awayGoals;
    @Column(name="winner")
    private int winner; // 1-0-2


    public Game() {
    }

    public Game(Long id, LocalDateTime gameDate, String homeTeam, String awayTeam) {
        this.id = id;
        this.gameDate = gameDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Game(Long id, LocalDateTime gameDate, String homeTeam, String awayTeam, int homeGoals, int awayGoals, int winner) {
        this.id = id;
        this.gameDate = gameDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.winner = winner;
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
