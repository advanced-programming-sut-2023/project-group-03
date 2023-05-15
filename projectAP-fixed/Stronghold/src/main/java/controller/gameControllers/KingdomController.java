package controller.gameControllers;

import Model.GamePlay.Player;
import controller.interfaces.KingdomInterface;

import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.CHANGE_RATE;
import static controller.Enums.Response.*;

public class KingdomController extends GeneralGameController implements KingdomInterface {
    GameController gameController;

    KingdomController(GameController gameController) {
        super(gameController.getGameMap());
        this.gameController = gameController;
    }

    public int showPopularity(Player player) {
        return player.getPopularity();
    }

    public String showPopularityFactors(Player player) {
        String output = "";
        output += "fear rate: " + player.getFearFactor() + "\n";
        output += "food rate: " + player.getFoodRate() + "\n";
        output += "tax rate: " + player.getTaxRate();
        return output;
    }

    private HashMap<String, String> getOptionsChangeRate(Matcher matcher) {
        String rateInfo = matcher.group("rateInfo");
        HashMap<String, String> infoMap = getOptions(CHANGE_RATE.getKeys(), rateInfo);
        String error = infoMap.get("error");
        if (error != null) return infoMap;

        if (!infoMap.get("r").matches("\\d+")) {
            infoMap.put("error", INVALID_INTEGER_KINGDOM.getOutput());
        }
        return infoMap;
    }

    public String changeFoodRate(Matcher matcher, Player player) {
        HashMap<String, String> infoMap = getOptionsChangeRate(matcher);
        String error = infoMap.get("error");
        if (error != null) return error;
        int rate = Integer.parseInt(infoMap.get("r"));

        if (!(rate >= -2 && rate <= 2)) return INVALID_FOOD_RATE.getOutput();
        player.setFoodRate(rate);
        return SUCCESSFUL_CHANGE_FOOD_RATE.getOutput();
    }

    public String changeTaxRate(Matcher matcher, Player player) {
        HashMap<String, String> infoMap = getOptionsChangeRate(matcher);
        String error = infoMap.get("error");
        if (error != null) return error;
        int rate = Integer.parseInt(infoMap.get("r"));

        if (!(rate >= -3 && rate <= 8)) return INVALID_TAX_RATE.getOutput();
        player.setTaxRate(rate);
        return SUCCESSFUL_CHANGE_TAX_RATE.getOutput();
    }

    public String changeFearRate(Matcher matcher, Player player) {
        HashMap<String, String> infoMap = getOptionsChangeRate(matcher);
        String error = infoMap.get("error");
        if (error != null) return error;
        int rate = Integer.parseInt(infoMap.get("r"));

        if (!(rate >= -5 && rate <= 5)) return INVALID_FEAR_RATE.getOutput();
        player.setFearFactor(rate);
        return SUCCESSFUL_CHANGE_FEAR_RATE.getOutput();
    }
}
