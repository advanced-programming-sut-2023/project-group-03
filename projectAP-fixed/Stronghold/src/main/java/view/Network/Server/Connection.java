package view.Network.Server;

import Model.GamePlay.Player;
import view.Network.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class Connection extends Thread{
    boolean isRunning = true;
    String clientUsername;
    Server server;
    Socket socket;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;


    public Connection(Socket socket,Server server) {
        this.server = server;
        this.socket = socket;
        server.getDataBase().connections.add(this);
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        //try {
            while (isRunning) {
                handleClient();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
//        } catch (RuntimeException e) {
//            throw new RuntimeException();
//        }
    }

    private void handleClient(){
        System.out.println("waiting...");
        String input = null;
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=========>" + input);
        try {
            if (input.startsWith(GameEvent.MAKE_GAME.getName())) {
                String username = GameEvent.getOptions(GameEvent.MAKE_GAME.getKeys(), GameEvent.makeClean(input)).get("u");
                String map = GameEvent.getOptions(GameEvent.MAKE_GAME.getKeys(), GameEvent.makeClean(input)).get("m");
                server.setHost(username);
                System.out.println("set host:" + username);
                server.setMapName(map);
                System.out.println("set map:" + map);
            }
            if (input.startsWith(GameEvent.INTRODUCE.getName())) {
                String username = GameEvent.getOptions(GameEvent.INTRODUCE.getKeys(), GameEvent.makeClean(input)).get("u");
                System.out.println("fixed as: " + username);
                clientUsername = username;
            }
            if (input.startsWith(GameEvent.JOIN_T0_GAME.getName())) {
                String username = GameEvent.getOptions(GameEvent.JOIN_T0_GAME.getKeys(), GameEvent.makeClean(input)).get("u");
                System.out.println("player " + username + " joined to list");
                if (server.getHost() == null || server.getHost().length() == 0) {
                    GameEvent gameEvent1 = GameEvent.REJECT_JOIN;
                    gameEvent1.fixMessage(null);
                    dataOutputStream.writeUTF(gameEvent1.getMessage());
                    server.getDataBase().connections.remove(this);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    isRunning = false;
                    return;
                }
                sendEvent(input);
                GameEvent gameEvent = GameEvent.ANNOUNCE_LIST;
                String[] strings = new String[4];
                int counter = 0;
                for (int i = 0; i < server.getDataBase().getConnections().size(); i++) {
                    System.out.println(server.getDataBase().getConnections().get(i).clientUsername);
                    strings[counter] = server.getDataBase().getConnections().get(i).clientUsername;
                    counter++;
                }
                System.out.println(counter);
                if (counter == 1) {
                    gameEvent.fixMessage(server.mapName, strings[0]);
                }
                if (counter == 2) {
                    gameEvent.fixMessage(server.mapName, strings[0], strings[1]);
                }
                if (counter == 3) {
                    gameEvent.fixMessage(server.mapName, strings[0], strings[1], strings[2]);
                }
                if (counter == 4) {
                    gameEvent.fixMessage(server.mapName, strings[0], strings[1], strings[2], strings[4]);
                }
                System.out.println(gameEvent.getMessage());
                System.out.println("=====================");
                dataOutputStream.writeUTF(gameEvent.getMessage());
            }
            if (input.startsWith(GameEvent.START_GAME.getName())) {
                sendEventToAll(input);
            }
            if (input.startsWith(GameEvent.DROP_BUILDING.getName())) {
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.DROP_UNIT.getName())) {
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.DELETE_BUILDING.getName())) {
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.ATTACK.getName())) {
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.SELECT_BUILDING.getName())) {
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.SELECT_TILE.getName())) {
                sendEvent(input);
            }
            if (input.startsWith(GameEvent.MOVE.getName())) {
                sendEvent(input);
            }
        } catch (IOException e) {
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            throw new RuntimeException();
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

    public void sendEventToAll(String output) {
        for (int i = 0; i < server.getDataBase().getConnections().size(); i++) {
            server.getDataBase().getConnections().get(i).acceptEvent(output);
        }
    }

    public void acceptEvent(String input) {
        try {
            dataOutputStream.writeUTF(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
