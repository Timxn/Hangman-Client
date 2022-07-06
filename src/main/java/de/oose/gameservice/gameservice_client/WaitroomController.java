package de.oose.gameservice.gameservice_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class WaitroomController {
    @FXML
    Label output_gameID, output_error;
    @FXML
    Button start_game;
    @FXML
    ListView output_player;
    private void update() {
        JSONObject response = api.updateWaitroom();
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
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Game.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1002, 699);
//            Scene scene = new Scene(fxmlLoader.load(), ClientApplication.DIMENSIONS_WIDTH_HEIGHT[0], ClientApplication.DIMENSIONS_WIDTH_HEIGHT[1]);
            ClientApplication.stage.setTitle("HANG YOURSELF!");
            ClientApplication.stage.setScene(scene);
            ClientApplication.stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            output_error.setText("DAFUQ JOINWAITROOM()");
        }
    }

    public void startGame() {
        try {
            if (api.startGame()) {
                enterGame();
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }

    public void initialize() {
        output_player.setItems(FXCollections.observableArrayList());
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(16.67), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}
