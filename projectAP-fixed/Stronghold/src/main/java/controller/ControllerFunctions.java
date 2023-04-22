package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerFunctions {

    public static Matcher getMatcher(String command, String regex){
        Matcher matcher = (Pattern.compile(regex)).matcher(command);
        if (matcher.find()) return matcher;
        return null;
    }

    public static String unwrapQuotation(String input) {
        if (input == null) return null;
        if (!input.contains("\"")) return input;
        return input.substring(1, input.length() - 1);
    }
}
