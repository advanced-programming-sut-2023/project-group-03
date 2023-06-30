package view.Network;

import Model.GamePlay.Player;
import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class GameClient extends Thread{
    Player player;
    User user;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    private Queue<GameEvent> events;

    public GameClient(String host, int port, User user) throws Exception{
        this.user = user;
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        start();
    }


    public GameEvent getEvent() {
        return events.remove();
    }

    public boolean hasEvent() {
        return events.size() > 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String input = dataInputStream.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
