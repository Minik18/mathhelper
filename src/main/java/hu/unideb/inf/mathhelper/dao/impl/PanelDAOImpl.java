package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.Controller;
import hu.unideb.inf.mathhelper.ui.controller.PanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PanelDAOImpl implements PanelDAO {

    private final LocationDAO locationDAO;
    private final ApplicationContext applicationContext;

    private Controller controller;

    public PanelDAOImpl(LocationDAO locationDAO, ApplicationContext applicationContext) {
        this.locationDAO = locationDAO;
        this.applicationContext = applicationContext;
    }

    @Override
    public BorderPane loadSampleQuestionPane() throws FXMLFileNotFoundException {
        File file = checkFileExistence(locationDAO.getSampleQuestionPaneFilePath());
        load(file);
        return (BorderPane) load(file);
    }

    @Override
    public AnchorPane loadPanel(String fileName) throws FXMLFileNotFoundException {
        File file = checkFileExistence(locationDAO.getPaneFilePath(fileName));
        load(file);
        return (AnchorPane) load(file);
    }

    @Override
    public PanelController getController() {
        return (PanelController) controller;
    }


    private Parent load(File file) {
        String sceneName = file.getName();
        String bundleName = sceneName.substring(0,sceneName.indexOf("."));
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationDAO.getTextFilePath(bundleName), new Locale("hu","HU"));
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL(),resource);
            loader.setControllerFactory(applicationContext::getBean);
            loader.load();
            controller = loader.getController();
            return loader.getRoot();
        } catch (IOException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
        return new VBox();
    }

    private File checkFileExistence(String path) throws FXMLFileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FXMLFileNotFoundException("No fxml file found in the given path: " + path);
        } else {
            return file;
        }
    }

}
