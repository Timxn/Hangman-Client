package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.gameservice_client.util.JavaFXHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class LobbyController {
    Timeline tl;
    @FXML
    Label output_gameID, output_error;
    @FXML
    Button start_game;
    @FXML
    ListView<Object> output_player;
    private void update() {
        updateLayout();
        JSONObject response;
        try {
            response = api.updateLobby();
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            return;
        }
        if (response.getBoolean("isStarted")) enterGame();
        output_gameID.setText(response.getString("gameID"));
        ArrayList<String> tmp = new ArrayList<>();
        for (int i = 0; i < response.getJSONArray("userList").length(); i++) {
            tmp.add(response.getJSONArray("userList").getString(i));
        }
        output_player.getItems().setAll(tmp);
    }

    private void enterGame() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Game.fxml", 1002, 699);
        } catch (IOException e) {
            output_error.setText(e.getMessage());
        }
    }

    public void quit() {
        try {
            ClientApplication.api.leaveGame();
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "hello-view.fxml", 1176, 498);
        } catch (Exception e) {
            // refactor
            output_error.setText(e.getMessage());
        }
    }

    public void startGame() {
        try {
            api.startGame();
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }

    public void initialize() {
        output_player.setItems(FXCollections.observableArrayList());
        tl = new Timeline(new KeyFrame(Duration.millis(16.67), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        updateLayout();
    }

    private double width = 0.0;

    private double height = 0.0;

    private void updateLayout() {
        double tempWidth = ClientApplication.stage.getWidth();
        double tempHeight = ClientApplication.stage.getHeight();
        if (width != tempWidth || height != tempHeight) {
            width = tempWidth;
            height = tempHeight;

            output_error.setLayoutX(tempWidth / 2 - 360);
            output_error.setLayoutY(tempHeight / 2 + 100);
        }

    }
}
