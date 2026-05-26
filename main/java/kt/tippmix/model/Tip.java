package kt.tippmix.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tip")
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="gamedate")
    private LocalDateTime gameDate;
    @Column(name="username")
    private String username;
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

}
