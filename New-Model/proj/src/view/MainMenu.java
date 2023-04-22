package view;

import model.User;
import view.Enums.ConsoleColors;

import java.util.Scanner;

public class MainMenu extends Menu{
    User user;

    public MainMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>main menu<<");
        System.out.println(ConsoleColors.TEXT_YELLOW + "choose a menu to continue:");
        System.out.println(ConsoleColors.TEXT_RESET + "1.profile menu");
        System.out.println("2.start Game");
        System.out.println("3.map menu");
        System.out.println("4.back to start menu");
    }

    @Override
    public void run() throws Transition {

    }
}
