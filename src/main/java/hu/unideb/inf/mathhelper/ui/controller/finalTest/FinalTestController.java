package hu.unideb.inf.mathhelper.ui.controller.finalTest;

import hu.unideb.inf.mathhelper.model.question.Part;
import hu.unideb.inf.mathhelper.ui.controller.PanelController;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import hu.unideb.inf.mathhelper.ui.model.RemainingTime;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import hu.unideb.inf.mathhelper.ui.util.FinalQuestionBuilder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class FinalTestController implements PanelController {

    private final FinalQuestionBuilder finalQuestionBuilder;
    private final PlayerObserver playerObserver;

    @FXML
    private Label taskPart;

    @FXML
    private Label timerLabel;

    @FXML
    private GridPane firstPartGridPane;

    @FXML
    private GridPane secondAPartGridPane;

    @FXML
    private GridPane secondBPartGridPane;

    @FXML
    private TextField chosenQuestion;

    @FXML
    private Button nextQuestion;

    @FXML
    private Button nextPart;

    @FXML
    private Button finish;

    @FXML
    private Button exit;

    @FXML
    private AnchorPane middleAnchor;

    private List<FinalQuestion> finalQuestions;
    private List<Node> firstPartButtons;
    private List<Node> secondPartButtons;
    private TimerTask timerTask;
    private FinalQuestion currentQuestion;
    private Timer timer;

    public FinalTestController(FinalQuestionBuilder finalQuestionBuilder, PlayerObserver playerObserver) {
        this.finalQuestionBuilder = finalQuestionBuilder;
        this.playerObserver = playerObserver;
    }

    @Override
    public void setup() {
        playerObserver.setFinalTestController(this);
        finalQuestions = finalQuestionBuilder.getFinalTestList();
        firstPartButtons = firstPartGridPane.getChildren();
        secondPartButtons = secondAPartGridPane.getChildren();
        List<Node> secondBPartButtons = secondBPartGridPane.getChildren();
        secondPartButtons.addAll(secondBPartButtons);

        firstPartButtons.forEach(node -> node.setOnMouseClicked(event -> changeQuestionByIndex(Integer.parseInt(((Button)node).getText()) - 1)));

        secondPartButtons.forEach(node -> node.setOnMouseClicked(event -> changeQuestionByIndex(Integer.parseInt(((Button)node).getText()) - 1)));

        exit.setOnMouseClicked(event -> restart());
        finish.setOnMouseClicked(event -> finishTest());
        nextPart.setOnMouseClicked(event -> setupForSecondPart());
        nextQuestion.setOnMouseClicked(event -> changeQuestionByIndex(finalQuestions.indexOf(currentQuestion) + 1));

        setupForFirstPart();

    }

    public void stoppedTimer() {
        if (secondPartButtons.get(0).isDisable()) { //First part
            setupForSecondPart();
        } else {                                    //Second part
            finishTest();
        }
    }

    private void changeQuestionByIndex(Integer index) {
        FinalQuestion finalQuestion = finalQuestions.get(index);
        if (index == 11 || index == 17) {
            changeQuestion(finalQuestion);
            nextQuestion.setDisable(true);
        } else {
            changeQuestion(finalQuestion);
            nextQuestion.setDisable(false);
        }
        if (finalQuestion.getQuestion().getPart().equals(Part.FIRST)) {
            firstPartButtons.forEach(node -> node.setDisable(false));
            firstPartButtons.get(index).setDisable(true);
        } else {
            secondPartButtons.forEach(node -> node.setDisable(false));
            secondPartButtons.get(index - 12).setDisable(true);
        }
    }

    private void changeQuestion(FinalQuestion finalQuestion) {
        middleAnchor.getChildren().clear();
        middleAnchor.getChildren().addAll(finalQuestion.getRootOfGraphicalUiElements());
        currentQuestion = finalQuestion;
    }

    private void setupForFirstPart() {

        taskPart.setText(taskPart.getAccessibleText());
        secondPartButtons.forEach(node -> node.setDisable(true));
        finish.setDisable(true);
        chosenQuestion.setDisable(true);
        changeQuestionByIndex(0);
        timerTask = new RemainingTime(timerLabel,45 * 60, playerObserver);
        startTimer();
    }

    private void setupForSecondPart() {
        taskPart.setText(taskPart.getAccessibleHelp());
        secondPartButtons.forEach(node -> node.setDisable(false));
        firstPartButtons.forEach(node -> node.setDisable(true));
        finish.setDisable(false);
        nextPart.setDisable(true);
        chosenQuestion.setDisable(false);
        changeQuestionByIndex(12);
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        if (((RemainingTime) timerTask).hasRunStarted()) {
            timerTask.cancel();
            timerTask = new RemainingTime(timerLabel, 135 * 60,playerObserver);
        }
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    private void restart() {
        stopTimer();
        playerObserver.endOfFinalTest();
    }

    private void finishTest() {
        String chosenTask = chosenQuestion.getText();
        if (chosenTask.equals("16") || chosenTask.equals("17") || chosenTask.equals("18")) {
            playerObserver.setUnwantedQuestion(finalQuestions.get(Integer.parseInt(chosenTask) - 1));
        } else {
            playerObserver.setUnwantedQuestion(finalQuestions.get(17));
        }

        stopTimer();
        playerObserver.setResults(finalQuestions);
        playerObserver.finishTest();
    }
}
