package de.oose.gameservice.gameservice_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AfterGameController {
    Timeline tl;
    @FXML
    Label output_result, output_winner, output_gameID, output_error;
    @FXML
    Button button_restart, button_quit;
    private void update() {
        try {
            if (ClientApplication.api.isStarted()) {
                // refactor this later
                tl.stop();
                FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Game.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1002, 699);
                ClientApplication.stage.setScene(scene);
                ClientApplication.stage.show();
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }
    public void restart() {
        try {
            ClientApplication.api.startGame();

        } catch (Exception e) {
            // refactor
            output_error.setText(e.getMessage());
        }
    }
    public void quit() {
        try {
            ClientApplication.api.leaveGame();

            // refactor this later
            tl.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1186, 498);
            ClientApplication.stage.setScene(scene);
            ClientApplication.stage.show();
        } catch (Exception e) {
            // refactor
            output_winner.setText(e.getMessage());
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
        }

        tl = new Timeline(new KeyFrame(Duration.millis(500), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}
