package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.SceneController;
import javafx.scene.Scene;

import java.net.URL;

public interface SceneDAO {

    Scene loadScene(URL path) throws FXMLFileNotFoundException;

    SceneController getController();

}
