package controller;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import static controller.Enums.Response.*;
import static controller.Enums.InputOptions.*;
import static Model.UserDatabase.*;

public class ProfileMenuController extends UserBasedMenuController {
    private User user;

    public ProfileMenuController(User user) {
        this.user = user;
    }

    public String changeUsername(String username) {
        if (!checkUsernameNicknameFormat(username)) return INVALID_USERNAME_FORMAT.getOutput();
        user.setUsername(username);
        return SUCCESSFUL_CHANGE_USERNAME.getOutput();
    }

    public String changeNickname(String nickName) {
        if (!checkUsernameNicknameFormat(nickName)) return INVALID_NICKNAME_FORMAT.getOutput();
        user.setNickname(nickName);
        return SUCCESSFUL_CHANGE_NICKNAME.getOutput();
    }

    public String changePassword(Matcher matcher) {
        String passwordInfo = matcher.group("passwordInfo");
        HashMap<String, String> infoMap = getOptions(CHANGE_PASSWORD.getKeys(), passwordInfo);
        String error = infoMap.get("error");
        if (error != null) return error;
        String oldPassword = getEncryptedPassword(infoMap.get("o"));
        if (!oldPassword.equals(user.getPassword())) return WRONG_CURRENT_PASSWORD.getOutput();
        String newPassword = infoMap.get("n");
        if (getEncryptedPassword(newPassword).equals(oldPassword)) return REPETITIVE_PASSWORD.getOutput();
        if (checkPasswordWeakness(newPassword)) return WEAK_PASSWORD.getOutput();
        user.setPassword(getEncryptedPassword(newPassword));
        return SUCCESSFUL_CHANGE_PASSWORD.getOutput();
    }

    public String changeEmail(String email) {
        if (checkEmailFormat(email)) return INVALID_EMAIL_FORMAT.getOutput();
        user.setEmail(email);
        return SUCCESSFUL_CHANGE_EMAIL.getOutput();
    }

    public String changeSlogan(String slogan) {
        user.setSlogan(slogan);
        return SUCCESSFUL_CHANGE_SLOGAN.getOutput();
    }

    public String removeSlogan() {
        user.setSlogan(null);
        return SUCCESSFUL_REMOVE_SLOGAN.getOutput();
    }

    public String displayHighScore() {
        return DISPLAY_HIGHSCORE.getOutput() + user.getHighScore();
    }

    public String displayRank() {
        String username = user.getUsername();
        updateRanking();
        int rank = 0;
        ArrayList<User> rankings = getRanking();
        for (User player : rankings) {
            rank++;
            if (player.getUsername().equals(username)) break;
        }

        return DISPLAY_RANK.getOutput() + rank;
    }

    public String displaySlogan() {
        String slogan = user.getSlogan();
        if (slogan == null) return slogan;
        return DISPLAY_NULL_SLOGAN.getOutput();
    }

    public String displayAllInfo() {
        String output = "";
        output += "username: " + user.getUsername();
        output += "nickname: " + user.getNickname();
        output += "email: " + user.getEmail();
        if (user.getSlogan() != null) output += "slogan: " + user.getSlogan();

        return output;
    }


}
