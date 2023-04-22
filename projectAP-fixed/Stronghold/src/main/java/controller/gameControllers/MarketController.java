package controller.gameControllers;

import java.util.regex.Matcher;

public class MarketController {
    GameMenuController gameMenuController;
    public MarketController(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }
    public String showPriceList() {
        return "";
    }

    public String checkBuyRequirements() {
        //check if market has that resource and if you have the money
        return "";
    }

    public String buyFromMarket(Matcher matcher) {
        return "";
    }

    public String checkSellRequirements() {
        //check if you have the resource and if you have that amount
        return "";
    }

    public String sellToMarket(Matcher matcher) {

        return "";
    }
}
