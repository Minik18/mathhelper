package hu.unideb.inf.mathhelper.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class SettingsSceneController implements PanelController {

    private static final List<String> ALLOWED_PICTURE_EXTENSIONS = List.of("png","jpg",".ico");

    @FXML
    private Button browse;

    @FXML
    private Text pictureErrorText;

    @Override
    public void setup() {
        browse.setOnMouseClicked(event -> {
            Stage newStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(newStage);
            if (file != null) {
                String fileName = FilenameUtils.getName(file.getPath());
                if (ALLOWED_PICTURE_EXTENSIONS.contains(FilenameUtils.getExtension(fileName))) {
                    browse.setText(fileName);
                } else {
                    pictureErrorText.setVisible(true);
                }
            }
        });
    }
}
