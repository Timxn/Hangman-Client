package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.api.Api;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class GameController {
    Thread housekeeper;

    @FXML
    ImageView output_hangman;
    @FXML
    Label output_word, output_error, output_already_guessed, output_current_player;
    @FXML
    TextField input_game_character;
    @FXML
    Button button_game_character;
    private void update() {
        switch (api.getFails()) {
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
        // case 0 -> bild.setImage(new Image(HelloApplication.class.getResource("") + "media/1.png"));

    }

    public void onGuess() {
        char character = input_game_character.getCharacters().charAt(0);
        if (ClientApplication.api.isGod() && !ClientApplication.api.isStarted()) {
            ClientApplication.api.setWord(input_game_character.getText());
            ClientApplication.api.startGame();
        }
    }
}
