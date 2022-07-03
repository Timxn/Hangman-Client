package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import de.oose.gameservice.api.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ResizeFeaturesBase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class WaitroomController {
    Thread housekeeper;

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
        housekeeper.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Game.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), ClientApplication.DIMENSIONS_WIDTH_HEIGHT[0], ClientApplication.DIMENSIONS_WIDTH_HEIGHT[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientApplication.stage.setTitle("HANG YOURSELF!");
        ClientApplication.stage.setScene(scene);
        ClientApplication.stage.show();
    }

    public void startGame() {
        if (api.startGame()) {
            enterGame();
        }
    }

    public void initialize() {
        output_player.setItems(FXCollections.observableArrayList());
        try {
            housekeeper = new Thread(() -> {
                while (true) {
                    try {
                        this.update();
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                    }
                }
            });
            housekeeper.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
