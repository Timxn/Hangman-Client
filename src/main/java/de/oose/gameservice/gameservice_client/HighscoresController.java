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
import java.io.IOException;
import java.util.List;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class HighscoresController {
    Timeline tl;
    @FXML
    Label output_error;
    @FXML
    Button back;
    @FXML
    ListView<Object> output_highscore;
    private void update() {
        updateLayout();
        List<String> response;
        try {
            response = api.getScoreboard();
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            return;
        }
        output_highscore.getItems().setAll(response);

    }

    @FXML
    private void back() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "hello-view.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
        } catch (IOException e) {
            output_error.setText(e.getMessage());
        }
    }

    public void initialize() {
        output_highscore.setItems(FXCollections.observableArrayList());
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
            output_highscore.setLayoutX(tempWidth / 2 - 140 - 150);
            output_highscore.setLayoutY(tempHeight / 2 - 50);
            back.setLayoutX(tempWidth / 2 + 10);
            back.setLayoutY(tempHeight / 2 - 60 + 10);
            output_error.setLayoutX(tempWidth / 2 - 360);
            output_error.setLayoutY(tempHeight / 2 + 100);
        }
    }
}