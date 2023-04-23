package view;

import Model.User;
import view.Enums.ConsoleColors;
import view.Enums.GameMenuCommands;
import view.Enums.StartingMenuCommands;
import view.Game.MapMenu;

import java.util.Scanner;
import java.util.regex.Pattern;

import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class StartingMenu extends Menu{
    User user;
    public StartingMenu(Scanner scanner) {
        super(scanner);
    }

    public void run() throws Transition {
        showGuide();
        String command=scanner.nextLine();
        if (Pattern.matches(StartingMenuCommands.LOGIN_MENU.getRegex(), command)) {
            if (user != null) {
                throw new Transition(new MainMenu(scanner, user));
            }
            throw new  Transition(new LoginMenu(scanner));
        }
        else if (Pattern.matches(StartingMenuCommands.SIGNUP_MENU.getRegex(), command)) {
            throw  new Transition(new SignUpMenu(scanner));
        }
        else if (Pattern.matches(StartingMenuCommands.EXIT.getRegex(), command)) {
            throw  new Transition(new StartingMenu(scanner));
        } else {
            colorPrint(TEXT_RED, "invalid command");
            throw new Transition(this);
        }
    }

    public void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_GREEN +">>Welcome to the fucking Game<<");
        System.out.println(ConsoleColors.TEXT_YELLOW + "choose a menu to continue:");
        System.out.println(ConsoleColors.TEXT_RESET + "1.login menu");
        System.out.println("2.signup menu");
        System.out.println("3.back");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
