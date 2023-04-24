package controller.interfaces;

import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface GameMarketInterface {
    public String showPriceList(Player player);
    public String buyMatcherHandler(Matcher matcher, Player player);
    public String sellMatcherHandler(Matcher matcher, Player player);
}
