package view;

import Model.User;
import view.Enums.ConsoleColors;
import view.Enums.LoginMenuCommands;
import view.Enums.ProfileMenuCommands;
import controller.ProfileMenuController;
import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class ProfileMenu extends Menu{
    User user;

    public ProfileMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() throws Transition {
        showGuide();
        String command = scanner.nextLine();
        String output = null;
        ProfileMenuController controller = new ProfileMenuController(user);
        if (command.matches(ProfileMenuCommands.BACK.getRegex())) {
            throw new Transition(new MainMenu(scanner, user));
        }
        else if (command.matches(ProfileMenuCommands.CHANGE_USERNAME.getRegex())) {
            Matcher matcher = ProfileMenuCommands.CHANGE_USERNAME.getMatcher(command);
            //output=controller.changeUsername()
        }
        else if (command.matches(ProfileMenuCommands.CHANGE_NICKNAME.getRegex())) {
            Matcher matcher = ProfileMenuCommands.CHANGE_NICKNAME.getMatcher(command);
        }
        else if (command.matches(ProfileMenuCommands.CHANGE_PASSWORD.getRegex())) {
            Matcher matcher=ProfileMenuCommands.CHANGE_PASSWORD.getMatcher(command);
            output = controller.changePassword(matcher);
        }
        else if (command.matches(ProfileMenuCommands.CHANGE_EMAIL.getRegex())) {
            Matcher matcher = ProfileMenuCommands.CHANGE_EMAIL.getMatcher(command);
        }
        else if (command.matches(ProfileMenuCommands.CHANGE_SLOGAN.getRegex())) {
            Matcher matcher = ProfileMenuCommands.CHANGE_SLOGAN.getMatcher(command);
        }
        else if (command.matches(ProfileMenuCommands.REMOVE_SLOGAN.getRegex())) {
            output = controller.removeSlogan();
        }
        else if (command.matches(ProfileMenuCommands.DISPLAY_HIGHSCORE.getRegex())) {
            output = controller.displayHighScore();
        }
        else if (command.matches(ProfileMenuCommands.DISPLAY_RANK.getRegex())) {
            output = controller.displayRank();
        }
        else if (command.matches(ProfileMenuCommands.DISPLAY_SLOGAN.getRegex())) {
            output = controller.displaySlogan();
        }
        else if (command.matches(ProfileMenuCommands.DISPLAY_ALL.getRegex())) {
            output = controller.displayAllInfo();
        }
        else {
            colorPrint(TEXT_RED, "invalid command");
            throw new Transition(this);
        }
        System.out.println(output);
        throw new Transition(this);
    }



    private void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>profile menu<<" + ConsoleColors.TEXT_RESET);
        colorPrint(ConsoleColors.TEXT_YELLOW, "back: backing to main menu");
        colorPrint(ConsoleColors.TEXT_YELLOW, "your command:");
    }
}
