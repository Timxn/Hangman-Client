package de.oose.gameservice.gameservice_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AfterGameController {
    private void update() {}
    public void initialize() {
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(60), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}
