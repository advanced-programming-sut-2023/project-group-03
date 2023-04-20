package controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static controller.Enums.UserBasedMenuCommands.*;
import static controller.ControllerFunctions.getMatcher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        return getMatcher(email, EMAIL_FORMAT.getRegex()) == null;
    }

    protected static ArrayList<String> securityQuestions = new ArrayList<>(Arrays.asList(
            "What is your favourite aunt's name?",
            "Which one of your grandpas do you hate the most?",
            "Do you even like this game?",
            "When did you have your first kiss?"
    ));

    public String randomPasswordGenerator() {
        return "";
    }

    public String randomSloganGenerator() {
        return "";
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
