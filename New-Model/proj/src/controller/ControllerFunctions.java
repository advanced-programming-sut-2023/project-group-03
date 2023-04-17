package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerFunctions {

    public static Matcher getMatcher(String command, String regex){
        Matcher matcher = (Pattern.compile(regex)).matcher(command);
        if (matcher.matches()) return (Pattern.compile(regex)).matcher(command);
        return null;
    }
}
