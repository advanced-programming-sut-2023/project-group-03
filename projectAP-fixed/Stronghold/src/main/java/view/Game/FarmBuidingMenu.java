package view.Game;

import Model.Buildings.Enums.GeneratorTypes;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;

import static view.Enums.ConsoleColors.*;
import static view.Enums.ConsoleColors.TEXT_GREEN;

public class FarmBuidingMenu extends Menu {
    private GameMenu gameMenu;
    public FarmBuidingMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu=gameMenu;
    }

    @Override
    public void run() throws Transition {
        String command = scanner.nextLine();
        if (command.matches("back")) {

        } else if (command.matches(GameMenuCommands.BUILD_GENERATOR.toString())) {

        } else {
            System.out.println(formatPrinter(TEXT_RED, "", "invalid command"));
        }
    }
    public void guide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>Farm Buildings<<"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "possible format of command: build ???"));
        System.out.println(formatPrinter(TEXT_GREEN, "", "1." + GeneratorTypes.HUNTERS_HUT.getName() +
                " 2. " + GeneratorTypes.BARLEY_FARM.getName() + " 3." + GeneratorTypes.DAIRY_FARM.getName() +
                " 4." + GeneratorTypes.WHEAT_FARM.getName() + "\n5." + GeneratorTypes.ORCHARD.getName() +
                " 6." + GeneratorTypes.BAKERY.getName()) + " 7." + GeneratorTypes.BREWERY.getName());
    }
}
