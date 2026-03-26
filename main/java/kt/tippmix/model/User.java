package kt.tippmix.model;

public class User {
    private String userName;
    private String secretName;
    private String favouriteNation;
    private String goalScorerNationality;

    public User(String userName, String secretName, String favouriteNation, String goalScorerNationality) {
        this.userName = userName;
        this.secretName = secretName;
        this.favouriteNation = favouriteNation;
        this.goalScorerNationality = goalScorerNationality;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public String getFavouriteNation() {
        return favouriteNation;
    }

    public void setFavouriteNation(String favouriteNation) {
        this.favouriteNation = favouriteNation;
    }

    public String getGoalScorerNationality() {
        return goalScorerNationality;
    }

    public void setGoalScorerNationality(String goalScorerNationality) {
        this.goalScorerNationality = goalScorerNationality;
    }
}
