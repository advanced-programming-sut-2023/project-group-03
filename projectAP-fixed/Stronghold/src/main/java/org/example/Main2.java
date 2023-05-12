package org.example;

import Model.Field.GameMap;
import Model.User;
import Model.UserDatabase;
import controller.gameControllers.MapController;
import view.Enums.GameMenuCommands;
import view.Game.SetGameMenu;
import view.StartingMenu;

import java.io.File;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        final File mapFolder = new File("src/main/resources/maps");
        for (File file : mapFolder.listFiles()) {
            GameMap map = MapController.loadMap(file);
            UserDatabase.addMap(map);
        }
        User user1 = new User("secondUser", "1", "adf", "adf", "ammat");
        User user2 = new User("firstUser", "2", "adf", "adf", "ammat");
        UserDatabase.addUser(user1);
        UserDatabase.addUser(user2);
        SetGameMenu Menu = new SetGameMenu((new Scanner(System.in)),user1);
        Menu.RunHandler();
    }
}
