package kt.tippmix.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="player_id")
    private long playerId;

    @Column(name="match")
    private Integer matchPoint;
    @Column(name="winner")
    private Integer winnerPoint;
    @Column(name="goal")
    private Integer goalPoint;
    @Column(name="mostgoal")
    private Integer mostGoalPoint;
    @Column(name="bonus")
    private Integer bonusPoint;
    @Column(name="total")
    private Integer total;
    @Column(name="exact")
    private Integer exactMatch;

    public Point(long id, long playerId, Integer matchPoint, Integer winnerPoint, Integer goalPoint, Integer mostGoalPoint, Integer bonusPoint, Integer total, Integer exactMatch) {
        this.id = id;
        this.playerId = playerId;
        this.matchPoint = matchPoint;
        this.winnerPoint = winnerPoint;
        this.goalPoint = goalPoint;
        this.mostGoalPoint = mostGoalPoint;
        this.bonusPoint = bonusPoint;
        this.total = total;
        this.exactMatch = exactMatch;
    }

    public Point() {
        this.matchPoint = 0;
        this.winnerPoint = 0;
        this.goalPoint = 0;
        this.mostGoalPoint = 0;
        this.bonusPoint = 0;
        this.total = 0;
        this.exactMatch = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Integer getMatchPoint() {
        return matchPoint;
    }

    public void setMatchPoint(Integer matchPoint) {
        this.matchPoint = matchPoint;
    }

    public Integer getWinnerPoint() {
        return winnerPoint;
    }

    public void setWinnerPoint(Integer winnerPoint) {
        this.winnerPoint = winnerPoint;
    }

    public Integer getGoalPoint() {
        return goalPoint;
    }

    public void setGoalPoint(Integer goalPoint) {
        this.goalPoint = goalPoint;
    }

    public Integer getMostGoalPoint() {
        return mostGoalPoint;
    }

    public void setMostGoalPoint(Integer mostGoalPoint) {
        this.mostGoalPoint = mostGoalPoint;
    }

    public Integer getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(Integer bonusPoint) {
        this.bonusPoint = bonusPoint;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(Integer exactMatch) {
        this.exactMatch = exactMatch;
    }
}
