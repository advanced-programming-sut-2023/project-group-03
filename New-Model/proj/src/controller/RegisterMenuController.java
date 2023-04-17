package controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import static view.SignUpMenuCommands.*;
import static controller.Response.*;
import static controller.ControllerFunctions.getMatcher;
import static view.SignUpMenu.getRandomPasswordConfirmation;
import static view.SignUpMenu.showRandomSlogan;

public class RegisterMenuController extends UserBasedMenuController {

    HashMap<String, String> infoMap = new HashMap() {{
        put("u", null);
        put("p", null);
        put("e", null);
        put("n", null);
        put("s", null);
    }};

    public String registerNewUser(Matcher inputMatcher) {
        //check all the problems that can occur for the command and return a proper String to menu to see what happened
        boolean randomPassword = false; // check if user wants a random password and if he wants it, no need to check the confirmation.
        String userInfo = inputMatcher.group("userInfo");
        Matcher matcher = getMatcher(userInfo, USER_FIELD.getRegex());
        if (matcher == null) return "Invalid command!";

        //check the format of user info
        Matcher registerFormatCheckerMatcher = getMatcher(userInfo, NEW_USER_FORMAT_CHECK.getRegex());
        if (registerFormatCheckerMatcher == null) return "Invalid command!";
        registerFormatCheckerMatcher.find();
        if (!(registerFormatCheckerMatcher.end() == userInfo.length())) return "Invalid command!";

        String option = "";
        String optionInfo = "";
        while (matcher.matches()) {
            option = matcher.group("option");
            optionInfo = matcher.group("optionInfo");
            if (!infoMap.containsKey(option)) return INVALID_OPTION.toString();
            if (infoMap.get(option) != null) return REPETITIVE_OPTION.toString();
            if (optionInfo == null) {
                if (option.equals("u")) return USERNAME_EMPTY.toString();
                if (option.equals("p")) return PASSWORD_EMPTY.toString();
                if (option.equals("n")) return NICKNAME_EMPTY.toString();
                if (option.equals("e")) return EMAIL_EMPTY.toString();
                if (option.equals("s")) return SLOGAN_EMPTY.toString();
            }
            if (option.equals("p") && optionInfo.equals("random")) {
                optionInfo = randomPasswordGenerator();
                randomPassword = true;
            }
            while (!getRandomPasswordConfirmation(optionInfo).equals(optionInfo)) {
                optionInfo = randomPasswordGenerator();
            }

            if (option.equals("s") && optionInfo.equals("random")) optionInfo = randomSloganGenerator();
            while (showRandomSlogan(optionInfo).equals("n")) {
                optionInfo = randomSloganGenerator();
            }

            if (optionInfo.contains("\"")) optionInfo = optionInfo.substring(1, optionInfo.length() - 2);

            infoMap.put(option, optionInfo);
        }

        if (!checkUsernameNicknameFormat(infoMap.get("u"))) return INVALID_USERNAME_FORMAT.toString();
        //check if there is a player with this username--------------

        if (!checkUsernameNicknameFormat(infoMap.get("n"))) return INVALID_NICKNAME_FORMAT.toString();

        if (checkPasswordWeakness(infoMap.get("p"))) return WEAK_PASSWORD.toString();
        Matcher passwordConfirmationMatcher = getMatcher(userInfo, PASSWORD_CONFIRMATION.getRegex());
        if (!randomPassword) {
            if (passwordConfirmationMatcher == null) return PASSWORD_CONFIRMATION_EMPTY.toString();
            if (!passwordConfirmationMatcher.group("password").equals(passwordConfirmationMatcher.group("passwordConfirmation"))) {
                return WRONG_PASSWORD_CONFIRMATION.toString();
            }
        }

        //check if there is a player with this email-----------------
        if (!checkEmailFormat(infoMap.get("e"))) return INVALID_EMAIL_FORMAT.toString();

        //add the new user to database
        return SUCCESSFULL_REGISTER.toString();
    }
}
