package view.Enums;

public enum ShopMenuCommands {

    SHOW_PRICE_LIST("^show price list$"),
    BUY("^buy (?<marketInfo>.+)"),
    SELL("^sell (?<marketInfo>.+)"),
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
