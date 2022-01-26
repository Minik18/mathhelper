package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.ui.controller.Controller;
import hu.unideb.inf.mathhelper.exception.SceneNotFoundException;
import javafx.scene.Scene;

import java.net.URL;

public interface SceneDAO {

    Scene loadScene(URL path) throws SceneNotFoundException;

    Controller getController();

}
