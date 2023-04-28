package org.example;

import Model.Field.GameMap;
import Model.User;
import Model.UserDatabase;
import com.google.gson.Gson;
import controller.gameControllers.MapController;
import view.Game.MapMenu;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        GameMap map = new GameMap(20);
//        map.setCenter(10, 10);
//        map.showMap(4);
        final File mapFolder = new File("src/main/resources/maps");
        for (File file : mapFolder.listFiles()) {
            GameMap map = MapController.loadMap(file);
            UserDatabase.addMap(map);
        }
        Scanner scanner = new Scanner(System.in);
        MapMenu mapMenu = new MapMenu(scanner, new User("", "", "", "", ""));
        mapMenu.RunHandler();
    }
}