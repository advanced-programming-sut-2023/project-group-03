package org.example;

import Model.Field.GameMap;
import Model.User;
import com.google.gson.Gson;
import view.Game.MapMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        GameMap map = new GameMap(20);
//        map.setCenter(10, 10);
//        map.showMap(4);
        Scanner scanner = new Scanner(System.in);
        MapMenu mapMenu = new MapMenu(scanner, new User("", "", "", "", ""));
        mapMenu.RunHandler();
    }
}