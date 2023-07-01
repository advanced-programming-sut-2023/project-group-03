package model.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class Sender {
    private static DataOutputStream dataOutputStream;

    public static synchronized void sendMessage(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }
}
