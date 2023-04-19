package view.Game;

import Model.Feild.GameMap;
import view.Menu;
import view.Transition;

import java.util.Scanner;

public class MapMenu extends Menu {
    GameMap map;

    public MapMenu(Scanner scanner, GameMap map) {
        super(scanner);
        this.map = map;
    }

    @Override
    public void run() throws Transition {

    }
}
