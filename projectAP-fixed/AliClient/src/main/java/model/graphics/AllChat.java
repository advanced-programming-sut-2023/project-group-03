package model.graphics;

import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.user.User;
import model.client.AllChatPacket;
import model.client.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class AllChat extends Application {
    private User user;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String[][] chats;

    public AllChat(User user, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.user = user;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }
    private static HashSet<String> selectedUsers = new HashSet<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //getting names of my chats
            AllChatPacket allChatPacket = new AllChatPacket("get chat", "get chats");
            Packet getChatPacket = new Packet("all chat", AllChatPacket.convertPacketToString(allChatPacket));
            dataOutputStream.writeUTF(getChatPacket.convertPacketToString());
            String resultChats = dataInputStream.readUTF();
            chats = (new GsonBuilder()).create().fromJson(resultChats, String[][].class);

            //graphic of all chats
            ScrollPane scrollPane = new ScrollPane();
            VBox chatBoxes = new VBox();
            scrollPane.setPrefSize(1240, 720);
            scrollPane.setContent(chatBoxes);
            chatBoxes.setAlignment(Pos.CENTER);

            Button backButton = new Button("back");
            Button createButton = new Button("create new room");
            chatBoxes.getChildren().addAll(backButton, createButton);

            createButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    HBox addUserBox = new HBox();
                    TextField usernameField = new TextField();
                    TextField chatNameField = new TextField();
                    chatNameField.setPromptText("chat name");
                    usernameField.setPromptText("username");
                    Button addButton = new Button("add");
                    Button acceptButton = new Button("accept");

                    addUserBox.getChildren().addAll(chatNameField, usernameField, addButton, acceptButton);
                    chatBoxes.getChildren().add(2, addUserBox);

                    addButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            selectedUsers.add(usernameField.getText());
                            usernameField.setText("");
                            usernameField.setPromptText("username");
                        }
                    });

                    acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            ArrayList<String> names = new ArrayList<>(selectedUsers);
                            String data = (new GsonBuilder()).create().toJson(names);
                            AllChatPacket createRoomPacket = new AllChatPacket("create chat", data);
                            createRoomPacket.setChatName(chatNameField.getText());
                            Packet packetCreate = new Packet("all chat", AllChatPacket.convertPacketToString(createRoomPacket));
                            selectedUsers.clear();
                            try {
                                dataOutputStream.writeUTF(packetCreate.convertPacketToString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            chatBoxes.getChildren().remove(addUserBox);
                        }
                    });

                    backButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //todo
                        }
                    });
                }
            });

            for (String[] chat : chats) {
                Button button = new Button(chat[1]);
                button.setPrefWidth(scrollPane.getPrefWidth() / 2);
                chatBoxes.getChildren().add(button);

                //moving to chat menu by clicking
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
            }

            primaryStage.setScene(new Scene(scrollPane));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
