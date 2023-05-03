package view.Game;

import Model.Buildings.Enums.GeneratorTypes;
import Model.GamePlay.Game;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;

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
        if (command.matches("back")) {
            throw new Transition(gameMenu);
        } else if (command.matches(GameMenuCommands.BUILD_REST.toString())) {

        } else if (command.matches(GameMenuCommands.BUILD_GENERATOR.toString())) {

        } else {
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
