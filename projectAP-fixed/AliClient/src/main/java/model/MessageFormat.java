package model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Enum.ReactionEnum;
import model.user.User;

import java.io.DataOutputStream;

public class MessageFormat {
    public static Image addReactionImage = new Image(
            MessageFormat.class.getResource("/images/reactions/addReaction.png").toExternalForm()
    );
    public static Image seenImage = new Image(
            MessageFormat.class.getResource("/images/seen.png").toExternalForm()
    );
    public static Image notSeenImage = new Image(
            MessageFormat.class.getResource("/images/notSeen.png").toExternalForm()
    );
    private VBox vBox = new VBox();
    private HBox upperBar = new HBox();
    private ImageView seenImageView = new ImageView();
    private Label sender = new Label();
    private Label time = new Label();
    private Label content = new Label();
    private HBox reactionBox = new HBox();
    private HBox addReactionBox = new HBox();
    private Reaction[] reactions = new Reaction[5];
    private static DataOutputStream dataOutputStream;
    private Message message;
    private VBox changeMessageBox;
    private Button editButton;
    private Button deleteForMeButton;
    private Button deleteForAllButton;
    private User currentUser;
    private VBox screenBox;

    public MessageFormat(VBox screenBox, DataOutputStream dataOutputStream, User user) {
        MessageFormat.dataOutputStream = dataOutputStream;
        this.currentUser = user;

        changeMessageBox = new VBox();
        editButton = new Button("Edit");
        deleteForMeButton = new Button("del for me");
        deleteForAllButton = new Button("del for all");
        changeMessageBox.getChildren().addAll(editButton, deleteForMeButton, deleteForAllButton);
        if (message.getSenderId() != user.getUserId()) changeMessageBox.setVisible(false);

        if (message.isSeen()) seenImageView.setImage(seenImage);
        else seenImageView.setImage(notSeenImage);

        upperBar.getChildren().addAll(sender, time, changeMessageBox, seenImageView);


        vBox.getChildren().addAll(upperBar, content, reactionBox);
        this.screenBox = screenBox;
        screenBox.getChildren().add(vBox);

        ImageView addReactionView = new ImageView(addReactionImage);
        Button addReactionButton = new Button();
        addReactionButton.setGraphic(addReactionView);

        //setting button's on action
        addReactionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vBox.getChildren().remove(reactionBox);
                vBox.getChildren().add(addReactionBox);
            }
        });

        deleteForMeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                message.deleteForMeFunc(MessageFormat.dataOutputStream);
            }
        });

        deleteForAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                message.deleteForAllFunc(MessageFormat.dataOutputStream);
            }
        });

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextArea editTextArea = new TextArea();
                editTextArea.setText(content.getText());
                Button acceptButton = new Button("accept");
                HBox editBox = new HBox(editTextArea, acceptButton);
                editBox.setPrefSize(vBox.getPrefWidth(), vBox.getPrefHeight());

                vBox.getChildren().add(editBox);

                acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        message.editMessage(editTextArea.getText(), MessageFormat.dataOutputStream);
                        vBox.getChildren().remove(editBox);
                    }
                });
            }
        });

        for (ReactionEnum reactionEnum : ReactionEnum.values()) {
            reactions[reactionEnum.getCode()] = new Reaction(reactionEnum, 0, reactionBox);

            ImageView reactionImageView = new ImageView(reactionEnum.getImage());
            Button reactionUpButton = new Button();
            reactionUpButton.setGraphic(reactionImageView);
            addReactionBox.getChildren().add(reactionUpButton);

            reactionUpButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    message.updateReaction(user.getUserId(), reactionEnum.getCode(), MessageFormat.dataOutputStream);
                    vBox.getChildren().remove(addReactionBox);
                    vBox.getChildren().add(reactionBox);
                }
            });
        }
    }

    public void updateMessage() {
        if (message == null) {
            screenBox.setVisible(false);
            return;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sender.setText(message.getSenderUsername());
                time.setText(message.getTime());
                content.setText(message.getContent());

                //update seen
                if (seenImageView.getImage().equals(seenImage) && !message.isSeen()) {
                    seenImageView.setImage(notSeenImage);
                }
                else if (seenImageView.getImage().equals(notSeenImage) && message.isSeen()) {
                    seenImageView.setImage(seenImage);
                }

                //update change buttons
                if (message.getSenderId() != currentUser.getUserId()) changeMessageBox.setVisible(false);
                else changeMessageBox.setVisible(true);



                //reactions
                for (int i = 0; i < message.getReactionsAmount().length; i++) {
                    reactions[i].setCounter(message.getReactionsAmount()[i]);
                    reactions[i].updateReaction();
                }

                screenBox.setVisible(true);
            }
        });
    }

    //getters and setters


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
