package controller.interfaces;

import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface KingdomInterface {
    int showPopularity(Player player);

    String showPopularityFactors(Player player);

    String changeFoodRate(Matcher matcher, Player player);

    String changeTaxRate(Matcher matcher, Player player);

    String changeFearRate(Matcher matcher, Player player);
}
