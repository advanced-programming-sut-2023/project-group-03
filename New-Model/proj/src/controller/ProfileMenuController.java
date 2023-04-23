package controller;

import Model.User;

import java.util.regex.Matcher;

public class ProfileMenuController extends UserBasedMenuController {
    public boolean changeProfile(Matcher matcher) {
        return false;
    }

    public boolean changeNickname(Matcher matcher) {
        return false;
    }

    public boolean changePassword(Matcher matcher) {
        return false;
    }

    public boolean checkEmail(Matcher matcher) {
        return false;
    }

    public boolean changeSlogan(Matcher matcher, boolean removeSlogan) {
        return false;
    }

    public String displayRank(User user) {
        return "";
    }

    public String displaySlogan(User user) {
        return "";
    }

    public String displayAllInfo(User user) {
        return "";
    }


}
