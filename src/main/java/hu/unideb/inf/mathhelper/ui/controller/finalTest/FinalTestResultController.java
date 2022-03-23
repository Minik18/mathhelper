package hu.unideb.inf.mathhelper.ui.controller.finalTest;

import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.model.FinalResult;
import hu.unideb.inf.mathhelper.model.question.SubQuestion;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.controller.PanelController;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import hu.unideb.inf.mathhelper.ui.util.QuestionValidator;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinalTestResultController implements PanelController {


    private final PlayerObserver playerObserver;
    private final QuestionValidator questionValidator;
    private final PanelDAO panelDAO;
    private final UserHandleService userHandleService;

    @Value("${ui.text.unwanted}")
    private String unwantedText;

    @Value("${ui.text.point}")
    private String pont;

    @FXML
    private AnchorPane middleAnchor;

    @FXML
    private Button finish;

    @FXML
    private Button summary;

    @FXML
    private Button nextQuestion;

    @FXML
    private Label unwantedLabel;

    @FXML
    private GridPane firstPartGridPane;

    @FXML
    private GridPane secondAPartGridPane;

    @FXML
    private GridPane secondBPartGridPane;

    private List<FinalQuestion> finalQuestions;
    private FinalQuestion currentQuestion;
    private List<Node> questionButtons;

    @Autowired
    public FinalTestResultController(PlayerObserver playerObserver, QuestionValidator questionValidator,
                                     PanelDAO panelDAO, UserHandleService userHandleService) {
        this.playerObserver = playerObserver;
        this.questionValidator = questionValidator;
        this.panelDAO = panelDAO;
        this.userHandleService = userHandleService;
    }

    @Override
    public void setup() {
        finalQuestions = playerObserver.getFinalQuestions();

        unwantedLabel.setText((finalQuestions.indexOf(playerObserver.getUnwantedQuestion()) + 1) + unwantedText);

        questionButtons = new ArrayList<>();
        questionButtons.addAll(firstPartGridPane.getChildren());
        questionButtons.addAll(secondAPartGridPane.getChildren());
        questionButtons.addAll(secondBPartGridPane.getChildren());

        questionButtons.forEach(node -> node.setOnMouseClicked(event -> changeQuestionByIndex(Integer.parseInt(((Button) node).getText()) - 1)));


        finish.setOnMouseClicked(event -> exit());
        nextQuestion.setOnMouseClicked(event -> changeQuestionByIndex(finalQuestions.indexOf(currentQuestion) + 1));

        changeQuestionByIndex(0);

        FinalResult result = calculateResult();

        if (result.getPercentage() >= 25) {
            userHandleService.incrementCompletedFinalQuestions();
            playerObserver.updateUserInformation();
        }

        playerObserver.setFinalResult(result);

        summary.setOnMouseClicked(event -> loadSummaryPane());
    }

    private void loadSummaryPane() {
        try {
            AnchorPane anchorPane = panelDAO.loadPanel("summary.fxml");
            panelDAO.getController().setup();
            middleAnchor.getChildren().clear();
            AnchorPane.setBottomAnchor(anchorPane, 0.0);
            AnchorPane.setTopAnchor(anchorPane, 0.0);
            AnchorPane.setRightAnchor(anchorPane, 0.0);
            AnchorPane.setLeftAnchor(anchorPane, 0.0);
            middleAnchor.getChildren().add(anchorPane);
        } catch (FXMLFileNotFoundException e) {
            //TODO
        }
    }

    private FinalResult calculateResult() {
        int sum = 0;
        int reached = 0;
        for (FinalQuestion finalQuestion : finalQuestions) {
            if (finalQuestions.indexOf(finalQuestion) != finalQuestions.indexOf(playerObserver.getUnwantedQuestion())) {
                questionValidator.validateQuestion(finalQuestion);
                AnchorPane anchorPane = (AnchorPane) ((ScrollPane) ((BorderPane) (finalQuestion.getRootOfGraphicalUiElements()).getChildren().get(0)).getCenter()).getContent();
                List<Node> vBox = ((VBox) anchorPane.getChildren().get(0)).getChildren();
                int index = 0;
                for (Node hBox : vBox) {
                    if (hBox.isVisible()) {
                        HBox secondLine = (HBox) ((VBox) ((HBox) hBox).getChildren().get(0)).getChildren().get(2);
                        Node userInput = secondLine.getChildren().stream()
                                .filter(node -> node instanceof HBox)
                                .findFirst()
                                .orElse(new HBox());
                        secondLine.getChildren().clear();
                        secondLine.getChildren().add(userInput);
                        String point;
                        Label label = new Label();
                        SubQuestion subQuestion = finalQuestion.getQuestion().getSubQuestion().getSubQuestionList().get(index);
                        if (subQuestion.isRight()) {
                            point = subQuestion.getPoint() + " / " + subQuestion.getPoint() + pont;
                            reached += subQuestion.getPoint();
                        } else {
                            point = "0 / " + subQuestion.getPoint() + pont;
                        }
                        sum += subQuestion.getPoint();
                        label.setText(point);
                        secondLine.getChildren().add(label);
                        index++;
                    }
                }
            }
        }
        FinalResult finalResult = new FinalResult(sum, reached);
        Integer percentage = finalResult.getPercentage();
        if (percentage >= 25) {
            if (percentage < 40) {
                userHandleService.incrementXp(30);
                userHandleService.incrementCompletedFinalQuestions();
            } else if (percentage < 60) {
                userHandleService.incrementXp(50);
                userHandleService.incrementCompletedFinalQuestions();
            } else if (percentage < 80) {
                userHandleService.incrementXp(80);
                userHandleService.incrementCompletedFinalQuestions();
            } else {
                userHandleService.incrementXp(100);
                userHandleService.incrementCompletedFinalQuestions();
            }
            playerObserver.updateUserInformation();
        }
        return finalResult;
    }

    private void exit() {
        playerObserver.endOfFinalTest();
    }

    private void changeQuestionByIndex(Integer index) {
        FinalQuestion finalQuestion = finalQuestions.get(index);
        if (index == 17) {
            changeQuestion(finalQuestion);
            nextQuestion.setDisable(true);
        } else {
            changeQuestion(finalQuestion);
            nextQuestion.setDisable(false);
        }
        questionButtons.forEach(node -> node.setDisable(false));
        questionButtons.get(index).setDisable(true);
    }

    private void changeQuestion(FinalQuestion finalQuestion) {
        middleAnchor.getChildren().clear();
        middleAnchor.getChildren().addAll(finalQuestion.getRootOfGraphicalUiElements());
        currentQuestion = finalQuestion;
    }

}
