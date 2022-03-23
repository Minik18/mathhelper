package hu.unideb.inf.mathhelper.ui.model;

import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class RemainingTime extends TimerTask {

    private Boolean hasStarted;
    private final Label label;
    private Integer seconds;
    private final PlayerObserver playerObserver;

    public RemainingTime(Label label, Integer seconds, PlayerObserver playerObserver) {
        this.label = label;
        this.seconds = seconds;
        this.hasStarted = false;
        this.playerObserver = playerObserver;
    }

    @Override
    public void run() {
        this.hasStarted = true;
        if (seconds > 0) {
            Platform.runLater(() -> label.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))));
            seconds--;
        } else {
            playerObserver.timerStopped();
            this.cancel();
        }
    }

    public boolean hasRunStarted() {
        return this.hasStarted;
    }
}
