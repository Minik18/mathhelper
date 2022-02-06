package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.net.URL;

public interface SceneDAO {

    Scene loadScene(URL path) throws FXMLFileNotFoundException;

    Controller getController();

    VBox loadSampleQuestionPane() throws FXMLFileNotFoundException;

}
