package view.Game;

import Model.Buildings.Enums.GeneratorTypes;
import Model.Buildings.Generators;
import Model.GamePlay.Player;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;
import static view.Enums.GameMenuCommands.*;

public class IndustryBuildingMenu extends Menu {
    GameMenu gameMenu;
    public IndustryBuildingMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {
        guide();
        String command = scanner.nextLine();
        String output = "";
        Player player = gameMenu.getGame().getCurrentPlayer();
        GameController gameController = new GameController(this.gameMenu.getGame().getMap());

        if (command.matches("back")) {
            throw new Transition(gameMenu);
        }
        else if (command.matches(BUILD_INVENTORY.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_INVENTORY.toString());
            output = gameController.buildInventoryMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(BUILD_MARKET.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_MARKET.toString());
            output = gameController.buildStoreMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(BUILD_GENERATOR.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, BUILD_GENERATOR.toString());
            output = gameController.buildGeneratorMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else {
            System.out.println(formatPrinter(TEXT_RED, "", "invalid command"));
        }
        throw new Transition(this);
    }

    public void guide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>Industry Buildings<<"));
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", "possible commands:"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "1." + BUILD_INVENTORY.toString()+
                " 2."+BUILD_MARKET+"\nformat: create ??? :"));
        System.out.println(formatPrinter(TEXT_GREEN, "", "1." + GeneratorTypes.IRON_MINE.getName() +
                " 2." + GeneratorTypes.STONE_MINE.getName() + " 3." + GeneratorTypes.PITCH_RIG.getName() +
                " 4." + GeneratorTypes.WOODCUTTER.getName()));
    }
}
