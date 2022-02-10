package hu.unideb.inf.mathhelper.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class FinalController implements Controller{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button start;

    @Override
    public void setup(Stage stage) {
        start.setOnAction(event -> {
            mainPane.getChildren().clear();
        });
    }
}
