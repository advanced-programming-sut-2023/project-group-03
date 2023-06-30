package view.Network;

import Model.GamePlay.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LaunchInitializer {
    static Pane fxmlPane;

    static {
        try {
            fxmlPane = FXMLLoader.load(Game.class.getResource("/fxml/Launcher.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
}
