package controller.interfaces;

import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface BuildingInterface {
    public String buildTowerMatcherHandler(Matcher matcher, Player player);
    public String buildWallMatcherHandler(Matcher matcher, Player player);
    public String buildBarracksMatcherHandler(Matcher matcher, Player player);
    //inventory
    public String buildInventoryMatcherHandler(Matcher matcher, Player player);
}
