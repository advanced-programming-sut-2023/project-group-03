package controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static controller.Enums.UserBasedMenuCommands.*;
import static controller.ControllerFunctions.getMatcher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UserBasedMenuController extends Controller {
    public static boolean checkUsernameNicknameFormat(String username) {
        return getMatcher(username, INVALID_USERNAME_FORMAT.getRegex()) == null;
    }
    public static boolean checkPasswordWeakness(String password) {
        if (password.length() < 6) return true;
        if (getMatcher(password, "[a-z]") == null) return true;
        if (getMatcher(password, "[A-Z]") == null) return true;
        if (getMatcher(password, "\\d") == null) return true;
        return getMatcher(password, "[^a-zA-Z0-9]") == null;
    }

    public static boolean checkEmailFormat(String email) {
        return (getMatcher(email, EMAIL_FORMAT.getRegex()) == null);
    }

    protected static ArrayList<String> securityQuestions = new ArrayList<>(Arrays.asList(
            "What is your favourite aunt's name?",
            "Which one of your grandpas do you hate the most?",
            "Do you even like this game?",
            "When did you have your first kiss?"
    ));

    protected static ArrayList<String> slogans = new ArrayList<>(Arrays.asList(
            "My army is stronger than your papa's belt.",
            "No one has guts to stop my win except whom I lose to.",
            "If I lose, this match is over.",
            "My enemies are gonna get boiled in their mama's tears.",
            "You can't run away from me. I couldn't do it neither.",
            "I'm not here to win, I'm here to conquer."
    ));

    public String randomPasswordGenerator() {
        String smallLetters = "abcdefghijklmnopqrstuvwxyz";
        String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String randomCharacters = "!@#$%^&*()-=_+[]\\|,./<>?`~";
        String allCharacters = smallLetters + capitalLetters + numbers + randomCharacters;

        ArrayList<String> characters = new ArrayList<>(Arrays.asList(smallLetters, capitalLetters, numbers, randomCharacters));

        Random randomGenerator = new Random();

        int passwordSize = randomGenerator.nextInt(4) + 6;
        String password = "";

        for (int i = 0; i < 4; i++) {
            password += characters.get(i).charAt(randomGenerator.nextInt(characters.get(i).length()));
        }

        for (int i = 0; i < passwordSize - 4; i++) {
            password += allCharacters.charAt(randomGenerator.nextInt(allCharacters.length()));
        }

        int breakPoint = randomGenerator.nextInt(passwordSize);
        password = password.substring(0, breakPoint) + password.substring(breakPoint, passwordSize);

        return password;
    }

    public String randomSloganGenerator() {
        Random randomGenerator = new Random();
        return slogans.get(randomGenerator.nextInt(slogans.size()));
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] messageDigestBytes = messageDigest.digest(password.getBytes());
        BigInteger result = new BigInteger(1, messageDigestBytes);
        return result.toString(16);
    }

    public String getEncryptedPassword(String password) {
        try {
            return encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String captchaGenerator() {
        return "";
    }
}