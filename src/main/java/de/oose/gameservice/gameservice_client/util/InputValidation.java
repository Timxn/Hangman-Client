package de.oose.gameservice.gameservice_client.util;

public class InputValidation {
    public static String validateOnlyAlphabeticalChars(String input) throws Exception {
        if (input.matches("[^a-zA-Z]")) throw new Exception("Input is malformed, only alphabetical chars are allowed.");
        return input;
    }

    public static char getFirstCharAsLowerCaseAlphabeticalChar(String input) throws Exception {
        if (input == null) throw new Exception("Input is empty");
        if (input.isBlank()) throw new Exception("Input is empty");
        char output = input.charAt(0);
        output = Character.toLowerCase(output);
        if (!Character.isAlphabetic(output)) throw new Exception("Input is malformed!");
        return output;
    }
}
