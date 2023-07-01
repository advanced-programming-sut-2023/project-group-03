package model.chat;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import model.server.ChatMenuPacket;
import model.server.Connection;
import model.user.UserDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Chat {
    @Expose
    protected int chatId;
    @Expose
    protected int lastMessageId;
    @Expose
    protected String chatName;
    @Expose
    protected ArrayList<Message> messages = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    protected HashMap<Integer, Message> messageHashMap = new HashMap<>();
    protected ArrayList<String> chatUsers = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    protected ArrayList<Connection> currentUsersConnections = new ArrayList<>();

    public Chat(ArrayList<String> names, String name) {
        this.chatName = name;
        ChatDatabase.addChat(this);
        for (String userName : names) {
            UserDatabase.getUserByName(userName).addChat(chatId);
        }
    }


    //getters and setters
    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public HashMap<Integer, Message> getMessageHashMap() {
        return messageHashMap;
    }

    public void setMessageHashMap(HashMap<Integer, Message> messageHashMap) {
        this.messageHashMap = messageHashMap;
    }

    public ArrayList<String> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(ArrayList<String> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public synchronized void addConnection(Connection connection) {
        currentUsersConnections.add(connection);
    }

    public synchronized void removeConnection(Connection connection) {
        currentUsersConnections.remove(connection);
    }

    public synchronized void sendMessageToCurrentConnections() throws IOException {
        String data = (new GsonBuilder()).create().toJson(messages);
        for (Connection connection : currentUsersConnections) {
            ChatMenuPacket packet = new ChatMenuPacket("chat menu", data);
            connection.getDataOutputStream().writeUTF((new GsonBuilder()).create().toJson(packet));
        }
    }

    public synchronized void sendMessageToConnection(Connection connection) throws IOException {
        String data = (new GsonBuilder()).create().toJson(messages);
        ChatMenuPacket packet = new ChatMenuPacket("chat menu", data);
        connection.getDataOutputStream().writeUTF((new GsonBuilder()).create().toJson(packet));
    }

    public synchronized void addMessage(Message message) {
        lastMessageId++;
        message.setMessageId(lastMessageId);
        messageHashMap.put(lastMessageId, message);
        messages.add(message);
    }

    public synchronized void updateMessage(Message message) {
        int id = message.getMessageId();
        Message chatMessage;
        for (int i = 0; i < messages.size(); i++) {
            chatMessage = messages.get(i);
            if (chatMessage.getMessageId() == id) {
                messages.set(i, message);
            }
        }
        messageHashMap.put(id, message);
    }

    public synchronized void deleteForMe(String messageId) {
        Message mainMessage = messageHashMap.get(Integer.parseInt(messageId));
        mainMessage.setDeletedForMe(true);
    }

    public synchronized void deleteForAll(String messageId) {
        Message mainMessage = messageHashMap.get(Integer.parseInt(messageId));
        mainMessage.setDeletedForAll(true);
    }
}
