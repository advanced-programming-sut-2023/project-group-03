package view;

import view.Enums.ConsoleColors;

import java.util.Scanner;

public class StartingMenu {
    public StartingMenu() {
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN +">>Welcome to the fucking Game<<");
        System.out.println(ConsoleColors.TEXT_BRIGHT_RED + "choose a menu to continue:");
        System.out.println(ConsoleColors.TEXT_RESET + "1.Login Menu");
        System.out.println("2.Signup Menu");
    }

    public void run(Scanner scanner) {

    }
}
