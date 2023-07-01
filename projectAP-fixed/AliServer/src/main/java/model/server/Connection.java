package model.server;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.chat.Chat;
import model.chat.ChatDatabase;
import model.chat.Message;
import model.user.User;
import model.user.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Connection extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private InetAddress ip;
    private int port;
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private MainServer mainServer;
    private Chat currentChat;

    private String clientStatus = "user";
    // private Packet packet;

    public Connection(Socket socket, MainServer mainServer) throws IOException {
        System.out.println("New connection from: " + socket.getInetAddress() + ":" + socket.getPort());
        ip = socket.getInetAddress();
        port = socket.getPort();
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.mainServer = mainServer;
    }

    @Override
    public void run() {
        currentUser = login();
        if (currentUser == null) {
            workerHandler();
            return;
        }

        String input;
        //user
        while (currentUser != null) {
            try {
                input = dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                new Scanner(System.in).nextInt();
                return;
            }

            Packet packet = Packet.convertStringToPacket(input);
            if (packet.getType().equals("user")) {
                UserPacket userPacket = UserPacket.convertStringToPacket(packet.getCommand());
                if (userPacket.getType().equals("update user")) {
                    User newUser = (new GsonBuilder()).create().fromJson(userPacket.getCommand(), User.class);
                    UserDatabase.updateUser(newUser);
                }
                else if (userPacket.getType().equals("get users")) {
                    //todo
                }
                else if (userPacket.getType().equals("get user")) {
                    int userId = Integer.parseInt(userPacket.getCommand());
                    User gotUser = UserDatabase.getUserById(userId);
                    UserPacket newUserPacket = new UserPacket("get user", (new GsonBuilder()).create().toJson(gotUser));
                    Packet newPacket = new Packet("user", newUserPacket.convertPacketToString());
                    try {
                        dataOutputStream.writeUTF(Packet.convertPacketToString(newPacket));
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                else if (userPacket.getType().equals("make request")) {
                    int friendId = Integer.parseInt(userPacket.getCommand());
                    User friend = UserDatabase.getUserById(friendId);
                    friend.addToRequests(friendId);
                }
            }

            if (packet.getType().equals("all chat")) {
                AllChatPacket allChatPacket = AllChatPacket.convertStringToPacket(packet.getCommand());
                if (allChatPacket.getType().equals("get chats")) {
                    ArrayList<Integer> usersChats = currentUser.getChatIds();
                    clientStatus = "all chat";

                    //send user chats to client
                    String[][] chatsIds = new String[usersChats.size()][2];
                    for (int i = 0; i < usersChats.size(); i++) {
                        Chat chat = ChatDatabase.getChatById(usersChats.get(i));
                        chatsIds[i][0] = Integer.toString(chat.getChatId());
                        chatsIds[i][1] = chat.getChatName();
                    }

                    Packet outPacket = new Packet("all chat", (new GsonBuilder()).create().toJson(chatsIds));
                    try {
                        dataOutputStream.writeUTF(Packet.convertPacketToString(outPacket));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                else if (allChatPacket.getType().equals("create chat")) {
                    Type listType = new TypeToken<ArrayList<Integer>>(){}.getType();
                    ArrayList<String> tempUsernames = (new GsonBuilder()).create().fromJson(allChatPacket.getTarget(), listType);
                    ArrayList<String> usernames = new ArrayList<>();
                    for (String name : tempUsernames) {
                        User user = UserDatabase.getUserByName(name);
                        if (user != null) usernames.add(name);
                    }
                    String chatName = allChatPacket.getChatName();
                    new Chat(usernames, chatName);
                    //todo maybe go to another menu???
                    continue;
                }
            }

            if (packet.getType().equals("chat menu")) {
                clientStatus = "chat menu";

                ChatMenuPacket chatMenuPacket = ChatMenuPacket.convertStringToPacket(packet.getCommand());
                if (chatMenuPacket.getType().equals("select chat")) {
                    int chatId = Integer.parseInt(chatMenuPacket.getCommand());
                    currentChat = ChatDatabase.getChatById(chatId);

                    boolean unseen = false;
                    for (Message message : currentChat.getMessages()) {
                        if (message.getSenderId() != currentUser.getUserId() && !message.isSeen()) {
                            unseen = true;
                            message.setSeen(true);
                        }
                    }

                    //add this user to active users
                    currentChat.addConnection(this);

                    //send messages to everybody
                    try {
                        if (!unseen) {
                            currentChat.sendMessageToConnection(this);
                        } else {
                            currentChat.sendMessageToCurrentConnections();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    continue;

                }
                else if (chatMenuPacket.getType().equals("create message")) {
                    String messageString = chatMenuPacket.getCommand();
                    Message message = (new GsonBuilder()).create().fromJson(messageString, Message.class);
                    currentChat.addMessage(message);
                }

                else if (chatMenuPacket.getType().equals("update message")) {
                    String messageString = chatMenuPacket.getCommand();
                    Message message = (new GsonBuilder()).create().fromJson(messageString, Message.class);
                    currentChat.updateMessage(message);
                }

//                else if (chatMenuPacket.getType().equals("delete for me")) {
//                    String messageId = chatMenuPacket.getCommand();
//                    currentChat.deleteForMe(messageId);
//                }
//
//                else if (chatMenuPacket.getType().equals("delete for all")) {
//                    String messageId = chatMenuPacket.getCommand();
//                    currentChat.deleteForAll(messageId);
//                }

                try {
                    currentChat.sendMessageToCurrentConnections();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User login() {

        return null;
    }

    public void workerHandler() {

    }

    //getters and setters

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
}
