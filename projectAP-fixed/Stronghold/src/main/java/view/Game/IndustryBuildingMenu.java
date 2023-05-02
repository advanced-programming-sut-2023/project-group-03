package view.Game;

import view.Menu;
import view.Transition;

import java.util.Scanner;

public class IndustryBuildingMenu extends Menu {
    GameMenu gameMenu;
    public IndustryBuildingMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {

    }
}
