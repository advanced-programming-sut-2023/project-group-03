package controller.Enums;

public enum UserBasedMenuCommands {
    INVALID_USERNAME_FORMAT("[^a-zA-Z0-9_]"),
    EMAIL_FORMAT("^([a-zA-Z0-9_\\.])+@([a-zA-Z0-9_\\.])+\\.([a-zA-Z0-9_\\.])+$"),
    ;
    String regex;

    UserBasedMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
