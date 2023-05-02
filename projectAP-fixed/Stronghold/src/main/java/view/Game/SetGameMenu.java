package view.Game;

import Model.GamePlay.Game;
import Model.GamePlay.Government;
import Model.GamePlay.Player;
import Model.User;
import Model.UserDatabase;
import view.Enums.ConsoleColors;
import view.Menu;
import view.Transition;

import java.util.Scanner;

import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class SetGameMenu extends Menu {
    User user = null;
    Game game = null;
    public SetGameMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() throws Transition {
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
            game.setMap(UserDatabase.getMapByName(command));
            return "map set successfull";
        }
    }

    private String setPlayerNumI(int i) {
        colorPrint(ConsoleColors.TEXT_YELLOW, "set player number " + i + ":");
        String command = scanner.nextLine();
        if (UserDatabase.getUserByName(command) == null) {
            return ConsoleColors.formatPrinter(TEXT_RED, "", "there is no player with this name");
        } else {
            Player buff = new Player(UserDatabase.getUserByName(command), new Government());
            game.getMap().setPlayerI(i, buff);
            game.addPlayer(buff);
            return "player set successfull";
        }
    }



}
