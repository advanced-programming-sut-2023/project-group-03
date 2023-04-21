package view.Game;

import Model.Field.GameMap;
import Model.User;
import view.Menu;
import view.Transition;

import java.util.Scanner;

public class MapMenu extends Menu {
    User user;

    public MapMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() throws Transition {

    }
}
