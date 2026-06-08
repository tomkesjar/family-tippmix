package kt.tippmix.model;

import jakarta.persistence.Column;

public class RankingResponse {

    private String fileName;
    private String secretName;
    private Integer matchPoint;
    private Integer winnerPoint;
    private Integer goalPoint;
    private Integer mostGoalPoint;
    private Integer bonusPoint;
    private Integer total;
    private Integer countExacts;
    private Nation favouriteNation;
    private Nation goalScorerNationality;
    private MostGoals mostGoals;

    public RankingResponse() {
    }

    /** Used when the caller already holds enum instances. */
    public RankingResponse(String fileName, String secretName, Integer matchPoint, Integer winnerPoint, Integer goalPoint, Integer mostGoalPoint, Integer bonusPoint, Integer total, Integer countExacts, Nation favouriteNation, Nation goalScorerNationality, MostGoals mostGoals) {
        this.fileName = fileName;
        this.secretName = secretName;
        this.matchPoint = matchPoint;
        this.winnerPoint = winnerPoint;
        this.goalPoint = goalPoint;
        this.mostGoalPoint = mostGoalPoint;
        this.bonusPoint = bonusPoint;
        this.total = total;
        this.countExacts = countExacts;
        this.favouriteNation = favouriteNation;
        this.goalScorerNationality = goalScorerNationality;
        this.mostGoals = mostGoals;
    }

    /**
     * JPQL constructor expression target.
     * The User entity stores favouriteNation / goalScorerNationality / mostGoals as plain
     * String columns, so JPQL delivers them as Strings; this constructor converts them.
     */
    public RankingResponse(String fileName, String secretName, Integer matchPoint, Integer winnerPoint, Integer goalPoint, Integer mostGoalPoint, Integer bonusPoint, Integer total, Integer countExacts, String favouriteNation, String goalScorerNationality, String mostGoals) {
        this.fileName = fileName;
        this.secretName = secretName;
        this.matchPoint = matchPoint;
        this.winnerPoint = winnerPoint;
        this.goalPoint = goalPoint;
        this.mostGoalPoint = mostGoalPoint;
        this.bonusPoint = bonusPoint;
        this.total = total;
        this.countExacts = countExacts;
        this.favouriteNation        = favouriteNation        != null ? Nation.valueOf(favouriteNation)          : null;
        this.goalScorerNationality  = goalScorerNationality  != null ? Nation.valueOf(goalScorerNationality)     : null;
        this.mostGoals              = mostGoals              != null ? MostGoals.valueOf(mostGoals)              : null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
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

    public Integer getCountExacts() {
        return countExacts;
    }

    public void setCountExacts(Integer countExacts) {
        this.countExacts = countExacts;
    }

    public Nation getFavouriteNation() {
        return favouriteNation;
    }

    public void setFavouriteNation(Nation favouriteNation) {
        this.favouriteNation = favouriteNation;
    }

    public Nation getGoalScorerNationality() {
        return goalScorerNationality;
    }

    public void setGoalScorerNationality(Nation goalScorerNationality) {
        this.goalScorerNationality = goalScorerNationality;
    }

    public MostGoals getMostGoals() {
        return mostGoals;
    }

    public void setMostGoals(MostGoals mostGoals) {
        this.mostGoals = mostGoals;
    }
}
