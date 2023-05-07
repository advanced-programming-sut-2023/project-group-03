package controller.gameControllers;

import Model.Buildings.Enums.Resources;
import Model.GamePlay.Player;
import controller.Controller;
import controller.interfaces.GameMarketInterface;
import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

import java.util.HashMap;
import java.util.regex.Matcher;

public class MarketController extends Controller implements GameMarketInterface{
    public String showPriceList(Player player) {
        String output = "";
        for (Resources resource : Resources.values()) {
            output = output.concat("name: " + resource.getName() +
                    ", in your inventory: " + player.getInventory().get(resource) +
                    ", price for buy: " + resource.getGold() +
                    ", price for sell " + resource.getSellPrice() +
                    "\n---------------------\n");
        }
        return output;
    }

    @Override
    public String buyMatcherHandler(Matcher matcher, Player player) {
        String buyInfo = matcher.group("marketInfo");
        HashMap<String, String> infoMap = getOptions(BUY_SELL.getKeys(), buyInfo);
        if (infoMap.get("error") != null) return infoMap.get("error");

        Resources resource = Resources.getResourceByName(infoMap.get("i"));
        if (resource == null) return INVALID_RESOURCE_TYPE.getOutput();
        if (!infoMap.get("a").matches("\\d+")) return INVALID_RESOURCE_AMOUNT.getOutput();

        return buyFromMarket(resource, Integer.parseInt(infoMap.get("a")), player);
    }

    @Override
    public String sellMatcherHandler(Matcher matcher, Player player) {
        String buyInfo = matcher.group("marketInfo");
        HashMap<String, String> infoMap = getOptions(BUY_SELL.getKeys(), buyInfo);
        if (infoMap.get("error") != null) return infoMap.get("error");

        Resources resource = Resources.getResourceByName(infoMap.get("i"));
        if (resource == null) return INVALID_RESOURCE_TYPE.getOutput();
        if (!infoMap.get("a").matches("\\d+")) return INVALID_RESOURCE_AMOUNT.getOutput();

        return sellToMarket(resource, Integer.parseInt(infoMap.get("a")), player);
    }

    private String buyFromMarket(Resources resource, int amount, Player player) {
        int totalPrice = amount * resource.getGold();
        if (totalPrice > player.getGold()) return NOT_ENOUGH_GOLD_PURCHASE.getOutput();

        player.setGold(player.getGold() - totalPrice);
        player.getInventory().put(resource, player.getInventory().get(resource) + amount);

        return SUCCESSFUL_PURCHASE.getOutput();
    }

    private String sellToMarket(Resources resource, int amount, Player player) {
        if (player.getInventory().get(resource) < amount) return NOT_ENOUGH_RESOURCE_SELL.getOutput();

        player.setGold(player.getGold() + amount * resource.getSellPrice());
        player.getInventory().put(resource, player.getInventory().get(resource) - amount);

        return SUCCESSFUL_SELL.getOutput();
    }
}
