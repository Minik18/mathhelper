package hu.unideb.inf.mathhelper.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class MainController implements Controller{

    @FXML
    private Button randomQuestion;

    @FXML
    private Button categories;

    @FXML
    private Button finalTest;

    @FXML
    private Button settings;

    @FXML
    private Button help;

    @FXML
    private Button legal;

    @FXML
    private Button exit;

    @FXML
    private Text level;

    @FXML
    private Text username;

    @FXML
    private Text rewardPoints;

    @FXML
    private Text helpPoints;

    @FXML
    private Text completedQuestions;

    @FXML
    private Text completedFinals;

    @FXML
    private ProgressBar xpBar;

    @Override
    public void setup() {
     randomQuestion.setOnMouseClicked(event -> loadRandomQuestionPane());
     categories.setOnMouseClicked(event -> loadCategoryChoosePane());
     finalTest.setOnMouseClicked(event -> loadFinalTestPane());
     settings.setOnMouseClicked(event -> loadSettingsPane());
     help.setOnMouseClicked(event -> loadHelpPane());
     legal.setOnMouseClicked(event -> loadLegalPane());
     exit.setOnMouseClicked(event -> exitApp());
    }

    private void exitApp() {
        //TODO
        System.out.println("Exit clicked");
    }

    private void loadLegalPane() {
        //TODO
        System.out.println("Legal clicked");
    }

    private void loadHelpPane() {
        //TODO
        System.out.println("Help clicked");
    }

    private void loadSettingsPane() {
        //TODO
        System.out.println("Settings clicked");
    }

    private void loadFinalTestPane() {
        //TODO
        System.out.println("Finals clicked");
    }

    private void loadCategoryChoosePane() {
        //TODO
        System.out.println("Categories clicked");
    }

    private void loadRandomQuestionPane() {
        //TODO
        System.out.println("Random Question clicked");
    }
}
