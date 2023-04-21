package view.Game;

import Model.Field.GameMap;
import Model.User;
import view.Enums.ConsoleColors;
import view.Menu;
import view.Transition;

import java.util.Scanner;

import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class MapMenu extends Menu {
    private User user;
    private GameMap map;
    public MapMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
        showGuide();
    }

    @Override
    public void run() throws Transition {
        String output;
        do {
            output = intializeSize();
            System.out.println(output);
        } while (output.equals("wrong input"));
        System.out.println(intializeName());

    }

    private void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>Map menu<<" + ConsoleColors.TEXT_RESET);
        colorPrint(ConsoleColors.TEXT_YELLOW, "exit: backing to main menu");
    }

    private String intializeSize() {
        colorPrint(ConsoleColors.TEXT_BRIGHT_YELLOW,"select a size: 1.(200*200) 2.(400*400)");
        String command = scanner.nextLine();
        if (command.matches("1")) {
            map = new GameMap(200);
            return "setting size: 200";
        } else if (command.matches("2")) {
            map = new GameMap(400);
            return "setting size: 400";
        } else {
            return "wrong input";
        }
    }

    private String intializeName() {
        colorPrint(ConsoleColors.TEXT_BRIGHT_YELLOW, "name of the map:");
        String command = scanner.nextLine();
        map.setName(command);
        return "setting name:"+command;
    }
}
