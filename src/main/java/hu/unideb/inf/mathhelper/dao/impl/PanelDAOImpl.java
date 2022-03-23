package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
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
import java.net.URL;
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
    public BorderPane loadSampleQuestionPane() {
        return (BorderPane) load(locationDAO.getSampleQuestionPaneFilePath());
    }

    @Override
    public AnchorPane loadPanel(String fileName) {
        return (AnchorPane) load(locationDAO.getPaneFilePath(fileName));
    }

    @Override
    public PanelController getController() {
        return (PanelController) controller;
    }


    private Parent load(String path) {
        String sceneName = new File(path).getName();
        String bundleName = sceneName.substring(0, sceneName.indexOf("."));
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationDAO.getTextFilePath(bundleName), new Locale("hu", "HU"));
            FXMLLoader loader = new FXMLLoader(new URL(path), resource);
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
}
