package de.oose.gameservice.gameservice_client.util;

import de.oose.gameservice.gameservice_client.ClientApplication;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXHelper {
    /**
     * @param stage The global stage that is the program
     * @param filename The name of the page. "Test.fxml"
     * @param width
     * @param height
     * @throws IOException
     */
    public static void enterPage(Stage stage, String filename, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(filename));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param tl Timeline that has to be stopped
     * @param stage The global stage that is the program
     * @param filename The name of the page. "Test.fxml"
     * @param width
     * @param height
     * @throws IOException
     */
    public static void enterPageWithTimeline(Timeline tl, Stage stage, String filename, int width, int height) throws IOException {
        tl.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(filename));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        stage.show();
    }
}
