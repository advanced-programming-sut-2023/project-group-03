package Model;

import java.util.ArrayList;
import java.util.Collections;

public class UserDatabase {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<User> ranking = new ArrayList<>();
    public UserDatabase() {
    }

    public static User getUserByName(String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(name)) {
                return users.get(i);
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {return null;}

    public static User getUserByNickName(String name) {
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {

    }

    public static void updateRanking() {
        Collections.sort(ranking);
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setRank(i + 1);
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getRanking() {
        return ranking;
    }
}
