package view.Network.Server;

import Model.GamePlay.Player;
import view.Network.Client;
import view.Network.GameEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class Connection extends Thread{
    String clientUsername;
    Server server;
    Socket socket;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;

    Queue<String> events;

    public Connection(Socket socket,Server server) {
        this.server = server;
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                handleClient();
                Thread.sleep(200);
            }
        } catch (Exception e) {

        }
    }

    private void handleClient() throws IOException {
        String input = dataInputStream.readUTF();
        try {
            if (input.startsWith(GameEvent.JOIN_T0_GAME.getName())) {
                String username = GameEvent.getOptions(GameEvent.JOIN_T0_GAME.getKeys(), input).get("u");
                System.out.println("player " + username + " joined");
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.START_GAME.getName())) {

            }
        } catch (Exception e) {

        }
    }

    public void sendEvent(String output) {
        for (int i = 0; i < server.getDataBase().getConnections().size(); i++) {
            if (server.getDataBase().getConnections().get(i).equals(this)) {
                continue;
            }
            server.getDataBase().getConnections().get(i).acceptEvent(output);
        }
    }

    public void acceptEvent(String input) {
        events.add(input);
        try {
            dataOutputStream.writeUTF(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
