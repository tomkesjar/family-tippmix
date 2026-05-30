package kt.tippmix.model;

public class WinnerBet {

    private String winner;
    private String goal;
    private String mostgoal;


    public WinnerBet() {}

    public WinnerBet(String winner, String goal, String mostgoal) {
        this.winner = winner;
        this.goal = goal;
        this.mostgoal = mostgoal;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getMostgoal() {
        return mostgoal;
    }

    public void setMostgoal(String mostgoal) {
        this.mostgoal = mostgoal;
    }
}
