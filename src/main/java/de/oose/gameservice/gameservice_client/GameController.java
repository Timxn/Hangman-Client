package de.oose.gameservice.gameservice_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class GameController {
    @FXML
    ImageView output_hangman;
    @FXML
    Label output_word, output_error, output_already_guessed, output_current_player;
    @FXML
    TextField input_game_character;
    @FXML
    Button button_game_character;
    private void update() {
        JSONObject updateGame = api.updateGame();

        switch (updateGame.getInt("mistakesMade")) {
            case 1 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/01.jpg"));
            case 2 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/02.jpg"));
            case 3 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/03.jpg"));
            case 4 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/04.jpg"));
            case 5 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/05.jpg"));
            case 6 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/06.jpg"));
            case 7 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/07.jpg"));
            case 8 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/08.jpg"));
            case 9 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/09.jpg"));
        }

        JSONArray triedChars = (JSONArray) updateGame.get("characterList");
        String triedCharsString = "";
        for (int i = 0; i < triedChars.length(); i++) {
            triedCharsString = triedCharsString + " " + triedChars.get(i);
        }
        JSONArray triedWord = (JSONArray) updateGame.get("word");
        String triedWordString = "";
        for (int i = 0; i < triedWord.length(); i++) {
            triedWordString = triedWordString + " " + triedWord.get(i);
        }

        output_current_player.setText(updateGame.getString("whoseTurnIsIt"));
        output_already_guessed.setText(triedCharsString);
        output_word.setText(triedWordString);
    }

    public void onGuess() {
        char character = input_game_character.getCharacters().charAt(0);
        if (!api.isStarted() && api.isGod()) {
            ClientApplication.api.setWord(input_game_character.getText());
            try {
                ClientApplication.api.startGame();
            } catch (Exception e) {
                output_error.setText(e.getMessage());
                return;
            }
        } else if (!api.isStarted() && !api.isGod()) {
            ClientApplication.api.setWord("Wartma");
            button_game_character.setDisable(true);
        } else if (api.isStarted() && api.isGod()) {
            ClientApplication.api.setWord("Wartma");
            button_game_character.setDisable(true);
        } else if (api.isStarted() && !api.isGod()) {
            ClientApplication.api.setWord(input_game_character.getText());
        }

    }
    public void initialize() {
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(60), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }
}
