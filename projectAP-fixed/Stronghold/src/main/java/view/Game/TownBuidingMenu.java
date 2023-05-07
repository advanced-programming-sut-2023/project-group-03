package view.Game;

import Model.Buildings.Enums.GeneratorTypes;
import Model.GamePlay.Game;
import Model.GamePlay.Player;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;
import static view.Enums.ConsoleColors.TEXT_BRIGHT_YELLOW;

public class TownBuidingMenu extends Menu {
    GameMenu gameMenu;

    public TownBuidingMenu(Scanner scanner, GameMenu gameMenu) {
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
        else if (command.matches(GameMenuCommands.BUILD_REST.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_REST.toString());
            output = gameController.buildRestMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.BUILD_GENERATOR.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_GENERATOR.toString());
            output = gameController.buildGeneratorMatcherHandler(matcher, player);
            System.out.println(output);
        }
        else {
            System.out.println(formatPrinter(TEXT_RED, "", "invalid command"));
        }
    }

    public void guide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>Town Buildings<<"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "possible format of commands:"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "1." + GameMenuCommands.BUILD_REST.toString() +
                " 2. " + GeneratorTypes.CHURCH + " 3." + GeneratorTypes.CATHEDRAL + " 4.dig Abb" + " 5.chah"));
    }
}
