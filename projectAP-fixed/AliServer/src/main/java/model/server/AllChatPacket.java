package model.server;

import com.google.gson.GsonBuilder;

public class AllChatPacket {
    private String type;
    private String target;
    private String chatName;

    public AllChatPacket(String type, String target) {
        this.type = type;
        this.target = target;
    }

    public static String convertPacketToString(AllChatPacket packet) {
        return (new GsonBuilder().create()).toJson(packet);
    }

    public static AllChatPacket convertStringToPacket(String data) {
        return (new GsonBuilder()).create().fromJson(data, AllChatPacket.class);
    }

    //getters and setters


    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
