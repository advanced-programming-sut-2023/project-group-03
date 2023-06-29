package view.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class GameClient implements Runnable {
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    private Queue<GameEvent> events;

    public GameClient(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {

    }

    public GameEvent getEvent() {
        return events.remove();
    }
}
