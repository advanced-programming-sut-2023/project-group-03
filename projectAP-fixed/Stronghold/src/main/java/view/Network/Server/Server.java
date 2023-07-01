package view.Network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    DataBase dataBase = new DataBase();
    String host;
    String mapName;

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            boolean bool = true;
            while (bool) {
                Socket socket = serverSocket.accept();
                System.out.println("###### new player just joined");
                Connection connection = new Connection(socket,this);
                connection.start();
            }
        } catch (IOException e) {

        }
    }

    @Override
    public void run() {

    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
