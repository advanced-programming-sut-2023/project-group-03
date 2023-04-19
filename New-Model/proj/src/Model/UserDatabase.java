package Model;

import java.util.ArrayList;

public class UserDatabase {
    private static ArrayList<User> users;
    private static ArrayList<User> ranking;
    public UserDatabase() {
    }

    public static User getUserByName(String name) {
        return null;
    }

    public static User getUserByEmail(String email) {return null;}

    public static User getUserByNickName(String name) {
        return null;
    }

    public static void addUser(User user) {

    }

    public static void removeUser(User user) {

    }

    public static void updateRanking() {

    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getRanking() {
        return ranking;
    }
}
