package controller.interfaces;

import Model.Field.Tile;
import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface UnitInterface {
    public String addTroopMatcherHandler(Matcher matcher, Player player, Tile tile);
    public String addEngineer(Player player, String amountString, Tile tile);
}
