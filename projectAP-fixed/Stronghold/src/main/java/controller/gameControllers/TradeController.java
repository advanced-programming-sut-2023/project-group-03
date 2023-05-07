package controller.gameControllers;

import Model.Buildings.Enums.Resources;
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
    public TradeController(Player owner) {
        this.owner = owner;
    }

    public String requestTrade(Matcher matcher) {
        String requestInfo = (matcher.group("TradeInfo"));
        HashMap<String, String> infoMap = getOptions(InputOptions.TRADE_REQUEST.getKeys(), requestInfo);
        String error = infoMap.get("error");
        if (error != null) {
            return error;
        }
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
        request.setMassage(infoMap.get("m"));
        request.sendToAll();
        return Response.SUCCESSFUL_TRADE_REQUEST.getOutput();
    }

    public String showTradeList() {
        String output = "My requests:\n";
        for (Request myRequest : owner.getMyRequests()) {
            output += myRequest.toString() + "\n";
        }
        output += "income requests:\n";
        for (Request incomeRequest : owner.getIncomeRequests()) {
            output += incomeRequest.toString();
        }
        return output;
    }

    public String acceptTrade(Matcher matcher) {
        String acceptInfo = matcher.group("AcceptInfo");
        HashMap<String, String> infoMap = getOptions(InputOptions.TRADE_ACCEPT.getKeys(), acceptInfo);
        if (infoMap.get("error") != null) {
            return infoMap.get("error");
        }
        if (!infoMap.get("i").matches("\\d+")) {
            return Response.INVALID_ID.getOutput();
        }
        int id = Integer.parseInt(infoMap.get("i"));
        if (owner.getRequestById(id) == null) {
            return Response.INVALID_REQUEST_ID.getOutput();
        }
        Request request = owner.getRequestById(id);
        if (owner.getResourceAmount(request.getResource()) < request.getAmount()) {
            return "not enough inventory";
        }
        if (request.getOwner().getGold() < request.getPrice()) {
            return "not enough gold";
        }
        owner.getRequestById(id).setAcceptedBy(owner);
        return Response.SUCCESSFUL_TRADE_ACCEPT.getOutput();
    }

    public String showTradeHistory() {
        return "";
    }
}
