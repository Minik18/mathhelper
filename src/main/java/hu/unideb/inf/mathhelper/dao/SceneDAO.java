package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.controller.Controller;
import hu.unideb.inf.mathhelper.exception.SceneNotFoundException;
import javafx.scene.Scene;

public interface SceneDAO {

    Scene loadScene(String path, String fileName) throws SceneNotFoundException;

    Controller getController();

}
