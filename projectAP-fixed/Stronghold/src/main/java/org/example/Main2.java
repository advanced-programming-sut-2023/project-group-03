package org.example;

import Model.User;
import view.Enums.GameMenuCommands;
import view.Game.SetGameMenu;
import view.StartingMenu;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        User user = new User("blah", "saalm", "adf", "adf", "ammat");
        SetGameMenu Menu = new SetGameMenu((new Scanner(System.in)),user);
        Menu.RunHandler();
    }
}
