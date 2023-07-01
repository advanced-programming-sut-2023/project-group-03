package model;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.user.User;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class ChatShower {
    private ScrollPane scrollPane;
    private VBox screenBox;
    ArrayList<MessageFormat> messageFormats = new ArrayList<>();

    public ChatShower(int width, int height, Pane pane, DataOutputStream dataOutputStream, User user) {
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(width, height);
        screenBox = new VBox();
        scrollPane.setContent(screenBox);
//        pane.getChildren().add(scrollPane);

        for (int i = 0; i < 100; i++) {
            messageFormats.add(new MessageFormat(screenBox, dataOutputStream, user));
        }
    }

    public void updateMessages(ArrayList<Message> messages) {
        int size = messages.size();
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (messages.get(i) == null) break;
            //todo delete for me or delete for all
            counter++;
            messageFormats.get(i).setMessage(messages.get(i));
        }

        for (int j = counter; counter < messageFormats.size(); counter++) {
            messageFormats.get(j).setMessage(null);
        }

        for (MessageFormat messageFormat : messageFormats) {
            messageFormat.updateMessage();
        }
    }

    //getters and setters

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
}
