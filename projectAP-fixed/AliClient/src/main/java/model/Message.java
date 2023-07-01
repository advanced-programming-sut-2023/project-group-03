package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.client.ChatMenuPacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Message {
    private String senderUsername;
    private int senderId;
    private String time;
    private String content;
    private HashMap<Integer, Integer> reactions;
    private int[] reactionsAmount = new int[5];
    private int messageId;
    private boolean seen = false;
    private boolean deletedForMe = false;
    private boolean deletedForAll = false;

    public Message(String senderUsername, String time, String content, int senderId) {
        this.senderUsername = senderUsername;
        this.time = time;
        this.content = content;
        this.senderId = senderId;
    }

    public String convertMessageToJson() {
        Gson gson = (new GsonBuilder()).create();
        return gson.toJson(this);
    }

    public static Message convertJsonToMessage(String data) {
        return (new GsonBuilder().create()).fromJson(data, Message.class);
    }

    public void updateReaction(Integer userId, int reactionCode, DataOutputStream dataOutputStream) {
        if (reactions.containsKey(userId)) {
            reactionsAmount[reactions.get(userId)]--;
        }
        reactionsAmount[reactionCode]++;
        reactions.put(userId, reactionCode);

        updateMessageToServer(dataOutputStream);
    }

    public void editMessage(String newMessage, DataOutputStream dataOutputStream) {
        content = newMessage;
        updateMessageToServer(dataOutputStream);
    }

    public void updateMessageToServer(DataOutputStream dataOutputStream) {
        ChatMenuPacket chatMenuPacket = new ChatMenuPacket("update message", this.convertMessageToJson());
        try {
            dataOutputStream.writeUTF(ChatMenuPacket.convertPacketToString(chatMenuPacket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteForMeFunc(DataOutputStream dataOutputStream) {
        deletedForMe = true;
        updateMessageToServer(dataOutputStream);
    }

    public void deleteForAllFunc(DataOutputStream dataOutputStream) {
        deletedForAll = true;
        updateMessageToServer(dataOutputStream);
    }

    //getters and setters
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<Integer, Integer> getReactions() {
        return reactions;
    }

    public int[] getReactionsAmount() {
        return reactionsAmount;
    }

    public void setReactionsAmount(int[] reactionsAmount) {
        this.reactionsAmount = reactionsAmount;
    }

    public boolean isDeletedForMe() {
        return deletedForMe;
    }

    public void setDeletedForMe(boolean deletedForMe) {
        this.deletedForMe = deletedForMe;
    }

    public boolean isDeletedForAll() {
        return deletedForAll;
    }

    public void setDeletedForAll(boolean deletedForAll) {
        this.deletedForAll = deletedForAll;
    }
}
