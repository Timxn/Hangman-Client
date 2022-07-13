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
        updateLayout();
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
        output_gameID.setText("Game ID: " + ClientApplication.api.gameid);
        updateLayout();
        try {
            if ((ClientApplication.api.getWinner()).equalsIgnoreCase(ClientApplication.api.username)) output_result.setText("You won");
            else output_result.setText("You lost");
            output_winner.setText("The winner is: " + ClientApplication.api.getWinner());
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }

        tl = new Timeline(new KeyFrame(Duration.millis(500), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    private double width = 0.0;

    private double height = 0.0;

    private void updateLayout() {
        double tempWidth = ClientApplication.stage.getWidth();
        double tempHeight = ClientApplication.stage.getHeight();
        if (width != tempWidth || height != tempHeight) {
            width = tempWidth;
            height = tempHeight;
            output_result.setLayoutX(tempWidth / 2 - 360);
            output_result.setLayoutY(tempHeight / 2 - 250);
            output_winner.setLayoutX(tempWidth / 2 - 360);
            output_winner.setLayoutY(tempHeight / 2 - 100);
            button_restart.setLayoutX(tempWidth / 2 - 140 - 150);
            button_restart.setLayoutY(tempHeight / 2);
            button_quit.setLayoutX(tempWidth / 2 - 140 + 150);
            button_quit.setLayoutY(tempHeight / 2);
            output_gameID.setLayoutX(tempWidth / 2 - 150);
            output_gameID.setLayoutY(tempHeight / 2 + 100);
            output_error.setLayoutX(tempWidth / 2 - 360);
            output_error.setLayoutY(tempHeight / 2 + 140);
        }
    }
}
