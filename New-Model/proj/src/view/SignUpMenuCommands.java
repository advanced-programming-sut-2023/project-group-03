package view;

public enum SignUpMenuCommands {
    INPUT("(?<input>([^\"\\s]\\S*|\"[^\"]+\"))"),
    USER_FIELD("-(?<option>\\w) (?<optionInfo>([^\"\\s]\\S*|\"[^\"]+\"))?"),
    PASSWORD_CONFIRMATION("-p (?<password>([^\"\\s]\\S*|\"[^\"]+\")) (?<passwordConfirmation>([^\"\\s]\\S*|\"[^\"]+\"))"),
    NEW_USER("user create (?<userInfo>.+)"),
    NEW_USER_FORMAT_CHECK("(-[[\\w]&&[^p]] ([^\"\\s]\\S*|\"[^\"]+\")?\\s?)|(-p (([^\"\\s]\\S*|\"[^\"]+\")( ([^\"\\s]\\S*|\"[^\"]+\"))?)?\\s?)+"),
    ;

    private String regex;
    private SignUpMenuCommands(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
