package view.Game;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.GamePlay.Player;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import view.Enums.BuildingMenuTypes;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;
import static view.Enums.GameMenuCommands.*;
public class CastleBuildingMenu extends Menu {
    private GameMenu gameMenu;
    public CastleBuildingMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {
        Guide();
        String command = scanner.nextLine();
        String output = "";
        Player player = gameMenu.getGame().getCurrentPlayer();
        GameController gameController = new GameController(this.gameMenu.getGame().getMap());
        if (command.matches("back")) {
            throw new Transition(gameMenu);
        }
        else if (command.matches(GameMenuCommands.BUILD_STONE_GATES.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_STONE_GATES.toString());
            output = gameController.buildStoneGatesMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.BUILD_TOWER.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_TOWER.toString());
            output = gameController.buildTowerMatcherHandler(matcher, gameMenu.getGame().getCurrentPlayer());
            System.out.println(output);
        }
        else if (command.matches(DROP_BUILDING.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, DROP_BUILDING.toString());
            output = gameController.dropBuildingMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(BUILD_DRAW_BRIDGE.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_DRAW_BRIDGE.toString());
            output = gameController.buildDrawbridgeMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(BUILD_WALL.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_WALL.toString());
            output = gameController.buildWallMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(BUILD_STAIR.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_STAIR.toString());
            System.out.println(gameController.buildStairMatcherHandler(matcher, gameMenu.getGame().getCurrentPlayer()));
        }
        else {
            System.out.println(formatPrinter(TEXT_RED, "", "invalid command"));
        }
        throw new Transition(this);
    }

    public void Guide() {
        gameMenu.getGame().getMap().showMap(gameMenu.getHalfSide());
        colorPrint(TEXT_RED,"================================================");
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>Castle Buildings<<"));
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", "possible commands:"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "1." + BUILD_STONE_GATES.toString() +
                " 2." + BUILD_TOWER + "\n3." + BUILD_WALL + " 4." + BUILD_BARRACKS + " 5." + BUILD_DRAW_BRIDGE + " 6." + BUILD_STAIR.toString()));
        System.out.println(formatPrinter(TEXT_BRIGHT_GREEN, "", "building traps: " + BUILD_TRAP.toString()));
        System.out.println(formatPrinter(TEXT_GREEN, "", "options: 1." + TrapsTypes.PITCH_DITCH + " 2." +
                TrapsTypes.KILLING_PIT + " 3." + TrapsTypes.CAGED_WAR_DOGS));

    }
}
