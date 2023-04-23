package view;


import Model.User;
import view.Enums.LoginMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static controller.Enums.Response.*;

public class LoginMenu extends Menu{

    private static ArrayList<String> securityQuestions;

    public LoginMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void run() throws Transition {
        String command=scanner.nextLine();
        if (Pattern.matches(LoginMenuCommands.LOGIN.getRegex(), command)) {
            User user = new User("", "", "", "", "");
            throw new Transition(new MainMenu(scanner, user));
        } else if (Pattern.matches(LoginMenuCommands.LOGOUT.getRegex(), command)) {

        } else if (Pattern.matches(LoginMenuCommands.PICK_SECURITY.getRegex(), command)) {

        } else if (Pattern.matches(LoginMenuCommands.FORGOT_PASSWORD.getRegex(), command)) {

        }
    }

    public static String getPasswordAgain(Scanner scanner, int seconds){
        System.out.println(INVALID_PASSWORD_LOGIN.getOutput() + seconds + " seconds");
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

}
