package model.user;

//import view.Network.Client;

import java.util.ArrayList;

public class User implements Comparable<User> {
    private String password;
    private String username;
    private String nickname;
    private String email;
    private String recoveryPass;
    private String slogan;
    private String securityQuestion;
    private int highScore;
    private int rank;
    private boolean stayLoggedIn = false;
    private int userId;//todo
    private ArrayList<Integer> chatIds = new ArrayList<>();

    public User(String password, String username, String nickname, String email, String slogan) {
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        UserDatabase.addUser(this);
        userId = UserDatabase.getLastUserId() + 1;
        UserDatabase.setLastUserId(userId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Integer> getChatIds() {
        return chatIds;
    }

    public synchronized void addChat(int chatId) {
        chatIds.add(chatId);
    }

    public void setChatIds(ArrayList<Integer> chatIds) {
        this.chatIds = chatIds;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecoveryPass() {
        return recoveryPass;
    }

    public void setRecoveryPass(String recoveryPass) {
        this.recoveryPass = recoveryPass;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(User o) {
        if (this.highScore > o.highScore) {
            return 1;
        } else if (this.highScore < o.highScore) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}
