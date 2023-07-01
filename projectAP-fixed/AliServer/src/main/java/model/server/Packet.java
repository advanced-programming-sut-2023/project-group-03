package model.server;

import com.google.gson.GsonBuilder;

public class Packet {
    private String type;//user, all chat, chat menu
    private String command;

    public Packet(String type, String command) {
        this.type = type;
        this.command = command;
    }

    public static String convertPacketToString(Packet packet) {
        return new GsonBuilder().create().toJson(packet);
    }

    public static Packet convertStringToPacket(String packetString) {
        return new GsonBuilder().create().fromJson(packetString, Packet.class);
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
