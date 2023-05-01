package controller.interfaces;

import Model.Buildings.Building;
import Model.GamePlay.Player;

import java.util.regex.Matcher;

public interface BuildingInterface {
    public String buildTowerMatcherHandler(Matcher matcher, Player player);
    public String buildWallMatcherHandler(Matcher matcher, Player player);
    public String buildBarracksMatcherHandler(Matcher matcher, Player player);
    //inventory
    public String buildInventoryMatcherHandler(Matcher matcher, Player player);
    //rest
    public String buildRestMatcherHandler(Matcher matcher, Player player);
    //generator
    public String buildGeneratorMatcherHandler(Matcher matcher, Player player);
    //gates
    public String buildStoneGates(Matcher matcher, Player player);
    public String buildDrawbridgeMatcherHandler(Matcher matcher, Player player);
    public String repair(Building building, Player player);
}
