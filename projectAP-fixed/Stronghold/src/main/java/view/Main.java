package view;

import Model.Field.GameMap;
import Model.UserDatabase;
import controller.gameControllers.MapController;
import view.StartingMenu;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final File mapFolder = new File("src/main/resources/maps");
        for (File file : mapFolder.listFiles()) {
            GameMap map = MapController.loadbufferMap(file);
            UserDatabase.addMap(map);
        }

        System.out.println(UserDatabase.getMaps().size());
        for (GameMap map : UserDatabase.getMaps()) {
            System.out.println(map.getName());
        }

        UserDatabase.loadSavedData();
        Scanner scanner = new Scanner(System.in);
        StartingMenu startingMenu=new StartingMenu(scanner);
        startingMenu.RunHandler();
        UserDatabase.saveUsers();
    }
}