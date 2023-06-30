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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Network.Server.DataBase;
import view.Network.Server.Server;
import view.fxmlMenu.GameLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LaunchInitializer implements Initializable{
    public boolean isHost = true;
    public TextField SeverAddress;
    private User user;
    static Pane fxmlPane;

    static {
        try {
            fxmlPane = FXMLLoader.load(GameLayout.class.getResource("/fxml/Launcher.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VBox ServerVbox;
    public Button exit;
    public Button next;
    public VBox MapVbox;
    public Label ServerAddress1;
    public TextField mapNumber;

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
                System.out.println("in");
                try {
                    GameClient gameClient = new GameClient("localhost", Integer.parseInt(s), user);
                } catch (Exception e) {
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
            String s = SeverAddress.getText();
            if (s.matches("\\d\\d\\d\\d")) {

            } else {
                SeverAddress.setPromptText("invalid port");
            }
        }
    }
}
