package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.ui.controller.PanelController;
import hu.unideb.inf.mathhelper.ui.controller.SceneController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public interface PanelDAO {

    BorderPane loadSampleQuestionPane() throws FXMLFileNotFoundException;

    AnchorPane loadPanel(String fileName) throws FXMLFileNotFoundException;

    PanelController getController();

}
