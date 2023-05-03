package controller.gameControllers;

import Model.Buildings.Building;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Player;
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
        this.buildingController = new BuildingController(this);
        this.kingdomController = new KingdomController(this);
        this.marketController = new MarketController(this);
        this.moveUnitController = new MoveUnitController(this);
        this.unitController = new UnitController(this);
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
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
    public String buildStoneGatesMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildStoneGatesMatcherHandler(matcher, player);
    }

    @Override
    public String buildDrawbridgeMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildDrawbridgeMatcherHandler(matcher, player);
    }

    @Override
    public String buildTrapMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildTrapMatcherHandler(matcher, player);
    }

    @Override
    public String repair(Building building, Player player) {
        return buildingController.repair(building, player);
    }

    @Override
    public String addTroopMatcherHandler(Matcher matcher, Player player, Tile tile) {
        return unitController.addTroopMatcherHandler(matcher, player, tile);
    }

    @Override
    public String addThrowerMatcherHandler(Matcher matcher, Player player) {
        return unitController.addThrowerMatcherHandler(matcher, player);
    }

    @Override
    public String addEngineer(Player player, String amountString, Tile tile) {
        return unitController.addEngineer(player, amountString, tile);
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
