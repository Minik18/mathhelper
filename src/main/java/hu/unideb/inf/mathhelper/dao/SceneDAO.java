package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.SceneController;
import javafx.scene.Scene;

public interface SceneDAO {

    Scene loadScene(String path) throws FXMLFileNotFoundException;

    SceneController getController();

}
