package view;

import view.Enums.ConsoleColors;
import view.Enums.GameMenuCommands;
import view.Enums.StartingMenuCommands;

import java.util.Scanner;
import java.util.regex.Pattern;

public class StartingMenu extends Menu{
    public StartingMenu(Scanner scanner) {
        super(scanner);
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN +">>Welcome to the fucking Game<<");
        System.out.println(ConsoleColors.TEXT_BRIGHT_RED + "choose a menu to continue:");
        System.out.println(ConsoleColors.TEXT_RESET + "1.Login Menu");
        System.out.println("2.Signup Menu");
    }
    @Override
    public void run() {
        String command=scanner.nextLine();
        try {
            if (Pattern.matches(StartingMenuCommands.LOGIN_MENU.getRegex(), command)) {
                throw new Transition(new LoginMenu(scanner));
            }
            if (Pattern.matches(StartingMenuCommands.SIGNUP_MENU.getRegex(), command)) {
                throw new Transition(new SignUpMenu(scanner));
            } else {
                System.out.println("wrong input");
                throw new Transition(this);
            }
        }catch (Transition e) {
            e.getDestMenu().run();
        }
    }
}
