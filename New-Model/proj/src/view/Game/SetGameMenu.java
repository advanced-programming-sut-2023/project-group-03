package view.Game;

import Model.User;
import view.Menu;

import java.util.Scanner;

public class SetGameMenu extends Menu {
    User user;

    public SetGameMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() {
        super.run();
    }
}
