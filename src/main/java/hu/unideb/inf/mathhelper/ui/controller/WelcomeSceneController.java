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
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WelcomeSceneController implements SceneController {

    private final UserHandleService userHandleService;
    private final SceneDAO sceneDAO;
    private final LocationDAO locationDAO;

    @FXML
    public Text errorText;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    private TextField userInput;

    @FXML
    private Button submit;

    @Autowired
    public WelcomeSceneController(UserHandleService userHandleService,
                                  SceneDAO sceneDAO, LocationDAO locationDAO) {
        this.userHandleService = userHandleService;
        this.sceneDAO = sceneDAO;
        this.locationDAO = locationDAO;
    }


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
            SceneController sceneController = sceneDAO.getController();
            stage.close();
            newStage.setScene(scene);
            sceneController.setup(newStage);
            newStage.setResizable(false);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setMaximized(true);
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
        } else if (text.replaceAll(" ", "").equals("")) {
            result = false;
        }
        return result;
    }
}
