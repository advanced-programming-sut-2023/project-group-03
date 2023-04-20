package controller;

import Model.User;
import controller.Enums.InputOptions;
import view.SignUpMenu;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.SignUpMenuCommands.*;
import static controller.Enums.ControllerCommands.*;
import static controller.Enums.Response.*;
import static controller.ControllerFunctions.*;
import static Model.UserDatabase.*;
import static view.SignUpMenu.*;

public class RegisterMenuController extends UserBasedMenuController {
    public static String askSecurityQuestion2(Matcher matcher) {
        return null;
    }

    public String registerNewUser(Matcher inputMatcher, Scanner scanner) {
        HashMap<String, String> infoMap = new HashMap() {{//setting up hash keys
            put("u", null);
            put("p", null);
            put("e", null);
            put("n", null);
            put("s", null);
        }};

        boolean randomPassword = false; // check if user wants a random password and if he wants it, no need to check the confirmation.
        boolean randomSlogan = false;
        String userInfo = inputMatcher.group("userInfo");
        //check the format of user info
        Matcher registerFormatCheckerMatcher = getMatcher(userInfo, NEW_USER_FORMAT_CHECK.getRegex());
        if (registerFormatCheckerMatcher == null) return INVALID_COMMAND.getOutput();

        String option = "";
        String optionInfo = "";
        Matcher matcher = getMatcher(userInfo, OPTION_FIELD.getRegex());
        if (matcher == null) return INVALID_COMMAND.getOutput();
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

            if (option.equals("s") && optionInfo.equals("random")) {
                optionInfo = randomSloganGenerator();
                randomSlogan = true;
            }

            infoMap.put(option, unwrapQuotation(optionInfo));
        } while (matcher.find());

        //check if input misses an option
        for (String key : infoMap.keySet()) {
            if (!key.equals("s") && infoMap.get(key) == null)
                return FIELD_FORGOTTEN.getOutput() + key;
        }

        if (!checkUsernameNicknameFormat(infoMap.get("u"))) return INVALID_USERNAME_FORMAT.getOutput();
        //check username and nickname
        if (getUserByName(infoMap.get("u")) != null) return REPETITIVE_USERNAME.getOutput();
        if (!checkUsernameNicknameFormat(infoMap.get("n"))) return INVALID_NICKNAME_FORMAT.getOutput();

        //check password
        if (checkPasswordWeakness(infoMap.get("p"))) return WEAK_PASSWORD.getOutput();
        Matcher passwordConfirmationMatcher = getMatcher(userInfo, PASSWORD_CONFIRMATION.getRegex());
        if (!randomPassword) {
            if (passwordConfirmationMatcher == null) return PASSWORD_CONFIRMATION_EMPTY.getOutput();
            if (!passwordConfirmationMatcher.group("password").equals(passwordConfirmationMatcher.group("passwordConfirmation"))) {
                return WRONG_PASSWORD_CONFIRMATION.getOutput();
            }
        }

        //setting random password and random slogan up
        randomSetups(randomSlogan, randomPassword, scanner, infoMap);

        //check email
        if (getUserByEmail(infoMap.get("e")) != null) return REPETITIVE_EMAIL.getOutput();
        if (!checkEmailFormat(infoMap.get("e"))) return INVALID_EMAIL_FORMAT.getOutput();

        //encrypt password before saving
        infoMap.put("p", getEncryptedPassword(infoMap.get("p")));

        User newUser = new User(infoMap.get("p"), infoMap.get("u"), infoMap.get("n"), infoMap.get("e"), infoMap.get("s"));
        addUser(newUser);

        setSecurityQuestionAnswer(newUser, scanner);

        return SUCCESSFUL_REGISTER.getOutput();
    }

    private void setSecurityQuestionAnswer(User newUser, Scanner scanner) {
        String question;
        HashMap<String, String> securityQuestionResult;

        while (true) {
            question = SignUpMenu.askSecurityQuestion(securityQuestions, scanner);
            securityQuestionResult  = getOptions(InputOptions.PICK_QUESTION.getKeys(), question);
            if (securityQuestionResult.get("error") != null) {
                showOutput(securityQuestionResult.get("error"));
                continue;
            }

            if (!securityQuestionResult.get("a").equals(securityQuestionResult.get("c"))) {
                showOutput(WRONG_ANSWER_CONFIRMATION.getOutput());
                continue;
            }

            if (Integer.parseInt(securityQuestionResult.get("q")) < 1 ||
                    Integer.parseInt(securityQuestionResult.get("q")) > securityQuestions.size()) {
                showOutput(QUESTION_OUT_OF_RANGE.getOutput());
                continue;
            }
            break;
        }
        newUser.setSecurityQuestion(securityQuestions.get(Integer.parseInt(securityQuestionResult.get("q")) - 1));

        //encrypt recovery password
        String answer = getEncryptedPassword(securityQuestionResult.get("a"));

        newUser.setRecoveryPass(answer);
    }

    private void randomSetups(boolean randomSlogan, boolean randomPassword, Scanner scanner, HashMap<String, String> infoMap) {
        if (randomPassword) {
            String password = infoMap.get("p");
            while (!getRandomPasswordConfirmation(password, scanner).equals(password)) {
                password = randomPasswordGenerator();
            }
        }

        if (randomSlogan) {
            String slogan = infoMap.get("s");
            while (showRandomSlogan(slogan, scanner).equals("n")) {
                slogan = randomSloganGenerator();
            }
        }
    }
}
