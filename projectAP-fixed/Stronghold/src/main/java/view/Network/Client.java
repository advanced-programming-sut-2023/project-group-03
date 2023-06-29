package view.Network;

import Model.User;
import view.Game.FarmBuidingMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Client implements Runnable{
    User user;
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private Queue<GameEvent> events;
    private Semaphore semaphore;

    public Client(String serverIP) {
        events = new LinkedList<>();
        semaphore = new Semaphore(1);
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {

        }
        try {
            serverAddress = InetAddress.getByName(serverIP);
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[4028];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(incoming);
                byte[] data = incoming.getData();
                GameEvent gameEvent = GameEvent.getEvent(new String(data, 0, incoming.getLength()));
                semaphore.acquire();
                events.add(gameEvent);
                semaphore.release();
                Thread.sleep(100);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
    public boolean hasNewEvent() {
        boolean res = false;
        try {
            res = events.size() != 0;
        } catch (Exception ignored) {
        }
        return res;
    }

    public GameEvent getEvent() {
        GameEvent gameEvent = null;
        try {
            gameEvent = events.poll();
        } catch (Exception ignored) {
        }
        return gameEvent;
    }

    public void sendJoinRequest(String playerName) {
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("type", GameEvent.JOIN_TO_GAME);
        //jsonObject.put("message", playerName);
        //sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT);
    }

    public void sendGetAllPlayersRequest() {
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("type", GameEvent.ALL_PLAYERS);
        //jsonObject.put("message", "");
        //sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT);
    }

    public void sendGameEvent(int type, String message) {
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("type", type);
        //jsonObject.put("message", message);
        //sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT);
    }
}
