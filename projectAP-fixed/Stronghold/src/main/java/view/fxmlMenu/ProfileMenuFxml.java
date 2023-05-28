package view.fxmlMenu;

import Model.Defaults;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Controllers.ProfileMenuFXController;

import java.io.IOException;

public class ProfileMenuFxml extends Application {
    public static Pane pane;

    static {
        try {
            pane = FXMLLoader.load(ProfileMenuFxml.class.getResource("/fxml/ProfileMenuPane.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ProfileMenuFXController.setGamePane(pane);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}