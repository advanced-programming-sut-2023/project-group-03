package controller;

import view.SignUpMenuCommands.*;

import java.util.regex.Matcher;

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
