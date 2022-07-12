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
import static java.lang.System.exit;

public class StartController {
    Timeline tl;
    @FXML
    private Button button_create_game, button_join_game, button_close_client, button_show_high_score;
    @FXML
    private TextField input_user_name, input_gameID;
    @FXML
    private Label output_error, text_hangman;

    public void initialize() {
        tl = new Timeline(new KeyFrame(Duration.millis(60), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        updateLayout();
    }

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

    @FXML
    protected void onClose() {
        try {
            System.err.println("Client closed!");
            exit(0);
        } catch (Exception e) {
            System.err.println("Error while closing client!");
        }
    }
    @FXML
    public void onShowHighScore() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Highscores.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
        } catch (IOException e) {
            output_error.setText(e.getMessage());
        }
    }

    private void joinWaitroom() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Lobby.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
        } catch (IOException e) {
            output_error.setText("Should join waitroom. Something is really broken!");
        }
    }

    public void update() {
        updateLayout();
        if (api == null) {
            try {
                api = new Api("localhost", 8001);
            } catch (IOException e) {
                output_error.setText("Server offline");
            }
        }
    }

    public void limitInputToFourChar() {
        if(!(input_gameID.getLength() < 5)) {
            input_gameID.setText(input_gameID.getText().substring(0, input_gameID.getLength() - 1));
            input_gameID.positionCaret(input_gameID.getLength());
        }
    }
    private double width = 0.0;

    private double height = 0.0;

    private void updateLayout() {
        double tempWidth = ClientApplication.stage.getWidth();
        double tempHeight = ClientApplication.stage.getHeight();
        if (width != tempWidth || height != tempHeight) {
            width = tempWidth;
            height = tempHeight;
            text_hangman.setLayoutX(tempWidth / 2 - 360);
            text_hangman.setLayoutY(tempHeight / 2 - 200);
            input_user_name.setLayoutX(tempWidth / 2 - 140 - 150);
            input_user_name.setLayoutY(tempHeight / 2 - 70);
            input_gameID.setLayoutX(tempWidth / 2 - 140 + 150);
            input_gameID.setLayoutY(tempHeight / 2 - 70);
            button_create_game.setLayoutX(tempWidth / 2 - 290);
            button_create_game.setLayoutY(tempHeight / 2 + 30);
            button_join_game.setLayoutX(tempWidth / 2 - 140);
            button_join_game.setLayoutY(tempHeight / 2 + 30);
            button_close_client.setLayoutX(tempWidth / 2 + 160);
            button_close_client.setLayoutY(tempHeight / 2 + 30);
            button_show_high_score.setLayoutX(tempWidth / 2 + 10);
            button_show_high_score.setLayoutY(tempHeight / 2 + 30);
            output_error.setLayoutX(tempWidth / 2 - 360);
            output_error.setLayoutY(tempHeight / 2 + 100);
        }
    }
}