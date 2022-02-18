package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.Part;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import hu.unideb.inf.mathhelper.ui.model.RemainingTime;
import hu.unideb.inf.mathhelper.ui.util.FinalQuestionBuilder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class FinalController implements Controller {

    @Autowired
    private PanelDAO panelDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private FinalQuestionBuilder finalQuestionBuilder;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button start;

    private List<FinalQuestion> finalQuestions;
    private AnchorPane middleAnchor;
    private AnchorPane loadedRoot;
    private List<Node> firstPartButtons;
    private List<Node> secondBPartButtons;
    private List<Node> secondPartButtons;
    private Label timerLabel;
    private Label taskPart;
    private TimerTask timerTask;
    private TextField notWantedQuestion;
    private Button nextQuestion;
    private Button nextPart;
    private Button finish;
    private FinalQuestion currentQuestion;

    @Override
    public void setup(Stage stage) {
        start.setOnAction(event -> {
            mainPane.getChildren().clear();
            load();
        });
    }

    private void load() {
        try {
            finalQuestions = finalQuestionBuilder.getFinalTestList();
            loadedRoot = panelDAO.loadPanel("finalTest.fxml");
            AnchorPane.setBottomAnchor(loadedRoot, 0.0);
            AnchorPane.setTopAnchor(loadedRoot, 0.0);
            AnchorPane.setLeftAnchor(loadedRoot, 0.0);
            AnchorPane.setRightAnchor(loadedRoot, 0.0);

            middleAnchor = (AnchorPane) ((BorderPane) loadedRoot.getChildren().get(0)).getCenter();

            getUiComponents();

            setupForFirstPart();

            mainPane.getChildren().add(loadedRoot);
        } catch (FXMLFileNotFoundException e) {
            //TODO
            e.printStackTrace();
        }
    }

    private void setupForFirstPart() {

        taskPart.setText(taskPart.getAccessibleText());
        secondPartButtons.forEach(node -> node.setDisable(true));
        finish.setDisable(true);
        notWantedQuestion.setDisable(true);
        changeQuestionByIndex(0);
        timerTask = new RemainingTime(timerLabel,45 * 60);
        startTimer();
    }

    private void setupForSecondPart() {
        taskPart.setText(taskPart.getAccessibleHelp());
        secondPartButtons.forEach(node -> node.setDisable(false));
        firstPartButtons.forEach(node -> node.setDisable(true));
        finish.setDisable(false);
        nextPart.setDisable(true);
        notWantedQuestion.setDisable(false);
        changeQuestionByIndex(12);
        startTimer();
    }

    private void finishTest() {
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

    private void startTimer() {
        Timer timer = new Timer();
        if (((RemainingTime)timerTask).hasRunStarted()) {
            timerTask.cancel();
            timerTask = new RemainingTime(timerLabel,135 * 60);
        }
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private void getUiComponents() {
        VBox vBox = (VBox) ((HBox) ((BorderPane) loadedRoot.getChildren().get(0)).getRight()).getChildren().get(1);
        taskPart = (Label) vBox.getChildren().get(0);
        timerLabel = (Label) ((HBox) vBox.getChildren().get(1)).getChildren().get(0);
        VBox boxOfGrids = (VBox) vBox.getChildren().get(2);

        firstPartButtons = ((GridPane) ((VBox) ((HBox) boxOfGrids.getChildren().get(0)).getChildren().get(0)).getChildren().get(2)).getChildren();
        List<Node> secondAPartButtons = ((GridPane) ((VBox) ((HBox) boxOfGrids.getChildren().get(1)).getChildren().get(0)).getChildren().get(2)).getChildren();
        secondBPartButtons = ((GridPane) ((VBox) ((HBox) boxOfGrids.getChildren().get(2)).getChildren().get(0)).getChildren().get(2)).getChildren();

        notWantedQuestion = (TextField) ((VBox)((VBox) ((HBox) boxOfGrids.getChildren().get(2)).getChildren().get(0)).getChildren().get(3)).getChildren().get(1);

        secondPartButtons = new ArrayList<>();
        secondPartButtons.addAll(secondAPartButtons);
        secondPartButtons.addAll(secondBPartButtons);

        nextQuestion = (Button) vBox.getChildren().get(3);
        nextQuestion.setOnMouseClicked(event -> changeQuestionByIndex(finalQuestions.indexOf(currentQuestion) + 1));

        nextPart = (Button) vBox.getChildren().get(4);
        nextPart.setOnMouseClicked(event -> setupForSecondPart());

        finish = (Button) vBox.getChildren().get(5);
        finish.setOnMouseClicked(event -> finishTest());

        firstPartButtons.forEach(node -> node.setOnMouseClicked(event -> changeQuestionByIndex(Integer.parseInt(((Button)node).getText()) - 1)));

        secondPartButtons.forEach(node -> node.setOnMouseClicked(event -> changeQuestionByIndex(Integer.parseInt(((Button)node).getText()) - 1)));

    }

}
