package view;

import java.util.Scanner;

public abstract class Menu {
    Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void run() throws Transition;

    public void RunHandler() {
        try {
            run();
        } catch (Transition transition) {
            if (transition.getDestMenu().equals(null)) {
                return;
            } else {
                transition.getDestMenu().RunHandler();
            }
        }
    }
}
