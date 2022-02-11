package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.Question;
import hu.unideb.inf.mathhelper.ui.util.QuestionBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinalController implements Controller{

    @Autowired
    private PanelDAO panelDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private QuestionBuilder questionBuilder;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button start;

    @Override
    public void setup(Stage stage) {
        start.setOnAction(event -> {
            mainPane.getChildren().clear();
            load();
        });
    }

    private void load() {
        try {
            AnchorPane root = panelDAO.loadPanel("finalTest.fxml");
            AnchorPane.setBottomAnchor(root,0.0);
            AnchorPane.setTopAnchor(root,0.0);
            AnchorPane.setLeftAnchor(root,0.0);
            AnchorPane.setRightAnchor(root,0.0);
            try {
                List<Question> questions = questionDAO.loadQuestionsIntoList(locationDAO.getQuestionFolderPath().getPath());
                AnchorPane middleAnchor = (AnchorPane) ((BorderPane)root.getChildren().get(0)).getCenter();
                questionBuilder.buildQuestionPane(questions.get(17),middleAnchor);
            } catch (QuestionFileNotFoundException e) {
                e.printStackTrace();
                //TODO
            }
            mainPane.getChildren().add(root);
        } catch (FXMLFileNotFoundException e) {
            //TODO
            e.printStackTrace();
        }
    }
}
