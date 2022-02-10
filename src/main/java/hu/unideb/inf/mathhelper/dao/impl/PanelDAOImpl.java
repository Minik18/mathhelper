package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class PanelDAOImpl implements PanelDAO {

    @Autowired
    private LocationDAO locationDAO;

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

    private Parent load(File file) {
        String sceneName = file.getName();
        String bundleName = sceneName.substring(0,sceneName.indexOf("."));
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationDAO.getTextFilePath(bundleName), new Locale("hu","HU"));
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL(),resource);
            loader.load();
            return loader.getRoot();
        } catch (IOException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
        return new VBox();
    }

    private File checkFileExistence(URL path) throws FXMLFileNotFoundException {
        File file = new File(path.getPath());
        if (!file.exists()) {
            throw new FXMLFileNotFoundException("No fxml file found in the given path: " + path);
        } else {
            return file;
        }
    }

}
