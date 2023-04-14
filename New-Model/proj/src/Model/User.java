package Model;

import java.util.ArrayList;

public class User {
    private String password;
    private String username;
    private String nickname;
    private String email;
    private String recoveryPass;
    private String slogan;
    private String securityQuestion;
    private int highScore;
    private int rank;

    public User(String password, String username, String nickname, String email, String recoveryPass, String slogan, String securityQuestion) {
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.recoveryPass = recoveryPass;
        this.slogan = slogan;
        this.securityQuestion = securityQuestion;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getRecoveryPass() {
        return recoveryPass;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getRank() {
        //calculate ranks
        return rank;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRecoveryPass(String recoveryPass) {
        this.recoveryPass = recoveryPass;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
