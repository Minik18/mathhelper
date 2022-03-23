package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.ui.controller.Controller;
import hu.unideb.inf.mathhelper.ui.controller.SceneController;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SceneDAOImpl implements SceneDAO {

    private Controller sceneController;
    private final ApplicationContext applicationContext;
    private final LocationDAO locationDAO;


    public SceneDAOImpl(ApplicationContext applicationContext, LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
        this.applicationContext = applicationContext;
    }

    @Override
    public Scene loadScene(String path) {
        return new Scene(load(path));
    }

    @Override
    public SceneController getController() {
        return (SceneController) sceneController;
    }

    private Parent load(String path) {
        String sceneName = new File(path).getName();
        String bundleName = sceneName.substring(0, sceneName.indexOf("."));
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationDAO.getTextFilePath(bundleName), new Locale("hu", "HU"));
            FXMLLoader loader = new FXMLLoader(new URL(path), resource);
            loader.setControllerFactory(applicationContext::getBean);
            loader.load();
            Parent parent = loader.getRoot();
            sceneController = loader.getController();
            return parent;
        } catch (IOException e) {
            AppLogger.logError(e);
        }
        return new GridPane();
    }
}
