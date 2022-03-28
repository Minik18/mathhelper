package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.dao.PicturesDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.ImageNotFoundException;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.model.UserData;
import hu.unideb.inf.mathhelper.model.level.Level;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
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
public class MainSceneController implements SceneController {

    private final UserHandleService userHandleService;
    private final LevelDAO levelDAO;
    private final LocationDAO locationDAO;
    private final PanelDAO panelDAO;
    private final PicturesDAO picturesDAO;

    @Value("${ui.text.level}")
    private String levelString;

    @Value("${ui.text.xp}")
    private String xpString;

    @Value("${ui.text.point}")
    private String pointString;

    @Value("${ui.text.db}")
    private String dbString;

    @FXML
    public AnchorPane centerPane;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Text xp;

    @FXML
    private Button randomQuestion;

    @FXML
    private Button finalTest;

    @FXML
    private Button settings;

    @FXML
    private Button battle;

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

    @Autowired
    public MainSceneController(UserHandleService userHandleService, LevelDAO levelDAO, LocationDAO locationDAO,
                               PanelDAO panelDAO, PlayerObserver playerObserver, PicturesDAO picturesDAO) {
        this.userHandleService = userHandleService;
        this.levelDAO = levelDAO;
        this.locationDAO = locationDAO;
        this.panelDAO = panelDAO;
        this.picturesDAO = picturesDAO;
        playerObserver.setMainController(this);
    }

    @Override
    public void setup(Stage stage) {

        battle.setOnMouseClicked(event -> loadPane("battle.fxml"));
        randomQuestion.setOnMouseClicked(event -> loadPane("random.fxml"));
        finalTest.setOnMouseClicked(event -> loadPane("final.fxml"));
        settings.setOnMouseClicked(event -> loadPane("settings.fxml"));
        help.setOnMouseClicked(event -> loadPane("help.fxml"));
        legal.setOnMouseClicked(event -> loadPane("legal.fxml"));
        exit.setOnMouseClicked(event -> stage.close());
        updateUserInformation();
    }

    public void lockButtons() {
        randomQuestion.setDisable(true);
        finalTest.setDisable(true);
        settings.setDisable(true);
        help.setDisable(true);
        legal.setDisable(true);
        exit.setDisable(true);
    }

    public void unlockButtons() {
        randomQuestion.setDisable(false);
        finalTest.setDisable(false);
        settings.setDisable(false);
        help.setDisable(false);
        legal.setDisable(false);
        exit.setDisable(false);
        loadPane("final.fxml");
    }

    public void updateUserInformation() {
        UserData userData = userHandleService.getUserData();
        Integer levelCount = userData.getLevel();
        Integer currentXp = userData.getXp();
        Integer maxXp = getMaxXpOnCurrentLevel(levelCount);
        username.setText(userData.getNickname());
        rewardPoints.setText(userData.getRewardPoints().toString() + pointString);
        helpPoints.setText(userData.getHelpPoints().toString() + pointString);
        completedQuestions.setText(userData.getNumberOfCompletedQuestions().toString() + dbString);
        completedFinals.setText(userData.getCountOfFinals().toString() + dbString);
        level.setText(levelCount + levelString);
        xp.setText(currentXp + " / " + maxXp + xpString);
        xpBar.setProgress(currentXp / (maxXp + 0.0));
        String profilePicName = userData.getProfilePictureName();
        if (!profilePicName.equals("")) {
            try {
                profilePicture.setImage(picturesDAO.loadPicture(locationDAO.getProfilePictureFilePath(userData.getProfilePictureName())));
            } catch (ImageNotFoundException e) {
                AppLogger.logError(e);
            }
        }
    }

    private Integer getMaxXpOnCurrentLevel(Integer level) {
        List<Level> levelList = levelDAO.getLevelSystem();
        Optional<Level> levelObj = levelList.stream()
                .filter(l -> l.getLevel().equals(level))
                .findFirst();
        if (levelObj.isPresent()) {
            return levelObj.get().getRequiredXp();
        } else {
            NoSuchElementException e = new NoSuchElementException("There is no level inside levels.xml with level : " + level);
            AppLogger.logError(e);
            throw e;
        }
    }

    private void loadPane(String fileName) {
        try {
            AnchorPane anchorPane = panelDAO.loadPanel(fileName);
            panelDAO.getController().setup();
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(anchorPane.getChildren());
        } catch (FXMLFileNotFoundException e) {
            AppLogger.logError(e);
            e.printStackTrace();
        }
    }
}
