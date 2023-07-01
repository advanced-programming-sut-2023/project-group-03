package model.client;

import com.google.gson.GsonBuilder;

public class ChatMenuPacket {
    private String type;
    private String command;

    public ChatMenuPacket(String type, String command) {
        this.type = type;
        this.command = command;
    }

    public static String convertPacketToString(ChatMenuPacket packet) {
        return (new GsonBuilder().create()).toJson(packet);
    }

    public static ChatMenuPacket convertStringToPacket(String data) {
        return (new GsonBuilder()).create().fromJson(data, ChatMenuPacket.class);
    }

    //getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
