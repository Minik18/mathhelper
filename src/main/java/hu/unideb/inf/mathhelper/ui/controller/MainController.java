package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.model.UserData;
import hu.unideb.inf.mathhelper.model.level.Level;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
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

    @Autowired
    private SceneDAO sceneDAO;

    @Value("${ui.text.level}")
    private String levelString;

    @Value("${ui.text.xp}")
    private String xpString;

    @Value("${ui.text.point}")
    private String pointString;

    @FXML
    public AnchorPane centerPane;

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

    @FXML
    private ImageView profilePicture;

    @Override
    public void setup(Stage stage) {
     randomQuestion.setOnMouseClicked(event -> loadPane("random.fxml"));
     categories.setOnMouseClicked(event -> loadPane("categories.fxml"));
     finalTest.setOnMouseClicked(event -> loadPane("final.fxml"));
     settings.setOnMouseClicked(event -> loadPane("settings.fxml"));
     help.setOnMouseClicked(event -> loadPane("help.fxml"));
     legal.setOnMouseClicked( event -> loadPane("legal.fxml"));
     exit.setOnMouseClicked(event -> stage.close());
     updateUserInformation();
    }

    private void updateUserInformation() {
        UserData userData = userHandleService.getUserData();
        Integer levelCount = userData.getLevel();
        Integer currentXp = userData.getXp();
        Integer maxXp = getMaxXpOnCurrentLevel(levelCount);
        username.setText(userData.getNickname());
        rewardPoints.setText(userData.getRewardPoints().toString() + pointString);
        helpPoints.setText(userData.getHelpPoints().toString() + pointString);
        completedQuestions.setText(userData.getNumberOfCompletedQuestions().toString() + pointString);
        completedFinals.setText(userData.getCountOfFinals().toString() + pointString);
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

    private void loadPane(String fileName) {
        try {
            AnchorPane anchorPane = (AnchorPane) sceneDAO.loadScene(locationDAO.getPaneFilePath(fileName)).getRoot();
            sceneDAO.getController().setup(null);
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(anchorPane.getChildren());
        } catch (FXMLFileNotFoundException e) {
            //TODO
            e.printStackTrace();
        }
    }
}
