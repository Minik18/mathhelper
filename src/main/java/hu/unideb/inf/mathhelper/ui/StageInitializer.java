package hu.unideb.inf.mathhelper.ui;

import hu.unideb.inf.mathhelper.MathHelperApplication;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.ui.controller.SceneController;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.service.UserTrackService;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<MathHelperApplication.StageReadyEvent> {

    private final SceneDAO sceneDAO;
    private final LocationDAO locationDAO;
    private final UserTrackService userTrackService;

    private SceneController sceneController;

    @Autowired
    public StageInitializer(SceneDAO sceneDAO, LocationDAO locationDAO, UserTrackService userTrackService) {
        this.sceneDAO = sceneDAO;
        this.locationDAO = locationDAO;
        this.userTrackService = userTrackService;
    }

    @Override
    public void onApplicationEvent(MathHelperApplication.StageReadyEvent event) {
        Scene scene = getScene();
        Stage stage = event.getStage();
        stage.setScene(scene);
        sceneController.setup(stage);
        if (!firstRun()) {
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(true);
        }
        stage.setResizable(false);
        stage.setTitle("Matematika érettségire felkészítő");
        stage.getIcons().add(new Image(locationDAO.getUiPictureFilePath("math.png")));
        stage.show();
    }

    private Scene getScene() {
        Scene scene = null;
        String path;
        if (firstRun()) {
            path = locationDAO.getSceneFilePath("welcome.fxml");
        } else {
            path = locationDAO.getSceneFilePath("main.fxml");
        }
        try {
            scene = sceneDAO.loadScene(path);
            sceneController = sceneDAO.getController();
        } catch (FXMLFileNotFoundException e) {
            AppLogger.logError(e);
        }
        return scene;
    }

    private boolean firstRun() {
        return userTrackService.getCurrentUser().getNickname().equals("default");
    }
}
