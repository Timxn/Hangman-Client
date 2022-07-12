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
    Long errorTimer = 0L;
    Long timeToDisplayErrors = 10000L;
    @FXML
    ImageView output_hangman;
    @FXML
    Label output_word, output_error, output_already_guessed, output_current_player;
    @FXML
    TextField input_game_character;
    @FXML
    Button button_game_character;

    private void update() {
        updateLayout();
        try {
            try {
                if (!api.isStarted()) api.getWinner();
            } catch (Exception e) {
                JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "Lobby.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
            }
            if (!hasWord) {
                output_word.setText("Word pending...");
                output_already_guessed.setText("Nothing guessed yet...");
                output_current_player.setText("God is thinking...");
                if (isGod) {
                    button_game_character.setText("Set Word!");
                    input_game_character.setPromptText("Word");
                } else {
                    input_game_character.setDisable(true);
                    button_game_character.setDisable(true);
                }
                hasWord = api.hasWord();
            } else {
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
                else output_current_player.setText(isTurn + " is playing!");
                output_already_guessed.setText("Already guessed: " + triedChars);
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }
        if (!output_error.getText().equals("")) if (errorTimer+ timeToDisplayErrors < System.currentTimeMillis()) output_error.setText("");
    }

    private void enterAfterGame() {
        try {
            JavaFXHelper.enterPageWithTimeline(tl, ClientApplication.stage, "AfterGame.fxml", ClientApplication.stage.getWidth(), ClientApplication.stage.getHeight());
        } catch (IOException e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
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
                errorTimer = System.currentTimeMillis();
            }
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }

    }
    public void initialize() {
        tl = new Timeline(new KeyFrame(Duration.millis(16.67), e ->update()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        updateLayout();
        try {
            isGod = api.isGod();
            if (isGod) input_game_character.setOnKeyReleased(null);
        } catch (Exception e) {
            output_error.setText(e.getMessage());
            errorTimer = System.currentTimeMillis();
        }
    }

    public void limitInputToOneChar() {
        if(!(input_game_character.getLength() ==0)) {
            input_game_character.setText(String.valueOf(input_game_character.getText().charAt(input_game_character.getLength() - 1)));
            input_game_character.positionCaret(input_game_character.getLength());
        }
    }

    private double width = 0.0;

    private double height = 0.0;

    private void updateLayout() {
        double tempWidth = ClientApplication.stage.getWidth();
        double tempHeight = ClientApplication.stage.getHeight();
        if (width != tempWidth || height != tempHeight) {
            width = tempWidth;
            height = tempHeight;
            output_hangman.setLayoutX(tempWidth / 2 - 300 - 50);
            output_hangman.setLayoutY(tempHeight / 2 - 150);
            output_word.setLayoutX(tempWidth / 2 - 360);
            output_word.setLayoutY(tempHeight / 2 - 240);
            input_game_character.setLayoutX(tempWidth / 2 + 100);
            input_game_character.setLayoutY(tempHeight / 2 - 60);
            button_game_character.setLayoutX(tempWidth / 2 + 100);
            button_game_character.setLayoutY(tempHeight / 2 + 20);
            output_current_player.setLayoutX(tempWidth / 2 + 90);
            output_current_player.setLayoutY(tempHeight / 2 - 100);
            output_already_guessed.setLayoutX(tempWidth / 2 - 360);
            output_already_guessed.setLayoutY(tempHeight / 2 - 180);
            output_error.setLayoutX(tempWidth / 2 - 360);
            output_error.setLayoutY(tempHeight / 2 + 140);
        }
    }
}
