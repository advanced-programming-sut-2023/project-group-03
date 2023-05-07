package view.Game;

import Model.Field.GameMap;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
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

import java.util.ArrayList;
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
        Drawable.setDrawables(new ArrayList<>());
        int displaySize = 3;
        String output;
        do {
            output = intializeSize();
            System.out.println(output);
        } while (output.equals("wrong input"));
        do {
            output = intializeNumberOfPlayers();
            System.out.println(output);
        } while (output.equals(TEXT_RED + "Invalid command"+ TEXT_RESET));
        do {
            output = intializeName();
            System.out.println(output);
        } while (output.equals(TEXT_RED + "Invalid name"+ TEXT_RESET));
        map.showMap(displaySize);
        MapController mapController = new MapController(map);
        Player currentPlayer = map.getPlayers()[0];
        while (true) {
            ConsoleColors.colorPrint(TEXT_GREEN, "your command:");
            String command = scanner.nextLine();
            if (command.matches("switch player: choose number of players from 1 to " + map.getNumberOfPlayers())) {
                while (true) {
                    command = scanner.nextLine();
                    if (!command.matches("\\d+")) {
                        System.out.println("a number please");
                    } else if (Integer.parseInt(command) > map.getNumberOfPlayers()) {
                        System.out.println("is greater than number of players");
                    } else if (Integer.parseInt(command) < 2) {
                        System.out.println("at least 2 player");
                    } else {
                        System.out.println("player switched");
                        currentPlayer = map.getPlayers()[Integer.parseInt(command) - 1];
                        break;
                    }
                }
            }
            else if (command.matches(MapMenuCommands.SET_OWNER_REC.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SET_OWNER_REC.getRegex());
                System.out.println(mapController.setOwner(matcher, currentPlayer));
            } else if (command.matches(MapMenuCommands.MOVE_ALI.getRegex())) {
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
            else if (command.matches(MapMenuCommands.DROP_UNIT_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.DROP_UNIT_ALI.getRegex());
                System.out.println(mapController.dropUnit(matcher, currentPlayer));
            }
            else if (command.matches(MapMenuCommands.DROP_BUILDING_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.DROP_BUILDING_ALI.getRegex());
                System.out.println(mapController.dropBuilding(matcher, currentPlayer));
            }
            else if (command.matches(MapMenuCommands.SET_TEXTURE_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SET_TEXTURE_ALI.getRegex());
                System.out.println(mapController.setTexture(matcher));
            }
            else if (command.matches(MapMenuCommands.SET_TEXTURE_REC_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SET_TEXTURE_REC_ALI.getRegex());
                System.out.println(mapController.setTextureRectangle(matcher));
            }
            else if (command.matches(MapMenuCommands.SHOW_DETAILS_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_DETAILS_ALI.getRegex());
                System.out.println(mapController.showDetails(matcher));
            }
            else if (command.matches(MapMenuCommands.BACK.getRegex())) {
                throw new Transition(new MainMenu(scanner, user));
            }
            else if (command.matches(MapMenuCommands.CLEAR_ALI.getRegex())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.CLEAR_ALI.getRegex());
                System.out.println(mapController.clearField(matcher));
            }
            else if (command.matches(MapMenuCommands.SAVE_MAP.getRegex())) {
                map.setDrawables(Drawable.getDrawables());
                mapController.saveMap(map);
                UserDatabase.addMap(map);
                System.out.println("map saved");
            }
            else {
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
        command = command.trim();
        if (UserDatabase.getMapByName(command) != null) {
            map = UserDatabase.getMapByName(command);
            return "map loaded";
        }
        else if (command.matches("[a-z1-9A-Z]+")) {
            map.setName(command);
            map.setCenter(map.getSize() / 2, map.getSize() / 2);
            UserDatabase.addMap(map);
            return "setting name: " + command;
        } else {
            return TEXT_RED + "Invalid name"+ TEXT_RESET;
        }
    }

    private String intializeNumberOfPlayers() {
        colorPrint(TEXT_YELLOW, "choose number of players: 2 or 3 or 4");
        String command = scanner.nextLine();
        if (command.matches("2")) {
            map.setNumberOfPlayers(2);
        } else if (command.matches("3")) {
            map.setNumberOfPlayers(3);
        } else if (command.matches("4")) {
            map.setNumberOfPlayers(4);
        } else {
            return ConsoleColors.formatPrinter(TEXT_RED, "", "Invalid command");
        }
        return ConsoleColors.formatPrinter("", "", "setting number of players: "+command);
    }
}
