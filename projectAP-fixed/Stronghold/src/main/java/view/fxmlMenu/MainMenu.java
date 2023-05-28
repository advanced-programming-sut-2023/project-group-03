package view.fxmlMenu;

import Model.Defaults;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.ProfileMenu;
import view.StartingMenu;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends Application {

    public static Pane mainPain;

    static {
        try {
            mainPain = FXMLLoader.load(StartingMenu.class.getResource("/fxml/MainMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startgame(ActionEvent actionEvent) {

    }

    public void map(ActionEvent actionEvent) {
    }

    public void profile(ActionEvent actionEvent) {
        try {
            new ProfileMenuFxml().start(Defaults.getCurrentStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void scoreboard(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) {
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = mainPain;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        root.setPrefSize(width, height);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
