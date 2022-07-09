package de.oose.gameservice.gameservice_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static java.lang.System.exit;
import static java.lang.System.out;

public class AfterGameController {
    Timeline tl;
    @FXML
    Label output_result;
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
            output_result.setText(e.getMessage());
        }
    }
    public void restart() {
        try {
            ClientApplication.api.startGame();

        } catch (Exception e) {
            output_result.setText(e.getMessage());
        }
    }
    public void quit() {
        try {
            ClientApplication.api.close();

            // refactor this later
            tl.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Game.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1002, 699);
            ClientApplication.stage.setScene(scene);
            ClientApplication.stage.show();
        } catch (Exception e) {
            output_result.setText(e.getMessage());
        }
        exit(0);
    }
    public void initialize() {
        if (ClientApplication.api.won) output_result.setText("You won");
        else output_result.setText("You lost");
        tl = new Timeline(new KeyFrame(Duration.millis(500), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}
