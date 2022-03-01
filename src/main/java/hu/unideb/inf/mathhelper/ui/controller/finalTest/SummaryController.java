package hu.unideb.inf.mathhelper.ui.controller.finalTest;

import hu.unideb.inf.mathhelper.model.FinalResult;
import hu.unideb.inf.mathhelper.ui.controller.PanelController;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummaryController implements PanelController {

    private final PlayerObserver playerObserver;

    @FXML
    private Label achievedPoint;

    @FXML
    private Label achievedGrade;

    @Autowired
    public SummaryController(PlayerObserver playerObserver) {
        this.playerObserver = playerObserver;
    }

    @Override
    public void setup() {
        FinalResult finalResult = playerObserver.getFinalResult();
        achievedPoint.setText(finalResult.getAchievedPoint() + " / " + finalResult.getSumOfFinalTest());
        String grade;
        Integer percentage = finalResult.getPercentage();
        if (percentage < 25) {
            grade = "Elégtelen";
        } else if (percentage < 40) {
            grade = "Elégséges";
        } else if (percentage < 60) {
            grade = "Közepes";
        } else if (percentage < 80) {
            grade = "Jó";
        } else {
            grade = "Jeles";
        }
        achievedGrade.setText(grade);
    }
}
