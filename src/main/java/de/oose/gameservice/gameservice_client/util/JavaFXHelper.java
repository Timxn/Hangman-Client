package de.oose.gameservice.gameservice_client.util;

import de.oose.gameservice.gameservice_client.ClientApplication;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JavaFXHelper {
    /**
     * @param tl Timeline that has to be stopped
     * @param stage The global stage that is the program
     * @param filename The name of the page. "Test.fxml"
     * @param width
     * @param height
     * @throws IOException
     */
    public static void enterPageWithTimeline(Timeline tl, Stage stage, String filename, double width, double height) throws IOException {
        tl.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(filename));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(ClientApplication.class.getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
