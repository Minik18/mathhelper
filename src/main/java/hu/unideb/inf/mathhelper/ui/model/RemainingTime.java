package hu.unideb.inf.mathhelper.ui.model;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class RemainingTime extends TimerTask {

    private Boolean hasStarted;
    private final Label label;
    private Integer seconds;

    public RemainingTime(Label label, Integer seconds) {
        this.label = label;
        this.seconds = seconds;
        this.hasStarted = false;
    }

    @Override
    public void run() {
        this.hasStarted = true;
        if (seconds > 0) {
            Platform.runLater(() -> label.setText( String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))));
            seconds--;
        } else
            this.cancel();
    }

    public boolean hasRunStarted() {
        return this.hasStarted;
    }
}
