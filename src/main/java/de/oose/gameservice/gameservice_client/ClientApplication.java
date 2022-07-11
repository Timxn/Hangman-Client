package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class ClientApplication extends Application {
    public static Api api;
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        ClientApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientApplication.stage.setTitle("Multiplayer Hangman");
        ClientApplication.stage.setScene(scene);
        ClientApplication.stage.setMinWidth(720);
        ClientApplication.stage.setMinHeight(480);
        ClientApplication.stage.setMaximized(true);
        ClientApplication.stage.show();
        ClientApplication.stage.setOnCloseRequest(e -> close());
    }

    private void close() {
        try {
            api.close();
            exit(0);
        } catch (Exception e) {
            System.err.println("Client closed!");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}