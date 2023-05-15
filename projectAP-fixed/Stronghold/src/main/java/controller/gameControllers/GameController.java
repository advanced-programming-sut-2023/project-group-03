package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Buildings.Enums.Resources;
import Model.Buildings.Generators;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Game;
import Model.GamePlay.Player;
import Model.Units.Unit;
import controller.Controller;
import controller.interfaces.*;
import view.Game.GameMenu;

import java.util.regex.Matcher;

public class GameController extends Controller implements GameMarketInterface , BuildingInterface,
        UnitInterface, KingdomInterface
{
    private BuildingController buildingController;
    private KingdomController kingdomController;
    private MarketController marketController;
    private MoveUnitController moveUnitController;
    private UnitController unitController;

    private GameMap gameMap;

    public GameController(GameMap gameMap) {
        this.buildingController = new BuildingController(gameMap);
        this.kingdomController = new KingdomController(this);
        this.marketController = new MarketController();
        this.moveUnitController = new MoveUnitController();
        this.unitController = new UnitController(gameMap);
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void nextTurn() {

    }

    public String moveMap(Matcher matcher) {
        return buildingController.moveMap(matcher);
    }

    public String showMap(Matcher matcher) {
        return buildingController.showMap(matcher);
    }

    public String showDetail(Matcher matcher) {
        return buildingController.showDetails(matcher);
    }

    @Override
    public String showPriceList(Player player) {
        return marketController.showPriceList(player);
    }

    @Override
    public String buyMatcherHandler(Matcher matcher, Player player) {
        return marketController.buyMatcherHandler(matcher, player);
    }

    @Override
    public String sellMatcherHandler(Matcher matcher, Player player) {
        return marketController.sellMatcherHandler(matcher, player);
    }

    @Override
    public String selectBuilding(Matcher matcher, Player player, GameMenu gameMenu) {
        return buildingController.selectBuilding(matcher, player, gameMenu);
    }

    @Override
    public String buildTowerMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildTowerMatcherHandler(matcher, player);
    }

    @Override
    public String buildWallMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildWallMatcherHandler(matcher, player);
    }

    @Override
    public String buildBarracksMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildBarracksMatcherHandler(matcher, player);
    }

    @Override
    public String buildInventoryMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildInventoryMatcherHandler(matcher, player);
    }

    @Override
    public String buildRestMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildRestMatcherHandler(matcher, player);
    }

    @Override
    public String buildGeneratorMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildGeneratorMatcherHandler(matcher, player);
    }

    @Override
    public String buildTrapMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildTrapMatcherHandler(matcher, player);
    }

    @Override
    public String buildStoreMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildStoreMatcherHandler(matcher, player);
    }

    @Override
    public String buildDrawbridgeMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildDrawbridgeMatcherHandler(matcher, player);
    }

    @Override
    public String buildStoneGatesMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildStoneGatesMatcherHandler(matcher, player);
    }

    public String dropBuildingMatcherHandler(Matcher matcher, Player player) {
        return buildingController.dropBuildingMatcherHandler(matcher, player);
    }

    @Override
    public String repair(Building building, Player player) {
        return buildingController.repair(building, player);
    }

    @Override
    public String buildOxTetherMatcherHandler(Matcher matcher, Player player, Generators generators) {
        return buildOxTetherMatcherHandler(matcher, player, generators);
    }

    @Override
    public String buildStairMatcherHandler(Matcher matcher, Player player) {
        return buildStairMatcherHandler(matcher, player);
    }

    @Override
    public String addUnitMatcherHandler(Matcher matcher, Player player, Barracks barracks) {
        return unitController.addUnitMatcherHandler(matcher, player, barracks);
    }

    @Override
    public String patrol(Matcher matcher, GameMenu gameMenu) {
        return unitController.patrol(matcher, gameMenu);
    }

    @Override
    public String selectUnitMatcherHandler(Matcher matcher, Player player, GameMenu gameMenu) {
        return unitController.selectUnitMatcherHandler(matcher, player, gameMenu);
    }

    @Override
    public String setState(Matcher matcher, Player player, GameMenu gameMenu) {
        return unitController.setState(matcher, player, gameMenu);
    }

    @Override
    public String attackMatcherHandler(Matcher matcher, GameMenu gameMenu) {
        return unitController.attackMatcherHandler(matcher, gameMenu);
    }

    @Override
    public String moveUnit(Matcher matcher, GameMenu gameMenu) {
        return unitController.moveUnit(matcher, gameMenu);
    }

    @Override
    public int showPopularity(Player player) {
        return kingdomController.showPopularity(player);
    }

    @Override
    public String showPopularityFactors(Player player) {
        return kingdomController.showPopularityFactors(player);
    }

    @Override
    public String changeFoodRate(Matcher matcher, Player player) {
        return kingdomController.changeFoodRate(matcher, player);
    }

    @Override
    public String changeTaxRate(Matcher matcher, Player player) {
        return kingdomController.changeTaxRate(matcher, player);
    }

    @Override
    public String changeFearRate(Matcher matcher, Player player) {
        return kingdomController.changeFearRate(matcher, player);
    }

    public String attackToBuildingMatcherHandler(Matcher matcher, GameMenu gameMenu) {
        return unitController.attackToBuildingMatcherHandler(matcher,gameMenu);
    }

    public void disbandUnit(GameMenu gameMenu) {
        unitController.disbandUnit(gameMenu);
    }

    public String showInventories(Player player) {
        String output = "";
        output += "gold: " + player.getGold() + "\n";
        output += "popularity: " + player.getPopularity() + "\n";
        output += "busy population: " + player.getCurrentPopulation() + "\n";
        output += "max population:" + player.getMaxPopulation() + "\n";
        for (Resources resource : Resources.values()) {
            output += resource.getName() + " " + player.getInventory().get(resource) + "\n";
        }
        return output;
    }
}
