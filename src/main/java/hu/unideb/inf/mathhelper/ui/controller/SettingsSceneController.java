package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SettingsDAO;
import hu.unideb.inf.mathhelper.exception.InvalidUsernameException;
import hu.unideb.inf.mathhelper.log.AppLogger;
import hu.unideb.inf.mathhelper.model.Settings;
import hu.unideb.inf.mathhelper.model.UserData;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Component
public class SettingsSceneController implements PanelController {

    private static final List<String> ALLOWED_PICTURE_EXTENSIONS = List.of("png", "jpg", ".ico");
    private final SettingsDAO settingsDAO;
    private final UserHandleService userHandleService;
    private final PlayerObserver playerObserver;
    private final LocationDAO locationDAO;

    @FXML
    private Button browse;

    @FXML
    private Text pictureErrorText;

    @FXML
    private MenuButton menuBar;

    @FXML
    private MenuItem yesButton;

    @FXML
    private MenuItem noButton;

    @FXML
    private Button nameSubmit;

    @FXML
    private Button reset;

    @FXML
    private Button pictureSubmit;

    @FXML
    private TextField nameField;

    @Autowired
    public SettingsSceneController(SettingsDAO settingsDAO,
                                   UserHandleService userHandleService, PlayerObserver playerObserver, LocationDAO locationDAO) {
        this.settingsDAO = settingsDAO;
        this.userHandleService = userHandleService;
        this.playerObserver = playerObserver;
        this.locationDAO = locationDAO;
    }

    @Override
    public void setup() {
        browse.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(newStage);
            if (file != null) {
                pictureSubmit.setOnMouseClicked(event1 -> {
                    UserData userData = userHandleService.getUserData();
                    File currentPicture = new File(locationDAO.getProfilePictureFilePath(userData.getProfilePictureName()));
                    if (!file.getPath().equals(currentPicture.getPath())) {
                        try {
                            Path source = Path.of(file.getPath());
                            File folder = new File(locationDAO.getProfilePictureFolderPath());
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                            Path destination = Path.of(locationDAO.getProfilePictureFilePath(file.getName()));
                            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            AppLogger.logError(e);
                        }
                        String newText = "Tallózás";
                        browse.setText(newText);
                        userHandleService.updateProfilePicture(file.getName());
                        playerObserver.updateUserInformation();
                    }
                });
                String fileName = FilenameUtils.getName(file.getPath());
                if (ALLOWED_PICTURE_EXTENSIONS.contains(FilenameUtils.getExtension(fileName))) {
                    browse.setText(fileName);
                } else {
                    pictureErrorText.setVisible(true);
                }
            }
        });

        nameSubmit.setOnMouseClicked(event1 -> {
            try {
                userHandleService.updateNickname(nameField.getText());
                playerObserver.updateUserInformation();
                nameField.clear();
            } catch (InvalidUsernameException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Hiba");
                alert.setHeaderText("Felhasználónév probléma");
                alert.setContentText("A megadott felhasználónév nem helyes. Legyen legalább 5, de maximum 60 karakter hosszú!");

                alert.showAndWait();
            }
        });

        reset.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Megerősítés");
            alert.setHeaderText("Felhasználói adatok törlése");
            alert.setContentText("Biztosan törölni szeretnéd a felhasználói adataidat? Az összes pontod, szinted elfog veszni!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get().equals(ButtonType.OK)) {
                userHandleService.resetUserData();
                playerObserver.updateUserInformation();
                settingsDAO.changeAllBossesDefeated(false);
            }
        });

        Settings settings = settingsDAO.getSettings();

        menuBar.setText(settings.isUseSolvedQuestions() ? yesButton.getText() : noButton.getText());

        yesButton.setOnAction(event -> {
            menuBar.setText(yesButton.getText());
            settingsDAO.changeShowSolvedQuestions(true);
        });

        noButton.setOnAction(event -> {
            menuBar.setText(noButton.getText());
            settingsDAO.changeShowSolvedQuestions(false);
        });

    }
}
