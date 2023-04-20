package view;

import controller.Enums.Response;
import controller.RegisterMenuController;
import view.Enums.ConsoleColors;
import view.Enums.SignUpMenuCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;
import static view.Enums.ConsoleColors.colorPrint;

public class SignUpMenu extends Menu {
    public SignUpMenu(Scanner scanner){
        super(scanner);
        showGuide();
    }

    @Override
    public void run() throws Transition {
        String command = scanner.nextLine();
        if (command.matches(SignUpMenuCommands.BACK.getRegex())) {
            throw new Transition(new StartingMenu(scanner));
        }
        if (command.matches(SignUpMenuCommands.OSKOL.getRegex())) {
            System.out.println(inlineInput());
            if (inlineInput().equals(Response.SUCCESSFUL_REGISTER.getOutput())) {
                securityQuestionGuide();
                do {
                    System.out.println(securityQuestionInline());
                } while (!securityQuestionInline().equals(Response.SUCCESSFUL_REGISTER));
            }
            throw new Transition(this);
        } else if (command.matches(SignUpMenuCommands.WISE.getRegex())) {
            nonInlineInput();
        } else {
            System.out.println("wrong input");
            throw new Transition(this);
        }
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

    public static HashMap<String, String> askSecurityQuestion(ArrayList<String> questions) {
        HashMap<String, String> pickedQuestion = new HashMap<>() {{
            put("q", null);
            put("a", null);
        }};

        System.out.println("Pick one of the questions below and enter the answer and confirmation of it.");

        int questionIndex = 1;
        for (String question : questions) {
            System.out.println(questionIndex + "." + question);
        }




        return pickedQuestion;
    }

    private void showGuide() {
        colorPrint(TEXT_BLACK,TEXT_BG_WHITE,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>Signup menu<<" + ConsoleColors.TEXT_RESET);
        colorPrint(ConsoleColors.TEXT_YELLOW, "back: backing to starting menu");
        colorPrint(ConsoleColors.TEXT_YELLOW, "to create user choose an option:");
        System.out.println("1.I am Oskol and want an inline command");
        System.out.println("2.I am not Oskol");
    }

    private String inlineInput() {
        colorPrint(TEXT_YELLOW, "salam Oskol! type the command:");
        String command = scanner.nextLine();
        String output = null;
        if (command.matches(SignUpMenuCommands.CREATE_USER.getRegex())) {
            Matcher matcher = SignUpMenuCommands.CREATE_USER.getPattern().matcher(command);
            RegisterMenuController register = new RegisterMenuController();
            output = register.registerNewUser(matcher, scanner);
        } else {
            output = "try again oskol";
        }
        return output;
    }

    private void nonInlineInput() {
        String username,nickname,slogan;
        String password,passwordConfirmation,email;
        colorPrint(TEXT_YELLOW, "Ahsant");
        colorPrint(TEXT_YELLOW, "username:");
        username = scanner.nextLine();
        colorPrint(TEXT_YELLOW, "password(random: random nickname):");
        password=scanner.nextLine();
        colorPrint(TEXT_YELLOW, "password confirmation:");
        passwordConfirmation = scanner.nextLine();
        colorPrint(TEXT_YELLOW, "email:");
        email = scanner.nextLine();
        colorPrint(TEXT_YELLOW, "nickname(random: random nickname):");
        nickname = scanner.nextLine();
        colorPrint(TEXT_YELLOW, "slogan(random: random slogan):");
        slogan = scanner.nextLine();
    }

    private void securityQuestionGuide() {
        colorPrint(TEXT_YELLOW,"pick your security question:");
        colorPrint(TEXT_YELLOW,"1.whats your aunt name? 2.whats your aunt last name? 3.whats your aunt phone number?");
    }
    private String securityQuestionInline() {
        String command = scanner.nextLine();
        String output = null;
        if (command.matches(SignUpMenuCommands.SECURITY_QUESTION.getRegex())) {
            Matcher matcher = SignUpMenuCommands.SECURITY_QUESTION.getPattern().matcher(command);
            output = RegisterMenuController.askSecurityQuestion2(matcher);
        } else {
            output = "try again oskol";
        }

        return output;
    }

}
