package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.PanelDAO;
import hu.unideb.inf.mathhelper.dao.SettingsDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstBossOpenController implements PanelController {

    private final SettingsDAO settingsDAO;
    private final PanelDAO panelDAO;

    @FXML
    private Label first;

    @FXML
    private Label second;

    @FXML
    private Label third;

    @FXML
    private Label fourth;

    @FXML
    private Label last;

    @FXML
    private Button start;

    @FXML
    private AnchorPane root;

    @Autowired
    public FirstBossOpenController(SettingsDAO settingsDAO, PanelDAO panelDAO) {
        this.settingsDAO = settingsDAO;
        this.panelDAO = panelDAO;
    }

    @Override
    public void setup() {
        first.setOpacity(0.0);
        second.setOpacity(0.0);
        third.setOpacity(0.0);
        fourth.setOpacity(0.0);
        last.setOpacity(0.0);
        start.setDisable(true);

        FadeTransition firstFade = new FadeTransition(Duration.seconds(2), first);
        firstFade.setFromValue(0.0);
        firstFade.setToValue(1.0);
        firstFade.setOnFinished(event -> {
            FadeTransition secondFade = new FadeTransition(Duration.seconds(3), second);
            secondFade.setFromValue(0.0);
            secondFade.setToValue(1.0);
            secondFade.setOnFinished(event2 -> {
                FadeTransition thirdFade = new FadeTransition(Duration.seconds(6), third);
                thirdFade.setFromValue(0.0);
                thirdFade.setToValue(1.0);
                thirdFade.setOnFinished(event3 -> {
                    FadeTransition fourthFade = new FadeTransition(Duration.seconds(6), fourth);
                    fourthFade.setFromValue(0.0);
                    fourthFade.setToValue(1.0);
                    fourthFade.setOnFinished(event4 -> {
                        FadeTransition lastFade = new FadeTransition(Duration.seconds(6), last);
                        lastFade.setFromValue(0.0);
                        lastFade.setToValue(1.0);
                        lastFade.setOnFinished(event5 -> start.setDisable(false));
                        lastFade.play();
                    });
                    fourthFade.play();
                });
                thirdFade.play();
            });
            secondFade.play();
        });
        firstFade.play();


        start.setOnMouseClicked(event -> {
            settingsDAO.changeFirstOpenBossFight(false);
            root.getChildren().clear();
            try {
                AnchorPane loaded = panelDAO.loadPanel("battle.fxml");
                AnchorPane.setBottomAnchor(loaded, 0.0);
                AnchorPane.setTopAnchor(loaded, 0.0);
                AnchorPane.setRightAnchor(loaded, 0.0);
                AnchorPane.setLeftAnchor(loaded, 0.0);
                root.getChildren().add(loaded);
                panelDAO.getController().setup();
            } catch (FXMLFileNotFoundException e) {
                //TODO
                e.printStackTrace();
            }
        });

    }
}
