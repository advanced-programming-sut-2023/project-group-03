package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Field.GameMap;
import Model.Field.Tile;
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
    public String attackMatcherHandler(Matcher matcher, Unit unit) {
        return unitController.attackMatcherHandler(matcher, unit);
    }

    @Override
    public int showPopularity(Player player) {
        return kingdomController.showPopularity(player);
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
}
