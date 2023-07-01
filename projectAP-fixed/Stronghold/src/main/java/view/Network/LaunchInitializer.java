package view.Network;

import Model.GamePlay.Game;
import Model.User;
import Model.UserDatabase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.Network.Server.DataBase;
import view.Network.Server.Server;
import view.fxmlMenu.GameLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LaunchInitializer implements Initializable {
    public static boolean isHost = true;
    public TextField SeverAddress;
    public VBox PlayersList;

    static Stage stage;
    public HBox LogHbox;
    public Label AnnouncedAddress;
    public static User user = UserDatabase.getUsers().get(0);
    static Pane fxmlPane;

    static {
        try {
            fxmlPane = FXMLLoader.load(GameLayout.class.getResource("/fxml/Launcher.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HBox LogHboxMap;
    public Button startGame;

    public GameClient gameClient;
    public VBox ServerVbox;
    public Button exit;
    public Button next;
    public VBox MapVbox;
    public Label ServerAddress1;
    public TextField mapNumber;
    public Label MapLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUp();
    }

    public void setUp() {
        if (isHost) {
            MapVbox.setVisible(true);
            ServerVbox.setVisible(true);
        } else {
            ServerVbox.setVisible(true);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void BackAction(ActionEvent actionEvent) {
    }

    public void NextAction(ActionEvent actionEvent) {
        UserDatabase.updateMaps();
        System.out.println(UserDatabase.getMaps().get(0).getName());
        if (isHost) {
            String s = SeverAddress.getText();
            if (s.matches("\\d\\d\\d\\d") && UserDatabase.getMapByName(mapNumber.getText()) != null) {
                try {
                    GameClient gameClient = new GameClient("localhost", Integer.parseInt(s), user, LaunchInitializer.this);
                    gameClient.setHost(true);
                    user.setGameClient(gameClient);
                    gotoWaitingAsHost(s);
                    MapLabel.setText(mapNumber.getText());
                    GameEvent makeGame = GameEvent.MAKE_GAME;
                    makeGame.fixMessage(mapNumber.getText(), user.getUsername());
                    user.getGameClient().dataOutputStream.writeUTF(makeGame.getMessage());
                    Thread.sleep(300);
                    GameEvent intro = GameEvent.INTRODUCE;
                    intro.fixMessage(user.getUsername());
                    user.getGameClient().dataOutputStream.writeUTF(intro.getMessage());
                    Thread.sleep(300);
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                    SeverAddress.setPromptText("there is no such a server");
                    SeverAddress.setText("");
                }
            } else {
                if (!s.matches("\\d\\d\\d\\d")) {
                    SeverAddress.setPromptText("invalid port");
                    SeverAddress.setText("");
                    return;
                } else {
                    mapNumber.setText("");
                    mapNumber.setPromptText("there is no such a map");
                }
            }
        } else {
            if (mapNumber.getText().length() > 0) {
                mapNumber.setText("you are not host");
                return;
            }
            mapNumber.setText("you are not host");
            String s = SeverAddress.getText();
            if (s.matches("\\d\\d\\d\\d")) {
                try {
                    GameClient gameClient = new GameClient("localhost", Integer.parseInt(s), user, LaunchInitializer.this);
                    gameClient.setHost(false);
                    user.setGameClient(gameClient);
                    GameEvent intro = GameEvent.INTRODUCE;
                    intro.fixMessage(user.getUsername());
                    System.out.println(user.getUsername());
                    user.getGameClient().dataOutputStream.writeUTF(intro.getMessage());
                    Thread.sleep(300);
                    GameEvent gameEvent = GameEvent.JOIN_T0_GAME;
                    gameEvent.fixMessage(user.getUsername());
                    user.getGameClient().dataOutputStream.writeUTF(gameEvent.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                    SeverAddress.setPromptText("there is no such a server");
                    SeverAddress.setText("");
                }
            } else {
                SeverAddress.setPromptText("invalid port");
                SeverAddress.setText("");
                return;
            }
        }
    }
    public void gotoWaiting (String numport){
        next.setVisible(false);
        LogHboxMap.setVisible(true);
        ServerVbox.setVisible(false);
        MapVbox.setVisible(false);
        PlayersList.setVisible(true);
        LogHbox.setVisible(true);
        AnnouncedAddress.setText(numport);
    }

    public void gotoWaitingAsHost(String numport) {
        startGame.setVisible(true);
        next.setVisible(false);
        LogHboxMap.setVisible(true);
        ServerVbox.setVisible(false);
        MapVbox.setVisible(false);
        PlayersList.setVisible(true);
        LogHbox.setVisible(true);
        AnnouncedAddress.setText(numport);
        Label label = new Label();
        label.setFont(Font.font("cambria", FontWeight.BOLD, 16));
        label.setText("1. "+user.getUsername()+" (HOST)");
        PlayersList.getChildren().add(label);
    }

    public void startAction(ActionEvent actionEvent) {
        GameEvent gameEvent = GameEvent.START_GAME;
        gameEvent.fixMessage(null);
        try {
            user.getGameClient().dataOutputStream.writeUTF(gameEvent.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
