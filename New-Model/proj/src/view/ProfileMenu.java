package view;

import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu{
    User user;

    public ProfileMenu(Scanner scanner, User user) {
        super(scanner);
        this.user = user;
    }

    @Override
    public void run() throws Transition {

    }

    // change profile data

    private void changeProfileNonPassword(Matcher matcher){
        // changes everything but password
    }

    private void changeProfilePassword(Matcher matcher){
        // changes password
    }

    // show profile data
    private void displayProfile(Matcher matcher){
        // shows different profile data based on matcher
    }
    
    //start game
    private void startGame(Matcher matcher){};
}
