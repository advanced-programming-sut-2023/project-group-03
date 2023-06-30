package view.Network;

import Model.GamePlay.Game;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import view.fxmlMenu.GameLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LaunchInitializer implements Initializable{
    static Pane fxmlPane;

    static {
        try {
            fxmlPane = FXMLLoader.load(GameLayout.class.getResource("/fxml/Launcher.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VBox ServerVbox;
    public Label ServerAddress;
    public Button exit;
    public Button next;
    public VBox MapVbox;
    public Label ServerAddress1;
    public TextField mapNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList strings = new ArrayList<String>();
        strings.add("salam");
        strings.add("bye");
        strings.add("vai");
        // listOfPlayers.setItems(FXCollections.observableList(strings));
    }
}
