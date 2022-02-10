package hu.unideb.inf.mathhelper.ui;

import hu.unideb.inf.mathhelper.MathHelperApplication;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.Controller;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.service.UserTrackService;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<MathHelperApplication.StageReadyEvent> {

    private final SceneDAO sceneDAO;
    private final LocationDAO locationDAO;
    private final UserTrackService userTrackService;

    private Controller controller;

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
       controller.setup(stage);
       if (!firstRun()) {
           stage.setResizable(false);
           stage.setFullScreen(true);
           stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
       }
       stage.show();
    }

    private Scene getScene() {
        Scene scene = null;
        URL path;
        if (firstRun()) {
            path = locationDAO.getSceneFilePath("welcome.fxml");
        } else {
            path = locationDAO.getSceneFilePath("main.fxml");
        }
        try {
            scene = sceneDAO.loadScene(path);
            controller = sceneDAO.getController();
        } catch (FXMLFileNotFoundException e) {
            //TODO
            e.printStackTrace();
        }
        return scene;
    }

    private boolean firstRun() {
        return userTrackService.getCurrentUser().getNickname().equals("default");
    }
}
