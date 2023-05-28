package view.Controllers;

import Model.Defaults;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.LoginMenu;
import view.fxmlMenu.EndingMenu;
import view.fxmlMenu.LoginMenuFxml;
import view.fxmlMenu.MainMenu;
import view.fxmlMenu.RegisterMenu;

import java.awt.*;
import java.io.IOException;


public class StartingController extends Application {
    public static Pane startingMenu;

    static {
        try {
            startingMenu = FXMLLoader.load(MainMenu.class.getResource("/fxml/StartingMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit(ActionEvent actionEvent) throws Exception {
        new EndingMenu().start(Defaults.getCurrentStage());
    }

    public void login(ActionEvent actionEvent) throws Exception {
        new LoginMenuFxml().start(Defaults.getCurrentStage());
    }

    public void register(ActionEvent actionEvent) throws Exception {
        new RegisterMenu().start(Defaults.getCurrentStage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Defaults.setCurrentStage(stage);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        startingMenu.setPrefSize(width, height);
        Scene scene = new Scene(startingMenu);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
