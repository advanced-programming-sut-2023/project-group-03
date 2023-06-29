package org.example;

import Model.Field.GameMap;
import Model.User;
import Model.UserDatabase;
import controller.gameControllers.MapController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Game.Phase2Test.GameGraphic;
import view.Game.Phase2Test.GameThread;
import view.Game.SetGameMenu;

import java.io.File;
import java.util.Scanner;

public class MainAliP2 extends Application {
    public static void main(String[] args) {
        // System.out.println("sdl;kfjsdl;kjflsadsdflksdjl;fjsdl;fjsdlj");
        // return;
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       primaryStage.setScene(new Scene(new Pane()));
       primaryStage.show();
       final File mapFolder = new File("src/main/resources/maps");
       for (File file : mapFolder.listFiles()) {
           GameMap map = MapController.loadbufferMap(file);
           UserDatabase.addMap(map);
       }
       UserDatabase.loadSavedData();
       User user1 = new User("secondUser", "1", "puria", "adf", "ammat");
       User user2 = new User("firstUser", "2", "mehran", "adf", "ammat");
       UserDatabase.addUser(user1);
       UserDatabase.addUser(user2);
       SetGameMenu Menu = new SetGameMenu((new Scanner(System.in)),user1, primaryStage);
    //    Menu.RunHandler();
       GameThread gameThread = new GameThread(Menu);
       gameThread.start();
    }
}
