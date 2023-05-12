package view.Game;

import Model.Buildings.Enums.Resources;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.GamePlay.Government;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Enums.TroopTypes;
import Model.User;
import Model.UserDatabase;
import view.Enums.ConsoleColors;
import view.Menu;
import view.Transition;

import java.util.ArrayList;
import java.util.Scanner;

import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class SetGameMenu extends Menu {
    User user = null;
    Game game;
    public SetGameMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() throws Transition {
        showGuide();
        Drawable.setDrawables(new ArrayList<>());
        String output = null;
        do {
            output = SetMap();
            System.out.println(output);
        } while (!output.equals("map set successfull"));
        for (int i = 0; i < game.getMap().getNumberOfPlayers(); i++) {
            do {
                output = setPlayerNumI(i);
                System.out.println(output);
            } while (!output.equals("player set successfull"));
        }
        Drawable.setDrawables(new ArrayList<>(this.game.getMap().getDrawables()));
        setResourses(500);
        GameMenu gameMenu = new GameMenu(scanner, game);
        throw new Transition(gameMenu);
    }

    private void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>Set Game<<" + " User: " + user.getUsername() + ConsoleColors.TEXT_RESET);
        colorPrint(ConsoleColors.TEXT_GREEN, "exit: backing to main menu");
        colorPrint(ConsoleColors.TEXT_GREEN, "start: start game");
    }
    private String SetMap() {
        colorPrint(ConsoleColors.TEXT_YELLOW, "choose map:");
        String command = scanner.nextLine();
        if (UserDatabase.getMapByName(command) == null) {
            return ConsoleColors.formatPrinter(TEXT_RED, "", "there is no map with this name");
        } else {
            game = new Game(UserDatabase.getMapByName(command), new ArrayList<>());
            //game.setMap(UserDatabase.getMapByName(command));
            return "map set successfull";
        }
    }

    private String setPlayerNumI(int i) {
        colorPrint(ConsoleColors.TEXT_YELLOW, "set player number " + (i + 1) + ":");
        String command = scanner.nextLine();
        if (UserDatabase.getUserByName(command) == null) {
            return ConsoleColors.formatPrinter(TEXT_RED, "", "there is no player with this name");
        } else {
            game.getMap().getPlayers()[i].setUser(UserDatabase.getUserByName(command));
            game.addPlayer(game.getMap().getPlayers()[i]);
            return "player set successfull";
        }
    }

    private void setResourses(int amount) {
        for (Player player : game.getPlayers()) {
            for (Resources resources : player.getInventory().keySet()) {
                player.getInventory().replace(resources, amount);
                player.setGold(1000);
                player.setPopularity(100);
                player.setCurrentPopulation(0);
                player.setMaxPopulation(64);
            }
            Troop King = new Troop(player, player.getKeep().getPosition(), TroopTypes.KING);
        }
    }



}
