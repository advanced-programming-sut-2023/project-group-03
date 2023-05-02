package view.Game;

import view.Menu;
import view.Transition;

import java.util.Scanner;

public class KeepMenu extends Menu {
    GameMenu gameMenu;
    public KeepMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {

    }
}
