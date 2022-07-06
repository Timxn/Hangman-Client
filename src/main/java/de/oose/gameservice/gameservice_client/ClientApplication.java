package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class ClientApplication extends Application {
    public static Api api;
    public static Stage stage;
    public static final int[] DIMENSIONS_WIDTH_HEIGHT = {1186, 498};

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DIMENSIONS_WIDTH_HEIGHT[0], DIMENSIONS_WIDTH_HEIGHT[1]);
        this.stage.setTitle("HANG YOURSELF!");
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void close() {
        api.close();
        exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}