package controller.gameControllers;

import Model.Buildings.Enums.Resources;
import Model.GamePlay.Game;
import Model.GamePlay.Player;
import Model.GamePlay.Request;
import controller.Controller;
import controller.Enums.InputOptions;
import controller.Enums.Response;

import java.util.HashMap;
import java.util.regex.Matcher;
public class TradeController extends Controller {
    GameController gameController;

    TradeController(GameController gameController) {
        this.gameController = gameController;
    }

    public String requestTrade(Matcher matcher, Player player) {
        String requestInfo = (matcher.group("TradeInfo"));
        HashMap<String, String> infoMap = getOptions(InputOptions.TRADE_REQUEST.getKeys(), requestInfo);
        String error = infoMap.get("error");
        if (!infoMap.get("p").matches("\\d+")) {
            return Response.INVALID_PRICE.getOutput();
        }
        if (Resources.getResourceByName(infoMap.get("t")) == null) {
            return Response.INVALID_RESOURCE.getOutput();
        }
        Request request = new Request(player);

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
