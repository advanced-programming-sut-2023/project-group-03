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
    Player owner;
    TradeController(Player owner) {
        this.owner = owner;
    }

    public String requestTrade(Matcher matcher) {
        String requestInfo = (matcher.group("TradeInfo"));
        HashMap<String, String> infoMap = getOptions(InputOptions.TRADE_REQUEST.getKeys(), requestInfo);
        String error = infoMap.get("error");
        if (!infoMap.get("p").matches("\\d+")) {
            return Response.INVALID_PRICE.getOutput();
        }
        if (!infoMap.get("a").matches("\\d+")) {
            return  Response.INVALID_AMOUNT.getOutput();
        }
        if (Resources.getResourceByName(infoMap.get("t")) == null) {
            return Response.INVALID_RESOURCE.getOutput();
        }
        int price = Integer.parseInt(infoMap.get("p"));
        int amount = Integer.parseInt(infoMap.get("a"));
        Resources resource = Resources.getResourceByName(infoMap.get("t"));
        Request request = new Request(owner, resource, amount, price);
        request.sendToAll();
        return Response.SUCCESSFUL_TRADE_REQUEST.getOutput();
    }

    public String showTradeList() {
        String output = "";
        for (Request myRequest : owner.getMyRequests()) {
            output+=myRequest.
        }
        return "";
    }

    public String acceptTrade() {
        return "";
    }

    public String showTradeHistory() {
        return "";
    }
}
