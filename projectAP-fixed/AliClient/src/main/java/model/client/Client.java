package model.client;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.user.User;
import model.user.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private User currentUser;
    private String host;
    private int port;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Scanner scanner = new Scanner(System.in);

    public Client(User currentUser, String host, int port, String[] args) throws IOException {
        this.host = host;
        this.port = port;
        while (true) {
            try {
                socket = new Socket(host, port);
                break;
            } catch (Exception e) {
                System.out.println("Fail to connect, please wait...");
            }
        }
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        UserDatabase.setDataInputStream(dataInputStream);
        UserDatabase.setDataOutputStream(dataOutputStream);

        //getting users to login
        String usersString = dataInputStream.readUTF();
        Type userType = new TypeToken<ArrayList<User>> () {}.getType();

        ArrayList<User> users = (new GsonBuilder()).create().fromJson(usersString, userType);
        UserDatabase.setUsers(users);

        //call login menu
    }

    //getters and setters

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
