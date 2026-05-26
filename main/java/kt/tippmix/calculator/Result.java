package kt.tippmix.calculator;

public enum Result {
    FULL_HIT(Points.POINT_FULL),
    HIT_GOAL_DIFF(Points.POINT_GOAL_DIFF),
    HIT_WINNER(Points.POINT_WINNER),
    NO_HIT(Points.POINT_NO_HIT);

    private int point;
    private Result(int point) {
        this.point = point;
    }
}
