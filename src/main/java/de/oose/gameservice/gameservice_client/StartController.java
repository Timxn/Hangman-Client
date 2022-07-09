package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class StartController {
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
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Waitroom.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 864, 569);
//            Scene scene = new Scene(fxmlLoader.load(), ClientApplication.DIMENSIONS_WIDTH_HEIGHT[0], ClientApplication.DIMENSIONS_WIDTH_HEIGHT[1]);
            ClientApplication.stage.setTitle("HANG YOURSELF!");
            ClientApplication.stage.setScene(scene);
            ClientApplication.stage.show();
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
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(60), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}