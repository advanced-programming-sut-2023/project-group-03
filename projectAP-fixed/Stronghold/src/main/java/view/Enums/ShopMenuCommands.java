package view.Enums;

public enum ShopMenuCommands {

    SHOW_PRICE_LIST(""),
    BUY(""),
    SELL("")
    ;


    private String regex;

    private ShopMenuCommands(String regex){
        this.regex = regex;
    }

    @Override

    public String toString(){
        return this.regex;
    }
}
