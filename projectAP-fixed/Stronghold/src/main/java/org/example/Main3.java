package org.example;

import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.User;
import Model.UserDatabase;
import controller.gameControllers.MapController;
import view.Enums.GameMenuCommands;
import view.Game.SetGameMenu;
import view.StartingMenu;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        final File mapFolder = new File("src/main/resources/maps");
        for (File file : mapFolder.listFiles()) {
            GameMap map = MapController.loadMap(file);
            UserDatabase.addMap(map);
        }

        GameMap gameMap = UserDatabase.getMapByName("second");
        HashSet<Player> players = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                Player owner = gameMap.getMap()[i][j].getOwner();
                if (!players.contains(owner)) players.add(owner);
            }
        }
        System.out.println(players.size());

        UserDatabase.loadSavedData();
        User user1 = new User("secondUser", "1", "puria", "adf", "ammat");
        User user2 = new User("firstUser", "2", "mehran", "adf", "ammat");
        UserDatabase.addUser(user1);
        UserDatabase.addUser(user2);
        SetGameMenu Menu = new SetGameMenu((new Scanner(System.in)),user1);
        Menu.RunHandler();
    }
}
