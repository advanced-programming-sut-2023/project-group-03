package view;


import Model.User;
import Model.UserDatabase;
import controller.Enums.Response;
import controller.LoginMenuController;
import view.Enums.ConsoleColors;
import view.Enums.LoginMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static controller.Enums.Response.*;
import static view.Enums.ConsoleColors.TEXT_RED;
import static view.Enums.ConsoleColors.colorPrint;

public class LoginMenu extends Menu{

    private static ArrayList<String> securityQuestions;

    public LoginMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void run() throws Transition {
        showGuide();
        String command=scanner.nextLine();
        if (command.matches(LoginMenuCommands.BACK.getRegex())) {
            throw new Transition(new StartingMenu(scanner));
        }
        if (command.matches(LoginMenuCommands.LOGIN.getRegex())) {
            LoginMenuController controller = new LoginMenuController();
            Matcher matcher = LoginMenuCommands.LOGIN.getPattern().matcher(command);
            matcher.find();
            String output = controller.login(matcher, scanner);
            System.out.println(output);
            if (output.matches(SUCCESSFUL_LOGIN.getOutput())) {
                Matcher matcher1 = LoginMenuCommands.USER_LOGIN.getMatcher(command);
                matcher1.find();
                String username = matcher1.group("username");
                User user = UserDatabase.getUserByName(username);
                throw new Transition(new MainMenu(scanner, user));
            }
            throw new Transition(this);
        }
        if (command.matches(LoginMenuCommands.FORGOT_PASSWORD.getRegex())) {
            LoginMenuController controller = new LoginMenuController();
            Matcher matcher = LoginMenuCommands.FORGOT_PASSWORD.getPattern().matcher(command);
            matcher.find();
            System.out.println(controller.forgotPassword(matcher, scanner));
            throw new Transition(this);
        }
        colorPrint(TEXT_RED, "invalid command");
        throw new Transition(this);
    }

    public static String getPasswordAgain(Scanner scanner, int seconds){
        System.out.println(WRONG_PASSWORD_LOGIN.getOutput() + 3 * seconds + " seconds");
        try {
            Thread.sleep((seconds * 3 * 1000));
        } catch (Exception e) {
            System.out.println("An exception happened for Thread.sleep.");
        }
        System.out.println(ENTER_PASSWORD.getOutput());
        return scanner.nextLine();
    }

    private void loginStay(Matcher matcher){
        // check if json file is null or not
    }

    private void forgotPassword(Matcher matcher){

    }

    public static String getAnswer(Scanner scanner, String question) {
        System.out.println(GET_ANSWER_LOGIN);
        System.out.println(question);
        return scanner.nextLine();
    }

    public static String getNewPassword(Scanner scanner) {
        System.out.println(ENTER_NEW_PASSWORD.getOutput());
        String newPassword = scanner.nextLine();
//        System.out.println(SUCCESSFULL_CHANGE_PASSWORD.getOutput());
        return newPassword;
    }

    public static void showOutput(String output) {
        System.out.println(output);
    }

    private void logout(Matcher matcher){
        // clears json
    }

    private void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        System.out.println(ConsoleColors.TEXT_BRIGHT_GREEN + ">>Login menu<<" + ConsoleColors.TEXT_RESET);
        colorPrint(ConsoleColors.TEXT_YELLOW, "back: backing to starting menu");
        colorPrint(ConsoleColors.TEXT_YELLOW, "possible command:");
        System.out.println("1.user login -u <username> -p <password>");
        System.out.println("2.forgot password -u <username>");
    }
}
