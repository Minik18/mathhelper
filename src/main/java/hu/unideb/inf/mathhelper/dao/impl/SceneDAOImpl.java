package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.Controller;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SceneDAOImpl implements SceneDAO {

    private Controller controller;
    private final ApplicationContext applicationContext;

    @Autowired
    private LocationDAO locationDAO;

    public SceneDAOImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Scene loadScene(URL path) throws FXMLFileNotFoundException{
        File file = checkFileExistence(path);
        return new Scene(load(file));
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public VBox loadSampleQuestionPane() throws FXMLFileNotFoundException {
        File file = checkFileExistence(locationDAO.getSampleQuestionPaneFilePath());
        loadSample(file);
        return loadSample(file);
    }

    private File checkFileExistence(URL path) throws FXMLFileNotFoundException {
        File file = new File(path.getPath());
        if (!file.exists()) {
            throw new FXMLFileNotFoundException("No fxml file found in the given path: " + path);
        } else {
            return file;
        }
    }

    private VBox loadSample(File file) {
        String sceneName = file.getName();
        String bundleName = sceneName.substring(0,sceneName.indexOf("."));
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationDAO.getTextFilePath(bundleName), new Locale("hu","HU"));
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL(),resource);
            loader.load();
            Parent parent = loader.getRoot();
            return (VBox) parent;
        } catch (IOException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
        return new VBox();
    }

    private Parent load(File file) {
        String sceneName = file.getName();
        String bundleName = sceneName.substring(0,sceneName.indexOf("."));
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationDAO.getTextFilePath(bundleName), new Locale("hu","HU"));
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL(),resource);
            loader.setControllerFactory(applicationContext::getBean);
            loader.load();
            Parent parent = loader.getRoot();
            controller = loader.getController();
            return parent;
        } catch (IOException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
        return new GridPane();
    }
}
