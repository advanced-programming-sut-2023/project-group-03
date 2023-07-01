package model.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ChatDatabase {
    static String dataPath = "src/main/resources/users/chats.json";
    private static ArrayList<Chat> chats = new ArrayList<>();

    public static void saveChats() {
        String data = makeChatsJson();
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

    public static void loadSavedChats() {
        File file = new File(dataPath);

        try {
            Scanner reader = new Scanner(file);
            String data = "";
            while (reader.hasNextLine()) {
                data = data.concat(reader.nextLine());
            }

            makeJsonChats(data);

        } catch (Exception e) {
            System.out.println("There was a problem...");
        }
    }
    private static int lastChatId;

    public static String makeChatsJson() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(chats);
    }

    public static void makeJsonChats(String save) {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        Type chatsType = new TypeToken<ArrayList<Chat>>() {}.getType();

        chats = gson.fromJson(save, chatsType);
        if (chats == null) chats = new ArrayList<>();

        for (int i = 0; i < chats.size(); i++) {
            HashMap<Integer, Message> messageHashMap = new HashMap<>();
            for (Message message : chats.get(i).messages) {
                messageHashMap.put(message.getMessageId(), message);
            }
            chats.get(i).setMessageHashMap(messageHashMap);
        }
    }

    public static Chat getChatById(int id) {
        for (Chat chat : chats) {
            if (chat.getChatId() == id) return chat;
        }
        return null;
    }

    //getters and setters

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public static void addChat(Chat chat) {
        chats.add(chat);
        lastChatId++;
        chat.setChatId(lastChatId);
    }
}
