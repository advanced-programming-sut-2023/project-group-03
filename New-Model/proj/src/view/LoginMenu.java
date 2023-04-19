package view;


import Model.User;
import view.Enums.LoginMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private void login(Matcher matcher){}

    private void loginStay(Matcher matcher){
        // check if json file is null or not
    }

    private void forgotPassword(Matcher matcher){}

    private void logout(Matcher matcher){
        // clears json
    }

}
