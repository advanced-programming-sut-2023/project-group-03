package controller.interfaces;

import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface KingdomInterface {
    public int showPopularity(Player player);
    String showPopularityFactors(Player player);
    public String changeFoodRate(Matcher matcher, Player player);
    public String changeTaxRate(Matcher matcher, Player player);
    public String changeFearRate(Matcher matcher, Player player);
}
