package controller;

import Model.User;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import static controller.Enums.InputOptions.*;
import static Model.UserDatabase.getUserByName;
import static controller.Enums.Response.*;
import static view.LoginMenu.*;

public class LoginMenuController extends UserBasedMenuController {
    public void stayLoggedIn() {

    }

    public String login(Matcher matcher, Scanner scanner) {
        String loginInfo = matcher.group("loginInfo");
        HashMap<String, String> infoMap = getOptions(LOGIN_USER.getKeys(), loginInfo);
        if (infoMap.get("error") != null) return infoMap.get("error");
        String username = infoMap.get("u");
        String password = getEncryptedPassword(infoMap.get("p"));
        User user = getUserByName(username);
        //check username existence
        if (user == null) return UNKNOWN_USERNAME.getOutput();

        boolean rightPassword = true;
        if (!password.equals(user.getPassword())) {
            rightPassword = wrongPassword(scanner, user);
        }

        if (!rightPassword) return BACK_TO_LOGIN_MENU.getOutput();
        return SUCCESSFUL_LOGIN.getOutput();
    }

    private boolean wrongPassword(Scanner scanner, User user) {
        /*
        define amountOfWrongPassword : Integer
        check the password and based on amountOfWrongPassword give a time break to user (5s, 10s, ...)
         */
        int wrongPasswordCounter = 0;
        String inputPassword;
        do {
            wrongPasswordCounter++;
            inputPassword = getPasswordAgain(scanner, wrongPasswordCounter);
            if (inputPassword.equals("back")) return false;
        } while (!getEncryptedPassword(inputPassword).equals(user.getPassword()));
        return true;
    }

    public String forgotPasswordGetUsername(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        User user = getUserByName(username);
        if (user == null) return UNKNOWN_USERNAME.getOutput();
        return forgotPassword(scanner, user);
    }

    private String forgotPassword(Scanner scanner, User user) {
        String securityQuestion = user.getSecurityQuestion();
        String answer = getAnswer(scanner, securityQuestion);;
        while (!getEncryptedPassword(answer).equals(user.getRecoveryPass())){
            if (answer.equals("back")) return BACK_TO_LOGIN_MENU.getOutput();
            view.LoginMenu.showOutput(WRONG_ANSWER_SECURITY_QUESTION.getOutput());
            answer  = getAnswer(scanner, securityQuestion);
        }
        return setNewPassword(user, scanner);
    }

    public String setNewPassword(User user, Scanner scanner) {
        String newPassword = getNewPassword(scanner);
        while (true) {
            if (newPassword.equals("back")) return BACK_TO_LOGIN_MENU.getOutput();
            if (checkPasswordWeakness(newPassword)) view.LoginMenu.showOutput(WEAK_PASSWORD.getOutput());
            else if (getEncryptedPassword(newPassword).equals(user.getPassword()))
                view.LoginMenu.showOutput(REPETITIVE_PASSWORD.getOutput());
            else break;
            newPassword = getNewPassword(scanner);
        }
        user.setPassword(getEncryptedPassword(newPassword));
        return SUCCESSFUL_CHANGE_PASSWORD.getOutput();
    }

}
