package view;

public enum SignUpMenuCommands {

    NEW_USER("")
    ;

    String regex;
    private SignUpMenuCommands(String regex){
        this.regex = regex;
    }
}
