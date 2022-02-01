package hu.unideb.inf.mathhelper.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Component
public class LegalController implements Controller{

    @FXML
    public Hyperlink contactLink;

    @FXML
    private Hyperlink uniLink;

    @FXML
    private Hyperlink picturesLink;

    @FXML
    private Hyperlink mathLink;

    @Override
    public void setup(@Nullable Stage stage) {
        setupLink(uniLink, uniLink.getAccessibleText());
        setupLink(picturesLink, picturesLink.getAccessibleText());
        setupLink(mathLink, mathLink.getAccessibleText());
        setupLink(contactLink, contactLink.getAccessibleText());
    }

    private void setupLink(Hyperlink link, String site) {
        link.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(URI.create(site));
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
        });
        link.setBorder(null);
    }
}
