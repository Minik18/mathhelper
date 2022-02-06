package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WelcomeSceneController implements Controller {

    @Autowired
    private UserHandleService userHandleService;

    @Autowired
    private SceneDAO sceneDAO;

    @Autowired
    private LocationDAO locationDAO;

    @FXML
    public Text errorText;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    private TextField userInput;

    @FXML
    private Button submit;

    @Override
    public void setup(Stage stage) {
        submit.setOnMouseClicked(event ->
        {
            String text = userInput.getText();
            if (isValid(text)) {
                userHandleService.updateNickname(text);
                loadMainScene(stage);
            } else {
                errorText.setVisible(true);
            }
        });
    }

    private void loadMainScene(Stage stage) {
        try {
            Stage newStage = new Stage();
            Scene scene = sceneDAO.loadScene(locationDAO.getSceneFilePath("main.fxml"));
            Controller controller = sceneDAO.getController();
            stage.close();
            newStage.setScene(scene);
            controller.setup(newStage);
            newStage.show();
        } catch (FXMLFileNotFoundException e) {
            //TODO
            e.printStackTrace();
        }
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
