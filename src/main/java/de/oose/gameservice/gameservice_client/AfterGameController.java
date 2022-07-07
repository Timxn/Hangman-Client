package de.oose.gameservice.gameservice_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static java.lang.System.exit;

public class AfterGameController {
    @FXML
    Label output_result;
    @FXML
    Button button_restart, button_quit;
    private void update() {}

    public void restart() {}
    public void quit() {exit(0);}
    public void initialize() {
        if (ClientApplication.api.won) output_result.setText("You won");
        else output_result.setText("You lost");
//        Timeline tl = new Timeline(new KeyFrame(Duration.millis(60), e ->update()));
//        tl.setCycleCount(Timeline.INDEFINITE);
//        tl.play();
    }
}
