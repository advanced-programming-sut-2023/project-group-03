package model.client;

import com.google.gson.GsonBuilder;

public class UserPacket {
    private String type;
    private String command;

    public UserPacket(String type, String command) {
        this.type = type;
        this.command = command;
    }

    public String convertPacketToString() {
        return new GsonBuilder().create().toJson(this);
    }

    public static UserPacket convertStringToPacket(String packetString) {
        return new GsonBuilder().create().fromJson(packetString, UserPacket.class);
    }

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
