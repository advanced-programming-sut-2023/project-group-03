package org.example;

import Model.Field.GameMap;
import Model.User;
import Model.UserDatabase;
import controller.gameControllers.GraphicController;
import controller.gameControllers.MapController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Game.SetGameMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainGraphicTest extends Application {
    public static void main(String[] args) {
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
       GraphicController.setupGamePlayers("first",
               new ArrayList<>(Arrays.asList(user1.getUsername(), user2.getUsername()))
               , user2, primaryStage);
    //    Menu.RunHandler();
    //    GameThread gameThread = new GameThread(Menu);
    //    gameThread.start();
    }
}
