package hu.unideb.inf.mathhelper.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Locale;

public class WelcomeSceneController implements Controller{

    @FXML
    private TextField userInput;

    @FXML
    private Button submit;

    @Override
    public void setup() {
        submit.setOnMouseClicked(event ->
        {
            String text = userInput.getText();
            if (isValid(text)) {
                loadMainScene();
            }
        });
    }

    private void loadMainScene() {
        System.out.println("Clicked");
    }

    private boolean isValid(String text) {
        boolean result = true;
        if (text.toLowerCase(Locale.ROOT).equals("default")) {
            result = false;
        } else if (text.replaceAll(" ","").equals("")) {
            result = false;
        }
        return result;
    }
}
