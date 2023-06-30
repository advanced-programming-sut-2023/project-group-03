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
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                GameEvent gameEvent = GameEvent.getEvent(dataInputStream.readUTF());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public GameEvent getEvent() {
        return events.remove();
    }

    public boolean hasEvent() {
        return events.size() > 0;
    }
}
