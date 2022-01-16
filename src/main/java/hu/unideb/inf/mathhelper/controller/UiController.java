package hu.unideb.inf.mathhelper.controller;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.exception.SceneNotFoundException;
import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.service.UserTrackService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UiController {

    private final static Integer INITIAL = 0;
    private final static Integer OTHER = 1;

    private final LocationDAO locationDAO;
    private final SceneDAO sceneDAO;
    private Controller currentController;
    private final User user;

    private Stage stage;

    @Autowired
    public UiController(LocationDAO locationDAO, SceneDAO sceneDAO, UserTrackService userTrackService) {
        this.locationDAO = locationDAO;
        this.sceneDAO = sceneDAO;
        this.user = userTrackService.getCurrentUser();
    }

    public void initializeStage(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Scene currentScene;
        if (isInitialStart()) {
            currentScene = getScene(INITIAL);
        } else {
            currentScene = getScene(OTHER);
        }
        currentController.setup();
        stage.setScene(currentScene);
        stage.show();
    }

    private Scene getScene(Integer value) {
        Scene scene = null;
        if(Objects.equals(value, INITIAL)) {
            try {
                scene = sceneDAO.loadScene(locationDAO.getScenesFolderPath() + "/welcome.fxml","welcome");
            } catch (SceneNotFoundException e) {
                //TODO: Handle error
                e.printStackTrace();
            }
        } else {
            try {
                scene = sceneDAO.loadScene(locationDAO.getScenesFolderPath() + "/main.fxml","main");
            } catch (SceneNotFoundException e) {
                //TODO: Handle error
                e.printStackTrace();
            }
        }
        currentController = sceneDAO.getController();
        return scene;
    }

    private boolean isInitialStart() {
        return user.getNickname().equals("default");
    }
}
