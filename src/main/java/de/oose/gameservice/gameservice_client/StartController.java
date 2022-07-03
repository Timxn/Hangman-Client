package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class StartController {
    Thread housekeeper;
    boolean active;

    @FXML
    private Button button_create_game, button_join_game;
    @FXML
    private TextField input_user_name, input_gameID;
    @FXML
    private Label output_error;
    @FXML
    protected void onCreateGame() {
        if (api.createGame(input_user_name.getText())) {
            joinWaitroom();
        }
    }

    @FXML
    protected void onJoinGame() {
        if (api.joinGame(input_gameID.getText(), input_user_name.getText())) {
            joinWaitroom();
        }
    }

    private void joinWaitroom() {
        try {
            housekeeper.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("Waitroom.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ClientApplication.DIMENSIONS_WIDTH_HEIGHT[0], ClientApplication.DIMENSIONS_WIDTH_HEIGHT[1]);
            ClientApplication.stage.setTitle("HANG YOURSELF!");
            ClientApplication.stage.setScene(scene);
            ClientApplication.stage.show();
        } catch (IOException e) {
            output_error.setText("DAFUQ JOINWAITROOM()");
        }
    }

    public void update() {
//        try {
//            api.sendRequest("updateWaiting","null");
//        } catch (IOException e) {
//        } catch (ClassNotFoundException e) {
////            label_error.setText("Smb fucke up");
//        }
    }

    public void initialize() {
        try {
            housekeeper = new Thread(() -> {
                while (api == null) {
                    try {
                        api = new Api("localhost", 8001);
                    } catch (IOException e) {
                        output_error.setText("Mach ma Server an");
                    }
                }
                while (true) {
                    this.update();
                    try {
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