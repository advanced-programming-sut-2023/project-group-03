package model.user;

//import Model.Field.GameMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.client.Packet;
import model.client.UserPacket;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UserDatabase {
    private static int lastUserId; //todo save this int
    static String dataPath = "src/main/resources/users/users.json";
    static String backupData = "src/main/resources/users/backup.json";
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    //    private static final ArrayList<GameMap> maps = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<User> ranking = new ArrayList<>();

    public static User getUserById(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == i) return users.get(i);
        }
        return null;
    }

    public static User getUserByName(String name) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(name)) {
                return users.get(i);
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) return user;
        }
        return null;
    }

    public static User getUserByNickName(String name) {
        return null;
    }

    public static synchronized void addUser(User user) {
        users.add(user);
        //todo add public chat
    }

    public static void removeUser(User user) {

    }

    public static int getLastUserId() {
        return lastUserId;
    }

    public static void setLastUserId(int lastUserId) {
        UserDatabase.lastUserId = lastUserId;
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

//    public static void addMap(GameMap map) {
//        maps.add(map);
//    }

    public static void saveUsers() {
        String data = makeUsersJson();
        File file = new File(dataPath);

        try {
            if (file.exists()) file.delete();
            file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data);
            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void loadSavedData() {
        File file = new File(dataPath);
        File backupFile = new File(backupData);

        try {
            Scanner reader = new Scanner(file);
            String data = "";
            while (reader.hasNextLine()) {
                data = data.concat(reader.nextLine());
            }

            makeJsonUsers(data);

            backupFile.delete();
            backupFile.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(backupFile));
            bufferedWriter.write(data);
            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println("There was a problem...");
        }
    }

    public static String makeUsersJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(users);
    }

    public static void makeJsonUsers(String save) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        Type usersType = new TypeToken<ArrayList<User>>() {
        }.getType();

        users = gson.fromJson(save, usersType);
    }

//    public static ArrayList<GameMap> getMaps() {
//        return maps;
//    }
//
//    public static GameMap getMapByName(String name) {
//        for (int i = 0; i < maps.size(); i++) {
//            if (maps.get(i).getName().equals(name)) {
//                return maps.get(i);
//            }
//        }
//        return null;
//    }

    public static void setUsers(ArrayList<User> users) {
        UserDatabase.users = users;
    }

    public static void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
            }
        }
    }

    public static void sendUserToServer(User user) throws IOException {
        UserPacket userPacket = new UserPacket("update user", (new GsonBuilder()).create().toJson(user));
        Packet packet = new Packet("user", userPacket.convertPacketToString());
        dataOutputStream.writeUTF(packet.convertPacketToString());
    }

    public static User getUserFromServer(int userId) throws IOException {
        UserPacket userPacket = new UserPacket("get user", Integer.toString(userId));
        Packet packet = new Packet("user", userPacket.convertPacketToString());
        dataOutputStream.writeUTF(packet.convertPacketToString());
        String result = dataInputStream.readUTF();

        Packet resultPacket = Packet.convertStringToPacket(result);
        String userString = resultPacket.getCommand();

        User user = (new GsonBuilder().create()).fromJson(userString, User.class);
        return user;
    }

    public static synchronized void requestFriendToServer(int friendId, User user) throws IOException {
        UserPacket userPacket = new UserPacket("make request", Integer.toString(friendId));
        Packet packet = new Packet("user", userPacket.convertPacketToString());
        dataOutputStream.writeUTF(packet.convertPacketToString());
        sendUserToServer(user);
    }

    //getters and setters

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public static void setDataOutputStream(DataOutputStream dataOutputStream) {
        UserDatabase.dataOutputStream = dataOutputStream;
    }

    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static void setDataInputStream(DataInputStream dataInputStream) {
        UserDatabase.dataInputStream = dataInputStream;
    }
}
