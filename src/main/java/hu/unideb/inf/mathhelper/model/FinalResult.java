package hu.unideb.inf.mathhelper.model;

public class FinalResult {

    private final Integer sumOfFinalTest;
    private final Integer achievedPoint;
    private final Integer percentage;

    public FinalResult(Integer sumOfFinalTest, Integer achievedPoint) {
        this.sumOfFinalTest = sumOfFinalTest;
        this.achievedPoint = achievedPoint;
        this.percentage = (int) ((this.achievedPoint / (this.sumOfFinalTest * 1.0)) * 100.0);
    }

    public Integer getPercentage() {
        return percentage;
    }

    public Integer getSumOfFinalTest() {
        return sumOfFinalTest;
    }

    public Integer getAchievedPoint() {
        return achievedPoint;
    }
}
