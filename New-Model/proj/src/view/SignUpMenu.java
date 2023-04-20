package view;

import view.Enums.ConsoleColors;
import view.Enums.SignUpMenuCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu extends Menu {
    private Scanner scanner;

    public SignUpMenu(Scanner scanner){
        super(scanner);
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>Signup menu<<" + ConsoleColors.TEXT_RESET);
    }

    @Override
    public void run() throws Transition {

    }


    private void createUser(Matcher matcher){}

    private void pickQuestion(Matcher matcher){}

    public static String getRandomPasswordConfirmation(String randomPassword, Scanner scanner) {
        System.out.println("Your random password is: " + randomPassword);
        System.out.println("Please re-enter the given password here: ");
        return scanner.nextLine();
    }

    public static String showRandomSlogan(String randomSlogan, Scanner scanner) {
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

    public static String askSecurityQuestion(ArrayList<String> questions, Scanner scanner) {
        System.out.println("Pick one of the questions below and enter the answer and confirmation of it.");
        for (int questionIndex = 0; questionIndex < questions.size(); questionIndex++) {
            System.out.println((questionIndex + 1) + ". " + questions.get(questionIndex));
        }

        Matcher checkFormatMatcher;
        String input;
        do {
            System.out.println("Please Enter: question pick -q <question's number> -a <answer> -c <confirmation>");
            input = scanner.nextLine();
            checkFormatMatcher = controller.ControllerFunctions.getMatcher(input, SignUpMenuCommands.PICK_QUESTION.getRegex());

        } while (checkFormatMatcher == null);


        return checkFormatMatcher.group("questionInfo");
    }

    public static void showOutput(String output) {
        System.out.println(output);
    }


}
