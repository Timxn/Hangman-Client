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
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1186, 498);
        this.stage.setTitle("HANG YOURSELF!");
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setOnCloseRequest(e -> close());
    }

    private void close() {
        try {
            api.close();
            exit(0);
        } catch (Exception e) {
            System.err.println("sucks to be you");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}