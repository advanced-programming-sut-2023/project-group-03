package controller.Enums;

public enum ControllerCommands {
    OPTION_FIELD("-(?<option>\\w+) (?<optionInfo>([^\"\\s]\\S*|\"[^\"]+\"))?"),
    MULTI_OPTION_FIELD_FORMAT("^(-\\w+ ([^\"\\s]\\S*|\"[^\"]+\")?\\s?)+$"),
    LOGIN_OPTION_FIELD("(-[\\w+] ([^\"\\s]\\S*|\"[^\"]+\")?\\s?)*" +
            "(--stay-logged-in\\s?)?(-[\\w+] ([^\"\\s]\\S*|\"[^\"]+\")?\\s?)*?$"),
    ;

    private final String regex;

    ControllerCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
