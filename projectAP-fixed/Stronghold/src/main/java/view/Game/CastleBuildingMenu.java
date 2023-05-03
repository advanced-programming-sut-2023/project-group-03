package view.Game;

import view.Enums.GameMenuCommands;
import view.Menu;
import view.Transition;

import java.util.Scanner;

import static view.Enums.ConsoleColors.*;
import static view.Enums.GameMenuCommands.*;
public class CastleBuildingMenu extends Menu {
    private GameMenu gameMenu;
    public CastleBuildingMenu(Scanner scanner,GameMenu gameMenu) {
        super(scanner);
        this.gameMenu = gameMenu;
    }

    @Override
    public void run() throws Transition {
        String command = scanner.nextLine();
        if (command.matches("back")) {

        } else if (command.matches(GameMenuCommands.BUILD_STONE_GATES.toString())) {

        } else if (command.matches(GameMenuCommands.BUILD_TOWER.toString())) {

        } else if (command.matches(GameMenuCommands.BUILD_WALL.toString())) {

        } else if (command.matches(GameMenuCommands.BUILD_BARRACKS.toString())) {

        } else if (command.matches(BUILD_DRAW_BRIDGE.toString())) {

        }
    }

    public void Guide() {
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", ">>Castle Buildings<<"));
        System.out.println(formatPrinter(TEXT_BRIGHT_YELLOW, "", "possible commands:"));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "1." + BUILD_STONE_GATES.toString()+
                " 2."+BUILD_TOWER+" 3."+BUILD_WALL+" 4."+BUILD_BARRACKS+" 5."+BUILD_DRAW_BRIDGE));

    }
}
