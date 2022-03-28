package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.BossDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.dao.SettingsDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.model.boss.Boss;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BattleController implements PanelController {

    private final UserHandleService userHandleService;
    private final BossDAO bossDAO;
    private final LocationDAO locationDAO;
    private final PlayerObserver playerObserver;
    private final SettingsDAO settingsDAO;
    private final PanelDAO panelDAO;

    @FXML
    private ImageView enemyPicture;

    @FXML
    private ImageView textBook;

    @FXML
    private ImageView correctPicture;

    @FXML
    private ImageView wrongPicture;

    @FXML
    private Button start;

    @FXML
    private Button addPoints;

    @FXML
    private Label difficulty;

    @FXML
    private Label about;

    @FXML
    private Label knowledge;

    @FXML
    private Label enemyName;

    @FXML
    private AnchorPane root;

    private Boss currentBoss;

    @Autowired
    public BattleController(UserHandleService userHandleService, BossDAO bossDAO, LocationDAO locationDAO,
                            PlayerObserver playerObserver, SettingsDAO settingsDAO, PanelDAO panelDAO) {
        this.userHandleService = userHandleService;
        this.bossDAO = bossDAO;
        this.locationDAO = locationDAO;
        this.playerObserver = playerObserver;
        this.settingsDAO = settingsDAO;
        this.panelDAO = panelDAO;
    }

    @Override
    public void setup() {
        if (settingsDAO.getSettings().isFirstBossFightOpen()) {
            loadFirstScene();
        }
        if (settingsDAO.getSettings().isAllBossesDefeated()) {
            loadWinScene();
        } else {
            textBook.setOpacity(0.0);
            wrongPicture.setOpacity(0.0);
            correctPicture.setOpacity(0.0);
            knowledge.setText(userHandleService.getUserData().getStudentKnowledgePoint().toString());
            setupBoss();
            if (userHandleService.getUserData().getRewardPoints() <= 0) {
                addPoints.setDisable(true);
            }
            addPoints.setOnMouseClicked(event -> {
                Integer amount = userHandleService.getUserData().getRewardPoints();
                userHandleService.incrementStudentKnowledgePoints(amount);  //This also decrements the user's points
                playerObserver.updateUserInformation();
                knowledge.setText(userHandleService.getUserData().getStudentKnowledgePoint().toString());
                addPoints.setDisable(true);
            });
            start.setOnMouseClicked(event -> play());
        }
    }

    private void loadFirstScene() {
        try {
            AnchorPane loaded = panelDAO.loadPanel("firstBossOpen.fxml");
            load(loaded);
        } catch (FXMLFileNotFoundException e) {
            AppLogger.logError(e);
        }
    }

    private void setupBoss() {
        try {
            currentBoss = bossDAO.getBossList().get(userHandleService.getUserData().getBossLevel());
            enemyName.setText(currentBoss.getName());
            enemyPicture.setImage(new Image(locationDAO.getUiPictureFilePath(currentBoss.getPicturePath())));
            enemyPicture.setOpacity(1.0);
            difficulty.setText(currentBoss.getDifficulty().toString());
            about.setText(currentBoss.getAbout());
        } catch (IndexOutOfBoundsException e) { //This means all bosses have been defeated
            loadWinScene();
        }


    }

    private void loadWinScene() {
        try {
            AnchorPane loaded = panelDAO.loadPanel("defeatedAllBosses.fxml");
            load(loaded);
        } catch (FXMLFileNotFoundException e) {
            AppLogger.logError(e);
        }
    }

    private void load(AnchorPane pane) {
        root.getChildren().clear();
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        root.getChildren().add(pane);
        panelDAO.getController().setup();
    }

    private void play() {

        start.setDisable(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), textBook);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        fadeTransition.setOnFinished(event -> {
            Integer knowledge = userHandleService.getUserData().getStudentKnowledgePoint();
            Integer difficulty = currentBoss.getDifficulty();
            double chanceToWin = knowledge / (difficulty * 1.0);
            Random ran = new Random();
            double random = ran.nextDouble(1) + 0.75;
            boolean win = chanceToWin >= random;
            FadeTransition resultFadeTransition;
            if (win) {
                resultFadeTransition = new FadeTransition(Duration.seconds(3), correctPicture);
                resultFadeTransition.setOnFinished(event1 -> {
                    FadeTransition enemyFade = new FadeTransition(Duration.seconds(4), enemyPicture);
                    enemyFade.setFromValue(1.0);
                    enemyFade.setToValue(0.0);
                    enemyFade.setOnFinished(event2 -> {
                        userHandleService.incrementBossLevel();
                        textBook.setOpacity(0.0);
                        wrongPicture.setOpacity(0.0);
                        correctPicture.setOpacity(0.0);
                        start.setDisable(false);
                        setupBoss();
                    });
                    enemyFade.play();
                });
            } else {
                resultFadeTransition = new FadeTransition(Duration.seconds(3), wrongPicture);
                resultFadeTransition.setOnFinished(event1 -> {
                    textBook.setOpacity(0.0);
                    wrongPicture.setOpacity(0.0);
                    correctPicture.setOpacity(0.0);
                    start.setDisable(false);
                });
            }

            resultFadeTransition.setFromValue(0.0);
            resultFadeTransition.setToValue(1.0);
            resultFadeTransition.play();
        });
        fadeTransition.play();

    }
}
