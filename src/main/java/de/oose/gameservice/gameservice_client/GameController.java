package de.oose.gameservice.gameservice_client;

import de.oose.gameservice.gameservice_client.util.InputValidation;
import de.oose.gameservice.gameservice_client.util.JavaFXHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.IOException;

import static de.oose.gameservice.gameservice_client.ClientApplication.api;

public class GameController {
    boolean isGod, hasWord;
    String isTurn = "";
    Timeline tl;
    @FXML
    ImageView output_hangman;
    @FXML
    Label output_word, output_error, output_already_guessed, output_current_player, info_isPlaying;
    @FXML
    TextField input_game_character;
    @FXML
    Button button_game_character;
    private void update() {
        try {
            try {
                if (!api.isStarted()) api.getWinner();
            } catch (Exception e) {
                JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Lobby.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
            }
            if (!hasWord) {
                if (isGod) {
                    button_game_character.setText("enter word!");
                    input_game_character.setPromptText("word");
                } else {
                    input_game_character.setDisable(true);
                    button_game_character.setDisable(true);
                }
                hasWord = api.hasWord();
            } else {
                info_isPlaying.setText("is playing");
                JSONObject updateGame = api.updateGame();
                isTurn = updateGame.getString("whoseTurnIsIt");
                if (isGod) {
                    input_game_character.setDisable(true);
                    button_game_character.setDisable(true);
                } else {
                    input_game_character.setDisable(false);
                    button_game_character.setDisable(false);
                }
                button_game_character.setDisable(!isTurn.equalsIgnoreCase(api.username)); // if user it is not the players turn, disable the button to guess

                switch (updateGame.getInt("mistakesMade")) {
                    case 1 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/01.jpg"));
                    case 2 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/02.jpg"));
                    case 3 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/03.jpg"));
                    case 4 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/04.jpg"));
                    case 5 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/05.jpg"));
                    case 6 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/06.jpg"));
                    case 7 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/07.jpg"));
                    case 8 -> output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/08.jpg"));
                    case 9 -> {
                        output_hangman.setImage(new Image(ClientApplication.class.getResource("") + "images/09.jpg"));
//                        wait(1000);
                        enterAfterGame();
                    }
                }

                if (updateGame.getBoolean("wordIsGuessed")) {
                    enterAfterGame();
                }

                output_word.setText(updateGame.getString("word"));

                String triedChars = updateGame.getString("characterList");

                if (isTurn.equals("ERROR")) output_error.setText("No ones Turn");
                else output_current_player.setText(isTurn);
                output_already_guessed.setText(triedChars);
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }

    private void enterAfterGame() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "AfterGame.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
        } catch (IOException e) {
            output_error.setText(e.getMessage());
        }
    }

    public void onGuess() {
        try {
            if (isGod) {
                api.setWord(InputValidation.validateOnlyAlphabeticalChars(input_game_character.getText()));
                button_game_character.setDisable(true);
            } else if (isTurn.equalsIgnoreCase(api.username)){
                api.guessLetter(String.valueOf(InputValidation.getFirstCharAsLowerCaseAlphabeticalChar(input_game_character.getText())));
                input_game_character.setText(null);
            } else {
                output_error.setText("Not your turn!");
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }

    }
    public void initialize() {
        tl = new Timeline(new KeyFrame(Duration.seconds(1), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        try {
            isGod = api.isGod();
            if (isGod) input_game_character.setOnKeyReleased(null);
        } catch (Exception e) {
            output_error.setText(e.getMessage());
        }
    }

    public void limitInputToOneChar() {
        if(!(input_game_character.getLength() ==0)) {
            input_game_character.setText(String.valueOf(input_game_character.getText().charAt(input_game_character.getLength() - 1)));
            input_game_character.positionCaret(input_game_character.getLength());
        }

    }
}
