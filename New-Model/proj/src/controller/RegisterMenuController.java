package controller;

import Model.User;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.SignUpMenuCommands.*;
import static controller.Enums.ControllerCommands.*;
import static controller.Enums.Response.*;
import static controller.ControllerFunctions.*;
import static view.SignUpMenu.getRandomPasswordConfirmation;
import static view.SignUpMenu.showRandomSlogan;
import static Model.UserDatabase.*;

public class RegisterMenuController extends UserBasedMenuController {

    HashMap<String, String> infoMap = new HashMap() {{
        put("u", null);
        put("p", null);
        put("e", null);
        put("n", null);
        put("s", null);
    }};

    public String registerNewUser(Matcher inputMatcher, Scanner scanner) {
        //check all the problems that can occur for the command and return a proper String to menu to see what happened
        boolean randomPassword = false; // check if user wants a random password and if he wants it, no need to check the confirmation.
        String userInfo = inputMatcher.group("userInfo");
        Matcher matcher = getMatcher(userInfo, OPTION_FIELD.getRegex());
        if (matcher == null) return "Invalid command!";

        //check the format of user info
        Matcher registerFormatCheckerMatcher = getMatcher(userInfo, NEW_USER_FORMAT_CHECK.getRegex());
        if (registerFormatCheckerMatcher == null) return "Invalid command!";
        if (!(registerFormatCheckerMatcher.end() == userInfo.length())) return "Invalid command!";

        String option = "";
        String optionInfo = "";
        do {
            option = matcher.group("option");
            optionInfo = matcher.group("optionInfo");
            if (!infoMap.containsKey(option)) return INVALID_OPTION.getOutput();
            if (infoMap.get(option) != null) return REPETITIVE_OPTION.getOutput() + option;
            if (optionInfo == null) {
                if (option.equals("u")) return USERNAME_EMPTY.getOutput();
                if (option.equals("p")) return PASSWORD_EMPTY.getOutput();
                if (option.equals("n")) return NICKNAME_EMPTY.getOutput();
                if (option.equals("e")) return EMAIL_EMPTY.getOutput();
                if (option.equals("s")) return SLOGAN_EMPTY.getOutput();
            }
            if (option.equals("p") && optionInfo.equals("random")) {
                optionInfo = randomPasswordGenerator();
                randomPassword = true;
            }
            while (!getRandomPasswordConfirmation(optionInfo, scanner).equals(optionInfo)) {
                optionInfo = randomPasswordGenerator();
            }

            if (option.equals("s") && optionInfo.equals("random")) optionInfo = randomSloganGenerator();
            while (showRandomSlogan(optionInfo, scanner).equals("n")) {
                optionInfo = randomSloganGenerator();
            }

            optionInfo = unwrapQuotation(optionInfo);

            infoMap.put(option, optionInfo);
        } while (matcher.matches());
        
        //check if input misses an option
        for (String key : infoMap.keySet()) {
            if (!key.equals("s") && infoMap.get(key) == null) 
                return "You forgot this key: " + key;
        }

        if (!checkUsernameNicknameFormat(infoMap.get("u"))) return INVALID_USERNAME_FORMAT.getOutput();
        //check if there is a player with this username
        if (getUserByName(infoMap.get("u")) != null) return REPETITIVE_USERNAME.getOutput();
        
        if (!checkUsernameNicknameFormat(infoMap.get("n"))) return INVALID_NICKNAME_FORMAT.getOutput();

        if (checkPasswordWeakness(infoMap.get("p"))) return WEAK_PASSWORD.getOutput();
        Matcher passwordConfirmationMatcher = getMatcher(userInfo, PASSWORD_CONFIRMATION.getRegex());
        if (!randomPassword) {
            if (passwordConfirmationMatcher == null) return PASSWORD_CONFIRMATION_EMPTY.getOutput();
            if (!passwordConfirmationMatcher.group("password").equals(passwordConfirmationMatcher.group("passwordConfirmation"))) {
                return WRONG_PASSWORD_CONFIRMATION.getOutput();
            }
        }

        //check if there is a player with this email
        if (getUserByEmail(infoMap.get("e")) != null) return REPETITIVE_EMAIL.getOutput();
        
        if (!checkEmailFormat(infoMap.get("e"))) return INVALID_EMAIL_FORMAT.getOutput();

        //asking the security question


        //add the new user to database
        addUser(new User(infoMap.get("p"), infoMap.get("u"), infoMap.get("n"), infoMap.get("e"), infoMap.get("s")));
        return SUCCESSFULL_REGISTER.getOutput();
    }

    public static HashMap<String, String> askSecurityQuestion(Matcher matcher) {
        HashMap<String, String> pickedQuestion = new HashMap<>() {{
            put("q", null);
            put("a", null);
            put("c", null);
        }};

        String questionInfo = matcher.group("questionInfo");
        if (getMatcher(questionInfo, MULTI_OPTION_FIELD_FORMAT.getRegex()) == null) return null;

        matcher = getMatcher(questionInfo, OPTION_FIELD.getRegex());
        if (matcher == null) return null;

        String option;
        String optionInfo;
        do {
            option = matcher.group("option");
            optionInfo = matcher.group("optionInfo");
        } while (matcher.matches());

        return pickedQuestion;
    }
}
