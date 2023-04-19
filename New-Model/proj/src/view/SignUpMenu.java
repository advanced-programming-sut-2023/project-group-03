package view;

import view.Enums.ConsoleColors;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu extends Menu {
    static Scanner scanner;

    public SignUpMenu(Scanner scanner){
        super(scanner);
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN +">>Signup menu<<"+ConsoleColors.TEXT_BLACK);
        System.out.println(ConsoleColors.TEXT_BRIGHT_YELLOW + "choose a command to continue:");
        System.out.println(ConsoleColors.TEXT_RESET + "1.Login Menu");
        System.out.println("2.Signup Menu");
    }

    @Override
    public void run() throws Transition {

    }


    private void createUser(Matcher matcher){}

    private void pickQuestion(Matcher matcher){}

    public static String getRandomPasswordConfirmation(String randomPassword) {
        System.out.println("Your random password is: " + randomPassword);
        System.out.println("Please re-enter the given password here: ");
        return scanner.nextLine();
    }

    public static String showRandomSlogan(String randomSlogan) {
        System.out.println("Your random slogan is: " + randomSlogan);
        System.out.println("If you want to keep this slogan please enter y and else enter n.");
        String confirmation = scanner.nextLine();
        while (!confirmation.equals("y") && !confirmation.equals("n")) {
            System.out.println("Invalid input...");
            System.out.println("If you want to keep this slogan please enter y and else enter n.");
            confirmation = scanner.nextLine();
        }
        return confirmation;
    }


}
