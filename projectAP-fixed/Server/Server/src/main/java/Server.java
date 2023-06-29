import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server implements Runnable{
    private DatagramSocket datagramSocket;
    private ArrayList<GameClient> clients;
    private Thread SendThread;

    public Server() {
        try {
            datagramSocket = new DatagramSocket(8888);
        } catch (SocketException e) {
            System.out.println("socket problem");
        }
        Thread listenThread = new Thread(this);
        listenThread.start();
        //SendThread.start();
        SendThread=new Thread(()->{
            while (true) {

            }
        });
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[4028];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            while (true) {
                datagramSocket.receive(incoming);
                byte[] data = incoming.getData();
                String packet = new String(data, 0, incoming.getLength());
                //analyzePacket(packet, incoming.getAddress(), incoming.getPort());
                sendPacketForAll(packet);
                Thread.sleep(1000 / 20);
            }
        } catch (Exception e) {

        }
    }

    private void analyzePacket(String body, InetAddress address, int port) {
        if (body.startsWith(GameEvent.DROP_UNIT.getName())) {

        }
        if (body.startsWith(GameEvent.DROP_BUILDING.getName())) {

        }
        if (body.startsWith(GameEvent.USER_JOINED_TO_NETWORK.getName())) {

        }
        if (body.startsWith(GameEvent.START_GAME.getName())) {

        }
        if (body.startsWith(GameEvent.JOIN_T0_GAME.getName())) {

        }
    }

    boolean sendPacket(String body, InetAddress address, int port) {
        DatagramPacket dp = new DatagramPacket(body.getBytes(), body.getBytes().length, address, port);
        try {
            datagramSocket.send(dp);
            return true;
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
    }

    void sendPacketForAll(String body) {
        for (GameClient player : clients) {
            sendPacket(body, player., player.port);
        }
    }

}
