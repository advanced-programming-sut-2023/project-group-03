package controller.gameControllers;

import controller.Controller;

import java.util.regex.Matcher;
public class TradeController extends Controller {
    GameController gameController;

    TradeController(GameController gameController) {
        this.gameController = gameController;
    }
    public String requestTrade(Matcher matcher) {
        return "";
    }

    public String showTradeList() {
        return "";
    }

    public String acceptTrade() {
        return "";
    }

    public String showTradeHistory() {
        return "";
    }
}
