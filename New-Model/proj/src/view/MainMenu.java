package view;

<<<<<<< HEAD
import Model.Field.GameMap;
import Model.User;
=======
import model.User;
>>>>>>> origin/phase1pouria
import view.Enums.ConsoleColors;
import view.Enums.MainMenuCommands;
import view.Game.MapMenu;
import view.Game.SetGameMenu;

import java.util.Scanner;

import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class MainMenu extends Menu{
    User user;

    public MainMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() throws Transition {
        showGuide();
        String command = scanner.nextLine();
        if (command.matches(MainMenuCommands.BACK.getRegex())) {
            StartingMenu startingMenu = new StartingMenu(scanner);
            if (user.isStayLoggedIn()) {
                startingMenu.setUser(user);
            }
            throw new Transition(startingMenu);
        }
        if (command.matches(MainMenuCommands.MAP_MENU.getRegex())) {
            throw new Transition(new MapMenu(scanner,user));
        }
        if (command.matches(MainMenuCommands.START_GAME.getRegex())) {
            throw new Transition(new SetGameMenu(scanner, user));
        }
        if (command.matches(MainMenuCommands.PROFILE_MENU.getRegex())) {
            throw new Transition(new ProfileMenu(scanner, user));
        }
        colorPrint(TEXT_RED, "invalid command");
        throw new Transition(this);
    }

    public void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>main menu<<" + " User: " + user.getUsername());
        System.out.println(ConsoleColors.TEXT_YELLOW + "choose a menu to continue:");
        System.out.println(ConsoleColors.TEXT_RESET + "1.profile menu");
        System.out.println("2.start Game");
        System.out.println("3.map menu");
        System.out.println("4.back to start menu");
    }
}
