package view.Game;

import view.Menu;
import view.Transition;

import java.util.Scanner;

public class BaracksMenu extends Menu {
    GameMenu gameMenu;

    public BaracksMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {

    }
}
