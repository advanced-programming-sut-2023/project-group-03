package model.client;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.ChatShower;
import model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChatListener extends Thread{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ChatShower chatShower;

    public ChatListener(DataInputStream dataInputStream,DataOutputStream dataOutputStream, ChatShower chatShower) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.chatShower = chatShower;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String input = dataInputStream.readUTF();
                ChatMenuPacket chatMenuPacket = ChatMenuPacket.convertStringToPacket(input);
                String messagesString = chatMenuPacket.getCommand();

                Type messagesTypeToken = new TypeToken<ArrayList<Message>> () {}.getType();

                ArrayList<Message> messages = (new GsonBuilder()).create().fromJson(messagesString, messagesTypeToken);

                chatShower.updateMessages(messages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
