package controller.interfaces;

import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface GameMarketInterface {
    String showPriceList(Player player);

    String buyMatcherHandler(Matcher matcher, Player player);

    String sellMatcherHandler(Matcher matcher, Player player);
}
