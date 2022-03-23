package hu.unideb.inf.mathhelper.ui.util;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.model.question.Answers;
import hu.unideb.inf.mathhelper.model.question.Help;
import hu.unideb.inf.mathhelper.model.question.Question;
import hu.unideb.inf.mathhelper.model.question.SubQuestion;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionBuilder {

    @Value("${ui.text.point}")
    private String points;

    private final LocationDAO locationDAO;
    private final UserHandleService userHandleService;
    private final PlayerObserver playerObserver;
    private final PanelDAO panelDAO;

    private Map<TextField, ImageView> usedFields;
    private List<Answers> answers;
    private List<Button> helpButtons;

    @Autowired
    public QuestionBuilder(LocationDAO locationDAO, UserHandleService userHandleService,
                           PlayerObserver playerObserver, PanelDAO panelDAO) {
        this.locationDAO = locationDAO;
        this.userHandleService = userHandleService;
        this.playerObserver = playerObserver;
        this.panelDAO = panelDAO;
    }

    public void buildQuestionPane(Question question, AnchorPane middleAnchor) {
        usedFields = new HashMap<>();
        answers = new ArrayList<>();
        helpButtons = new ArrayList<>();


        List<SubQuestion> subQuestionList = question.getSubQuestion().getSubQuestionList();
        BorderPane root;
        try {
            root = panelDAO.loadSampleQuestionPane();
            middleAnchor.getChildren().clear();

            HBox titleLine = (HBox) root.getTop();
            ((Label) (titleLine.getChildren().get(0))).setText(question.getDescription());
            Button questionImage = (Button) (titleLine.getChildren().get(2));
            if (question.hasImage()) {
                openImage(questionImage, question.getImage());
            } else {
                questionImage.setVisible(false);
            }

            ScrollPane scrollPane = (ScrollPane) root.getCenter();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            VBox vbox = (VBox) ((AnchorPane) (scrollPane.getContent())).getChildren().get(0);
            int index = 0;
            for (SubQuestion subQuestion : subQuestionList) {
                HBox main = (HBox) vbox.getChildren().get(index);
                VBox boxInMain = (VBox) main.getChildren().get(0);
                Label desc = (Label) boxInMain.getChildren().get(1);
                desc.setText(subQuestion.getDescription());

                HBox secondLine = (HBox) boxInMain.getChildren().get(2);
                HBox result = (HBox) secondLine.getChildren().get(0);
                Button pictureButton = (Button) secondLine.getChildren().get(1);
                VBox helpBox = (VBox) secondLine.getChildren().get(2);
                Label helpLabel = (Label) secondLine.getChildren().get(3);
                if (subQuestion.hasImage()) {
                    openImage(pictureButton, subQuestion.getImage());
                } else {
                    pictureButton.setVisible(false);
                }

                ImageView image = (ImageView) result.getChildren().get(2);
                image.setVisible(false);

                usedFields.put((TextField) result.getChildren().get(1), image);
                answers.add(subQuestion.getAnswer());


                Text cost = (Text) helpBox.getChildren().get(0);
                Button button = (Button) helpBox.getChildren().get(1);
                Help help = subQuestion.getHelp();
                if (help.getNeededPoints() != 0) {
                    cost.setText(help.getNeededPoints() + points);
                    button.setOnMouseClicked(event -> {
                        if (userHandleService.getUserData().getHelpPoints() >= help.getNeededPoints()) {
                            helpLabel.setVisible(true);
                            helpLabel.setText(help.getDescription());
                            secondLine.getChildren().remove(helpBox);
                            cost.setText(cost.getText().substring(0, cost.getText().length() - 1));
                            cost.setFill(Color.BLACK);
                            userHandleService.decrementHelpPoints(help.getNeededPoints());
                            playerObserver.updateUserInformation();
                        } else {
                            cost.setFill(Color.RED);
                            cost.setText(cost.getText() + "!");
                        }
                    });
                } else {
                    secondLine.getChildren().remove(helpBox);
                }
                helpButtons.add(button);
                main.setVisible(true);
                index++;
            }

            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);

            middleAnchor.getChildren().add(root);

        } catch (FXMLFileNotFoundException e) {
            AppLogger.logError(e);
        }
    }

    public Map<TextField, ImageView> getUsedFields() {
        return usedFields;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public List<Button> getHelpButtons() {
        return helpButtons;
    }

    private void openImage(Node button, String name) {
        button.setVisible(true);
        button.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.initOwner(button.getScene().getWindow());
            stage.setTitle(FilenameUtils.getBaseName(name));
            stage.setScene(new Scene(new AnchorPane(new ImageView(new Image(locationDAO.getQuestionPictureFilePath(name))))));
            stage.setResizable(false);
            stage.show();
        });
    }

}
