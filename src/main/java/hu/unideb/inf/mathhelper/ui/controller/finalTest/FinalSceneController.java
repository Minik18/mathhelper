package hu.unideb.inf.mathhelper.ui.controller.finalTest;

import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.ui.controller.PanelController;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinalSceneController implements PanelController {

    private final PanelDAO panelDAO;
    private final PlayerObserver playerObserver;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button start;

    @Autowired
    public FinalSceneController(PanelDAO panelDAO, PlayerObserver playerObserver) {
        this.panelDAO = panelDAO;
        this.playerObserver = playerObserver;
    }

    @Override
    public void setup() {
        playerObserver.setFinalSceneController(this);
        start.setOnAction(event -> {
            playerObserver.startOfFinalTest();
            load("finalTest.fxml");
        });

    }

    public void finishTest() {
        load("finalTestResult.fxml");
    }

    private void load(String name) {
        try {
            mainPane.getChildren().clear();
            AnchorPane loadedRoot = panelDAO.loadPanel(name);
            AnchorPane.setBottomAnchor(loadedRoot, 0.0);
            AnchorPane.setTopAnchor(loadedRoot, 0.0);
            AnchorPane.setLeftAnchor(loadedRoot, 0.0);
            AnchorPane.setRightAnchor(loadedRoot, 0.0);

            panelDAO.getController().setup();
            mainPane.getChildren().add(loadedRoot);
        } catch (FXMLFileNotFoundException e) {
            AppLogger.logError(e);
        }
    }
}
