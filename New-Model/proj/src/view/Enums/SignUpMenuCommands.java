package view.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands {
    BACK("back to starting menu"),
    UNDO("undo"),
    OSKOL("I am Oskol"),
    WISE("I am not Oskol"),
    CREATE_USER("(?=.*-u\\s+(?<username>\\S+))(?=.*-p\\s+(?<password>\\S+)\\s+(?<confirm>\\S+))" +
            "(?=.*-email\\s+(?<email>\\S+))" + "(?=.*-n\\s+(?<nickname>\\S+))?(?=.*-s\\s+(?<slogan>.+))?"),
    SECURITY_QUESTION("question pick (?=.*-q\\s+(?<questionNumber>\\S+))(?=.*-a\\s+(?<answer>\\S+))" +
            "(?=.*-c (?<confirm>\\w+))"),


    INPUT("(?<input>([^\"\\s]\\S*|\"[^\"]+\"))"),
    PASSWORD_CONFIRMATION("-p (?<password>([^\"\\s]\\S*|\"[^\"]+\")) (?<passwordConfirmation>([^\"\\s]\\S*|\"[^\"]+\"))"),
    NEW_USER("user create (?<userInfo>.+)"),
    NEW_USER_FORMAT_CHECK("(-[[\\w]&&[^p]] ([^\"\\s]\\S*|\"[^\"]+\")?\\s?)|(-p (([^\"\\s]\\S*|\"[^\"]+\")" +
            "( ([^\"\\s]\\S*|\"[^\"]+\"))?)?\\s?)+$"),
    PICK_QUESTION("^pick question (?<questionInfo>.+)"),
    ;
    private String regex;
    private Pattern pattern;
    private SignUpMenuCommands(String regex){
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    public String getRegex() {
        return this.regex;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public static Matcher getMatcher(String command) {
        Matcher matcher = null;
        if (command.matches(OSKOL.regex)) {
            matcher = OSKOL.pattern.matcher(command);
        } else if (command.matches(WISE.regex)) {
            matcher = WISE.pattern.matcher(command);
        }
        return matcher;
    }
}
