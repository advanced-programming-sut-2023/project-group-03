package controller;

import Model1.Model.User;

import java.util.regex.Matcher;

public class LoginMenuController extends controller.UserBasedMenuController {
    public void stayLoggedIn() {

    }

    public String login(Matcher matcher) {
        //check username existence
        /*
        define amountOfWrongPassword : Integer
        check the password and based on amountOfWrongPassword give a time break to user (5s, 10s, ...)
         */
        return "";
    }

    public String getSecurityQuestion(User user) {
        return "";
    }

    public boolean checkSecurityQuestionAnswer(User user, String answer) {
        return true;
    }

    public String setNewPassword(User user, String newPassword, String newPasswordConfirmation) {
        //check the format of newPassword and confirmation of it
        //set the new password for the given user
        return "";
    }

}
