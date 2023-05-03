package view.Game;

import Model.GamePlay.Game;
import view.Menu;
import view.Transition;

import java.util.Scanner;

public class TownBuidingMenu extends Menu {
    GameMenu gameMenu;

    public TownBuidingMenu(Scanner scanner, GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {

    }

    public void guid() {

    }
}
