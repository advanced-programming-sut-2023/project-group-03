package view.Game;

import Model.Buildings.Enums.GeneratorTypes;
import Model.GamePlay.Player;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;
import static view.Enums.ConsoleColors.TEXT_YELLOW;

public class WeaponBuidingMenu extends Menu {
    GameMenu gameMenu;
    public WeaponBuidingMenu(Scanner scanner, GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {
        String command = scanner.nextLine();
        String output = "";
        Player player = gameMenu.getGame().getCurrentPlayer();
        GameController gameController = new GameController(this.gameMenu.getGame().getMap());
        if (command.matches("back")) {
            throw new Transition(gameMenu);
        }
        if (command.matches(GameMenuCommands.BUILD_GENERATOR.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_GENERATOR.toString());
            output = gameController.buildGeneratorMatcherHandler(matcher, player);
            System.out.println(output);
        } else {
            System.out.println(formatPrinter(TEXT_RED, "", "invalid command"));
        }
    }
    public void guide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>weopon Buildings<<"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "possible format of command: build ???"));
        System.out.println(formatPrinter(TEXT_GREEN, "", "1." + GeneratorTypes.BOW_MAKER.getName() +
                " 2. " + GeneratorTypes.SWORD_MAKER.getName() + " 3." + GeneratorTypes.PIKE_MAKER.getName() +
                " 4." + GeneratorTypes.SPEAR_MAKER.getName() + " 5." + GeneratorTypes.TANNER.getName() +
                " 6."+GeneratorTypes.ARMOURER.getName()));
    }
}
