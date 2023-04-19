package controller;

import java.util.ArrayList;
import java.util.Arrays;

import static controller.UserBasedMenuCommands.*;
import static controller.ControllerFunctions.getMatcher;

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

    private ArrayList<String> securityQuestions = new ArrayList<>(Arrays.asList(
            "What is your favourite aunt's name?",
            "Which one of your grandpas do you hate the most?",
            "Do you even like this game?",
            "When did you have your first kiss?"
    ));

    public ArrayList<String> getSecurityQuestions() {
        return securityQuestions;
    }

    public String randomPasswordGenerator() {
        return "";
    }

    public String randomSloganGenerator() {
        return "";
    }

    public String captchaGenerator() {
        return "";
    }
}
