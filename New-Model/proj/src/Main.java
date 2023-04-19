import view.StartingMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StartingMenu startingMenu=new StartingMenu(scanner);
        startingMenu.run();
    }
}