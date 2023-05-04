package controller.interfaces;

import Model.Buildings.Building;
import Model.GamePlay.Player;
import view.Game.GameMenu;

import java.util.regex.Matcher;

public interface BuildingInterface {
    String selectBuilding(Matcher matcher, Player player, GameMenu gameMenu);
    String buildTowerMatcherHandler(Matcher matcher, Player player);
    String buildWallMatcherHandler(Matcher matcher, Player player);
    String buildBarracksMatcherHandler(Matcher matcher, Player player);
    //inventory
    String buildInventoryMatcherHandler(Matcher matcher, Player player);
    //rest
    String buildRestMatcherHandler(Matcher matcher, Player player);
    //generator
    String buildGeneratorMatcherHandler(Matcher matcher, Player player);
    //gates
    String buildStoneGatesMatcherHandler(Matcher matcher, Player player);
    String buildDrawbridgeMatcherHandler(Matcher matcher, Player player);
    //traps
    String buildTrapMatcherHandler(Matcher matcher, Player player);
    //store
    String buildStoreMatcherHandler(Matcher matcher, Player player);
    //repair
    String repair(Building building, Player player);
}
