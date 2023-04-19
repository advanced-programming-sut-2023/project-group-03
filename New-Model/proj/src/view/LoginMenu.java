package view;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Menu{

    private static ArrayList<String> securityQuestions;

    public LoginMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void run() {
        super.run();
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
