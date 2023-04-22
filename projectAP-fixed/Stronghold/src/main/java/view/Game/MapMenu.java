package view.Game;

import Model.Field.GameMap;
import Model.User;
import Model.UserDatabase;
import controller.ControllerFunctions;
import controller.gameControllers.MapController;
import view.Enums.ConsoleColors;
import view.Enums.MapMenuCommands;
import view.MainMenu;
import view.Menu;
import view.StartingMenu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;

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
        int displaySize = 3;
        String output;
        do {
            output = intializeSize();
            System.out.println(output);
        } while (output.equals("wrong input"));
        System.out.println(intializeName());
        map.showMap(displaySize);
        MapController mapController = new MapController(map);
        while (true) {
            ConsoleColors.colorPrint(TEXT_GREEN, "your command:");
            String command = scanner.nextLine();
            if (command.matches(MapMenuCommands.MOVE_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.MOVE_ALI.getRegex());
                System.out.println(mapController.moveMap(matcher));
            }
            else if (command.matches(MapMenuCommands.DROPTREE_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.DROPTREE_ALI.getRegex());
                System.out.println(mapController.dropTree(matcher));
            }
            else if (command.matches(MapMenuCommands.DROPROCK_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.DROPROCK_ALI.getRegex());
                System.out.println(mapController.dropRock(matcher));
            }
            else if (command.matches(MapMenuCommands.DROPUNIT.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.DROPUNIT.getRegex());
                //
            }
            else if (command.matches(MapMenuCommands.SET_TEXTURE_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SET_TEXTURE_ALI.getRegex());
                System.out.println(mapController.setTexture(matcher));
            }
            else if (command.matches(MapMenuCommands.SHOW_DETAILS_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_DETAILS_ALI.getRegex());
                System.out.println(mapController.showDetails(matcher));
            } else if (command.matches(MapMenuCommands.BACK.getRegex())) {
                throw new Transition(new MainMenu(scanner, user));
            } else if (command.matches(MapMenuCommands.CLEAR_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.CLEAR_ALI.getRegex());
                System.out.println(mapController.clearField(matcher));
            } else {
                colorPrint(TEXT_RED, "invalidCommand");
            }
            map.showMap(displaySize);
        }
    }

    private void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>Map menu<<" + " User: " + user.getUsername() + ConsoleColors.TEXT_RESET);
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
        map.setCenter(map.getSize()/ 2, map.getSize()/ 2);
        UserDatabase.addMap(map);
        return "setting name: "+command;
    }
}
