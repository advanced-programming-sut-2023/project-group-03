package view.Enums;

public enum SignUpMenuCommands {
    INPUT("(?<input>([^\"\\s]\\S*|\"[^\"]+\"))"),
    USER_FIELD("-(?<option>[upesn]) (?<optionInfo>([^\"\\s]\\S*|\"[^\"]+\"))?"),
    PASSWORD_CONFIRMATION("-p (?<password>([^\"\\s]\\S*|\"[^\"]+\")) (?<passwordConfirmation>([^\"\\s]\\S*|\"[^\"]+\"))"),
    NEW_USER("user create (?<userInfo>.+)")
    ;

    private String regex;
    private SignUpMenuCommands(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
