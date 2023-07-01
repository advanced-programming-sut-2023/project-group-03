package model.graphics;

import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.client.Packet;
import model.client.UserPacket;
import model.user.User;
import model.user.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FriendshipFunctions {
    public static ScrollPane getWaitingRequestsBox(int width, int userId) throws IOException {
        User user = UserDatabase.getUserFromServer(userId);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(width, 100);
        HBox waitingBox = new HBox();
        waitingBox.setPrefHeight(100);
        waitingBox.setPrefWidth(width);

        ArrayList<Integer> waitingUsers = user.getWaitingRequests();

        for (int id : waitingUsers) {
            VBox requestVbox = new VBox();
            User requester = UserDatabase.getUserFromServer(id);
            Label username = new Label(requester.getUsername());
            username.setPrefSize(100, 80);
            Button acceptButton = new Button("accept");
            Button declineButton = new Button("decline");
            HBox buttons = new HBox(acceptButton, declineButton);
            buttons.setPrefSize(100, 20);
            acceptButton.setPrefSize(50, 20);
            declineButton.setPrefSize(50, 20);
            acceptButton.setVisible(false);
            declineButton.setVisible(false);

            username.setOnMouseEntered(e -> {
                acceptButton.setVisible(true);
                declineButton.setVisible(true);
            });
            username.setOnMouseExited(e -> {
                acceptButton.setVisible(false);
                declineButton.setVisible(false);
            });

            requestVbox.getChildren().addAll(username, buttons);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    user.addFriend(id);
                    user.removeFromRequest(id);
                    requestVbox.getChildren().removeAll(username, buttons);
                    try {
                        UserDatabase.sendUserToServer(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            declineButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    user.removeFromRequest(id);
                    requestVbox.getChildren().removeAll(username, buttons);
                    try {
                        UserDatabase.sendUserToServer(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            waitingBox.getChildren().add(requestVbox);
        }

        scrollPane.setContent(waitingBox);
        return scrollPane;
    }

    public static ScrollPane getFriendsBox(int width, int userId) throws IOException {
        User user = UserDatabase.getUserFromServer(userId);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(width, 100);
        HBox friendsBox = new HBox();
        friendsBox.setPrefHeight(100);

        for (int i = 0; i < user.getFriends().size(); i++ ) {
            Label label = new Label(UserDatabase.getUserFromServer(user.getFriends().get(i)).getUsername());
            friendsBox.getChildren().add(label);
        }

        scrollPane.setContent(friendsBox);
        return scrollPane;
    }

    public static Pane getProfile(int userId) throws IOException {
        User user = UserDatabase.getUserFromServer(userId);
        String username = user.getUsername();
        String nickname = user.getNickname();
        int highScore = user.getHighScore();
        String slogan = user.getSlogan();

        Label usernameLabel = new Label(username);
        Label nicknameLabel = new Label(nickname);
        Label scoreLabel = new Label(Integer.toString(highScore));
        Label sloganLabel = new Label(slogan);

        usernameLabel.setPrefSize(100, 30);
        nicknameLabel.setPrefSize(100, 30);
        scoreLabel.setPrefSize(100, 30);
        sloganLabel.setPrefSize(100, 30);

        VBox vBox = new VBox(usernameLabel, nicknameLabel, scoreLabel, sloganLabel);
        vBox.setPrefSize(100, 150);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    public static void searchForPlayer(int userId, Pane pane) throws IOException {
        User user = UserDatabase.getUserFromServer(userId);

        VBox vBox = new VBox();
        vBox.setLayoutX(pane.getPrefWidth() / 2);
        vBox.setLayoutY(pane.getPrefHeight() / 2);
        pane.getChildren().add(vBox);
        HBox searchBox = new HBox();
        searchBox.setPrefSize(100, 30);
        vBox.getChildren().add(searchBox);

        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("username");
        usernameTextField.setPrefSize(100, 30);

        Button acceptButton = new Button("accept");
        acceptButton.setPrefSize(40, 30);

        searchBox.getChildren().addAll(usernameTextField, acceptButton);

        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int friendId = UserDatabase.getUserByName(usernameTextField.getText()).getUserId();
                    Pane profilePane = getProfile(friendId);
                    vBox.getChildren().add(profilePane);
                    Button requestFriend = new Button("request friend");
                    Button cancelButton = new Button("cancel");
                    vBox.getChildren().addAll(requestFriend, cancelButton);

                    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            vBox.setVisible(false);
                            pane.getChildren().remove(vBox);
                        }
                    });

                    requestFriend.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            vBox.setVisible(false);
                            pane.getChildren().remove(vBox);
                            user.addToRequests(friendId);
                            try {
                                UserDatabase.requestFriendToServer(friendId, user);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }
}
