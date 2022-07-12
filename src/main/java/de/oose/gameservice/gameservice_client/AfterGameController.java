package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.gameservice_client.util.JavaFXHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AfterGameController {
    Timeline tl;
    Long errorTimer = 0L;
    Long timeToDisplayErrors = 10000L;
    @FXML
    Label output_result, output_winner, output_gameID, output_error;
    @FXML
    Button button_restart, button_quit;
    private void update() {
        try {
            if (ClientApplication.api.isStarted()) {
                JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Game.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }
        if (!output_error.getText().equals("")) if (errorTimer+ timeToDisplayErrors < System.currentTimeMillis()) output_error.setText("");
    }
    public void restart() {
        try {
            ClientApplication.api.startGame();
        } catch (Exception e) {
            // refactor
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }
    }
    public void quit() {
        try {
            ClientApplication.api.leaveGame();
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "hello-view.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
        } catch (Exception e) {
            // refactor
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }
    }
    public void initialize() {
        output_gameID.setText("The GameID is: " + ClientApplication.api.gameid);
        try {
            if ((ClientApplication.api.getWinner()).equalsIgnoreCase(ClientApplication.api.username)) output_result.setText("You won");
            else output_result.setText("You lost");
            output_winner.setText("The winner of this round is: " + ClientApplication.api.getWinner());
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }

        tl = new Timeline(new KeyFrame(Duration.millis(500), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}
