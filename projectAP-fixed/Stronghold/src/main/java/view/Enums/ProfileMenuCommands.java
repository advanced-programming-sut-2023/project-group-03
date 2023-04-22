package view.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    BACK("back to main menu"),
    CHANGE_USERNAME("profile change -u (?<username>\\S+)"),
    CHANGE_NICKNAME("profile change -n (?<nickname>\\S+)"),
    CHANGE_PASSWORD("^profile change password (?<passwordInfo>.+)$"),
    CHANGE_EMAIL("profile change -e (?<email>\\S+)"),
    CHANGE_SLOGAN("profile change slogan -s (?<slogan>.+)"),
    REMOVE_SLOGAN("profile remove slogan"),
    DISPLAY_HIGHSCORE("profile display highscore"),
    DISPLAY_RANK("profile display rank"),
    DISPLAY_SLOGAN("profile display slogan"),
    DISPLAY_ALL("profile display"),
    START_GAME("")
    ;
    private String regex;
    private Pattern pattern;
    private ProfileMenuCommands(String regex){
        this.regex = regex;
        pattern = Pattern.compile(regex);
    }

    public String getRegex() {
        return this.regex;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Matcher getMatcher(String command) {
        return pattern.matcher(command);
    }
}
