package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WelcomeSceneController implements Controller{

    @FXML
    private TextField userInput;

    @FXML
    private Button submit;

    @Autowired
    private UserHandleService userHandleService;

    @Override
    public void setup() {
        submit.setOnMouseClicked(event ->
        {
            String text = userInput.getText();
            if (isValid(text)) {
                userHandleService.updateNickname(text);
                loadMainScene();
            }
        });
    }

    private void loadMainScene() {
        //TODO
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
