package model.graphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ChatShower;
import model.Message;
import model.user.User;
import model.client.ChatListener;
import model.client.ChatMenuPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class ChatMenu extends Application {
    private ChatShower chatShower;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private int chatId;
    private ChatListener chatListener;
    private int width;
    private int height;
    private User currentUser;

    public ChatMenu(User user, DataInputStream dataInputStream, DataOutputStream dataOutputStream, int chatId, int width, int height) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.chatId = chatId;
        this.height = height;
        this.width = width;
        this.currentUser = user;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            ChatMenuPacket chatMenuPacket = new ChatMenuPacket("select chat", Integer.toString(chatId));
            dataOutputStream.writeUTF(ChatMenuPacket.convertPacketToString(chatMenuPacket));//todo put in a better place

            VBox chatPane = new VBox();
            chatPane.setPrefSize(width, height);

            Button backButton = new Button("back");
            chatPane.getChildren().add(backButton);

            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //todo go back to allChat application
                }
            });

            //chat shower
            chatShower = new ChatShower(width, height, chatPane, dataOutputStream, currentUser);
            chatPane.getChildren().add(chatShower.getScrollPane());

            //message box for getting message
            HBox messageBox = new HBox();
            TextArea messageField = new TextArea();
            Button sendButton = new Button();
            messageBox.getChildren().addAll(messageField, sendButton);

            sendButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String messageContent = messageField.getText();
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String hh = Integer.toString(localDateTime.getHour());
                    String mm = Integer.toString(localDateTime.getMinute());

                    Message message = new Message(currentUser.getUsername(),hh + " : " + mm , messageContent, currentUser.getUserId());

                    //sending to server
                    ChatMenuPacket newMessagePacket = new ChatMenuPacket("create message", message.convertMessageToJson());
                    try {
                        dataOutputStream.writeUTF(ChatMenuPacket.convertPacketToString(newMessagePacket));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    messageField.setText("");
                }
            });



            chatListener = new ChatListener(dataInputStream, dataOutputStream, chatShower);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
