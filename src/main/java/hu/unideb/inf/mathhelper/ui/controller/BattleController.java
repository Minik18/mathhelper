package hu.unideb.inf.mathhelper.ui.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class BattleController implements PanelController{

    @FXML
    private ImageView equation;

    @FXML
    private Rectangle from;

    @FXML
    private Rectangle to;

    @Override
    public void setup() {
        play();
    }

    private void play() {
        equation.setVisible(true);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(equation);
        transition.setDuration(Duration.millis(2000));
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setByY(100);
        transition.play();

    }
}
