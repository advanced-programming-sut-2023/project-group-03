package view.Game;

import Model.GamePlay.Game;
import Model.GamePlay.Player;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import controller.gameControllers.TradeController;
import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;

public class KeepMenu extends Menu {
    GameMenu gameMenu;
    private Game game;
    public KeepMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
        game = gameMenu.getGame();
    }

    @Override
    public void run() throws Transition {
        guide();
        String command = scanner.nextLine();
        String output = "";
        Player player = gameMenu.getGame().getCurrentPlayer();
        GameController gameController = new GameController(this.gameMenu.getGame().getMap());
        TradeController tradeController = new TradeController(player);

        if (command.matches("back")) {
            throw new Transition(gameMenu);
        }
        else if (command.matches(GameMenuCommands.FEAR_RATE.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.FEAR_RATE.toString());
            output = gameController.changeFearRate(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.FOOD_RATE.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.FOOD_RATE.toString());
            output = gameController.changeFoodRate(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.TAX_RATE.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.TAX_RATE.toString());
            output = gameController.changeTaxRate(matcher, player);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.TRADE_REQUEST.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.TRADE_REQUEST.toString());
            output = tradeController.requestTrade(matcher);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.TRADE_ACCEPT.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.TRADE_ACCEPT.toString());
            output = tradeController.acceptTrade(matcher);
            System.out.println(output);
        }
        else {
            System.out.println(formatPrinter(TEXT_RED, "", "invalid command"));
        }
        throw new Transition(this);
    }

    public void guide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>Keep<<"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "fearRate:" +
                game.getCurrentPlayer().getFearFactor() + " taxRate:" + game.getCurrentPlayer().getTaxRate() +
                " foodRate: " + game.getCurrentPlayer().getFoodRate() +
                " popularity: " + game.getCurrentPlayer().getPopularity() +
                " gold: " + game.getCurrentPlayer().getGold()));
        System.out.println(formatPrinter(TEXT_YELLOW,"","possible commands:"));
        System.out.println(formatPrinter(TEXT_GREEN, "", "1." + GameMenuCommands.FEAR_RATE.toString() +
                " 2." + GameMenuCommands.TAX_RATE.toString() + " 3." + GameMenuCommands.FOOD_RATE.toString()
                + " 4." + GameMenuCommands.SHOW_FOOD_LIST.toString() +
                "\n5." + GameMenuCommands.TRADE_REQUEST.toString() + " 6." + GameMenuCommands.TRADE_ACCEPT.toString()));
    }
}
