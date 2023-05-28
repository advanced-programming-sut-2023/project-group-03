package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerFunctions {

    public static Matcher getMatcher(String command, String regex) {
        Matcher matcher = (Pattern.compile(regex)).matcher(command);
        if (matcher.find()) return matcher;
        return null;
    }

    public static String unwrapQuotation(String input) {
        if (input == null) return null;
        if (!input.contains("\"")) return input;
        return input.substring(1, input.length() - 1);
    }

    public String adapter(String[] keys, String[] inputs) {
        String output = "";
        for (int i = 0; i < keys.length; i++) {
            output += "-" + keys[i] + " " + inputs[i] + " ";
        }
        return output.substring(0, output.length() - 2);
    }

    public String registerAdapter(String[] keys, String[] inputs, String passwordConfirmation) {
        String output = "";
        for (int i = 0; i < keys.length; i++) {
            if (!keys[i].equals("p")) output += "-" + keys[i] + " " + inputs[i] + " ";
            else output += "-" + keys[i] + " " + inputs[i] + " " + passwordConfirmation + " ";
        }
        return output.substring(0, output.length() - 2);
    }
}
