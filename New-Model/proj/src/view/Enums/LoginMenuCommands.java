package view.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    BACK("back to starting menu"),
    USER_LOGIN("user login(?=.*-u\\s+(?<username>\\S+))(?=.*-p\\s+(?<password>\\S+))(?<stay>\\s+--stay-logged-in)?"),
    PICK_SECURITY(""),
    LOGIN("^user login (?<loginInfo>.+)$"),
    FORGOT_PASSWORD("forgot my password -u (?<username>\\S+)"),
    LOGOUT("user logged out"),
    ;

    private String regex;
    private Pattern pattern;
    private LoginMenuCommands(String regex){
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    public String getRegex() {
        return regex;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Matcher getMatcher(String command) {
        return pattern.matcher(command);
    }
}
