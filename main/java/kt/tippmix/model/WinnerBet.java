package kt.tippmix.model;

public class WinnerBet {

    private String winner;
    private String goal;

    public WinnerBet() {}

    public WinnerBet(String winner, String goal) {
        this.winner = winner;
        this.goal = goal;
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
}
