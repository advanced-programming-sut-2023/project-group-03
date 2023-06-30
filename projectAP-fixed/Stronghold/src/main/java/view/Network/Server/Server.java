package view.Network.Server;

import view.Network.GameEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    DataBase dataBase = new DataBase();

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            boolean bool = true;
            while (bool) {
                Socket socket = serverSocket.accept();
                System.out.println("made");
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
}
