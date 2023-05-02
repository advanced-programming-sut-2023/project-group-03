package view.Game;

import view.Menu;
import view.Transition;

import java.util.Scanner;

public class CastleBuildingMenu extends Menu {
    private GameMenu gameMenu;
    public CastleBuildingMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {

    }
}
