package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.UserData;
import hu.unideb.inf.mathhelper.model.level.Level;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class MainController implements Controller{

    @Autowired
    private UserHandleService userHandleService;

    @Autowired
    private LevelDAO levelDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Value("${ui.text.level}")
    private String levelString;

    @Value("${ui.text.xp}")
    private String xpString;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Text xp;

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
    public void setup(Stage stage) {
     randomQuestion.setOnMouseClicked(event -> loadRandomQuestionPane());
     categories.setOnMouseClicked(event -> loadCategoryChoosePane());
     finalTest.setOnMouseClicked(event -> loadFinalTestPane());
     settings.setOnMouseClicked(event -> loadSettingsPane());
     help.setOnMouseClicked(event -> loadHelpPane());
     legal.setOnMouseClicked(event -> loadLegalPane());
     exit.setOnMouseClicked(event -> exitApp());
     updateUserInformation();
    }

    private void updateUserInformation() {
        UserData userData = userHandleService.getUserData();
        Integer levelCount = userData.getLevel();
        Integer currentXp = userData.getXp();
        Integer maxXp = getMaxXpOnCurrentLevel(levelCount);
        username.setText(userData.getNickname());
        rewardPoints.setText(userData.getRewardPoints().toString());
        helpPoints.setText(userData.getHelpPoints().toString());
        completedQuestions.setText(userData.getNumberOfCompletedQuestions().toString());
        completedFinals.setText(userData.getCountOfFinals().toString());
        level.setText(levelCount + levelString);
        xp.setText(currentXp + " / " + maxXp + xpString);
        xpBar.setProgress(currentXp / (maxXp + 0.0) );
    }

    private Integer getMaxXpOnCurrentLevel(Integer level) {
        List<Level> levelList = levelDAO.getLevelSystem(locationDAO.getLevelSystemFilePath());
        Optional<Level> levelObj = levelList.stream()
                .filter(l -> l.getLevel().equals(level))
                .findFirst();
        if (levelObj.isPresent()) {
            return levelObj.get().getRequiredXp();
        } else {
            //TODO Handle situation
            throw new NoSuchElementException("There is no level inside levels.xml with level : " + level );
        }
    }

    private void exitApp() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
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
