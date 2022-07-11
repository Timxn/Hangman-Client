package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import de.oose.gameservice.gameservice_client.util.JavaFXHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class StartController {
    Timeline tl;
    @FXML
    private Button button_create_game, button_join_game;
    @FXML
    private TextField input_user_name, input_gameID;
    @FXML
    private Label output_error;
    @FXML
    protected void onCreateGame() {
        try {
            if (!input_user_name.getText().isBlank() && api.createGame(input_user_name.getText())) {
                joinWaitroom();
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }

    @FXML
    protected void onJoinGame() {
        try {
            if (!input_user_name.getText().isBlank() && api.joinGame(input_gameID.getText(), input_user_name.getText())) {
                joinWaitroom();
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }

    private void joinWaitroom() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Lobby.fxml", 864, 569);
        } catch (IOException e) {
            output_error.setText("DAFUQ JOINWAITROOM()");
        }
    }

    public void update() {
        if (api == null) {
            try {
                api = new Api("localhost", 8001);
            } catch (IOException e) {
                output_error.setText("Mach ma Server an");
            }
        }
    }

    public void initialize() {
        tl = new Timeline(new KeyFrame(Duration.millis(60), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    public void limitInputToFourChar() {
        if(!(input_gameID.getLength() < 5)) {
            input_gameID.setText(String.valueOf(input_gameID.getText().substring(0,input_gameID.getLength() - 1)));
            input_gameID.positionCaret(input_gameID.getLength());
        }
    }
}