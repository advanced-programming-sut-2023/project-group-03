package view.Game;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Buildings.Defending.CastleBuilding;
import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Keep;
import Model.Buildings.Store;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.Units.Unit;
import Model.User;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import controller.gameControllers.GameMenuController;
import controller.gameControllers.MarketController;
import view.Enums.ConsoleColors;
import view.Enums.GameMenuCommands;
import view.Enums.MapMenuCommands;
import view.Enums.ShopMenuCommands;
import view.Menu;
import view.Transition;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;

public class GameMenu extends Menu {
    private Game game;
    User user;

    private Drawable selected = null;
    private ArrayList<Unit> selectedUnits = new ArrayList<>();

    CastleBuildingMenu castleBuildingMenu;
    FarmBuidingMenu farmBuidingMenu;
    FoodProcessingMenu foodProcessingMenu;
    IndustryBuildingMenu industryBuildingMenu;
    TownBuidingMenu townBuidingMenu;
    WeaponBuidingMenu weaponBuidingMenu;
    BaracksMenu baracksMenu;
    KeepMenu keepMenu;

    public GameMenu(Scanner scanner, Game game) {
        super(scanner);
        this.game = game;
        game.setCurrentPlayer(game.getPlayers().get(0));
        Tile.setGameMap(game.getMap());
        castleBuildingMenu = new CastleBuildingMenu(scanner, this);
        farmBuidingMenu = new FarmBuidingMenu(scanner, this);
        foodProcessingMenu = new FoodProcessingMenu(scanner, this);
        industryBuildingMenu = new IndustryBuildingMenu(scanner, this);
        townBuidingMenu = new TownBuidingMenu(scanner, this);
        weaponBuidingMenu = new WeaponBuidingMenu(scanner, this);
        baracksMenu = new BaracksMenu(scanner, this);
        keepMenu = new KeepMenu(scanner, this);
    }

    @Override
    public void run() throws Transition {
        showGuide();
        String command = scanner.nextLine();

        GameController gameController = new GameController(game.getMap());
        if (command.matches("select menu")) {
            SelectMenuGuide();
            Menu next = handleMenu();
            if (next == null) {
                throw new Transition(this);
            } else {
                throw new Transition(next);
            }
        } else if (command.matches(GameMenuCommands.SHOW_INVENTORY.toString())) {
            String output = gameController.showInventories(game.getCurrentPlayer());
            System.out.println(output);
        } else if (command.matches(GameMenuCommands.SELECT_BUILDING.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_BUILDING.toString());
            String output = gameController.selectBuilding(matcher, game.getCurrentPlayer(), this);
            System.out.println(output);
        } else if (command.matches(GameMenuCommands.SELECT_UNIT.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_UNIT.toString());
            String output = gameController.selectUnitMatcherHandler(matcher, game.getCurrentPlayer(), this);
            System.out.println(output);
        } else if (command.matches(MapMenuCommands.MOVE_ALI.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.MOVE_ALI.getRegex());
            String output = gameController.moveMap(matcher);
            System.out.println(output);
        } else if (command.matches(MapMenuCommands.SHOW_MAP.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_MAP.getRegex());
            String output = gameController.moveMap(matcher);
            System.out.println(output);
        } else if (command.matches(MapMenuCommands.SHOW_DETAILS_ALI.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_DETAILS_ALI.getRegex());
            String output = gameController.showDetail(matcher);
            System.out.println(output);
        } else if (command.matches("next turn")) {
            if (game.nextTurn()) {
                throw new Transition(new MapMenu(scanner, user));
            }
        } else if (selected instanceof Building) {
            if (command.matches(GameMenuCommands.REPAIR.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.REPAIR.toString());
                String output = gameController.repair(((Building) selected), game.getCurrentPlayer());
                System.out.println(output);
            }
        } else if (selected instanceof Barracks) {
            if (command.matches(GameMenuCommands.CREATE_UNIT.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.CREATE_UNIT.toString());
                System.out.println(gameController.addUnitMatcherHandler(matcher, game.getCurrentPlayer(), ((Barracks) selected)));
            }
        } else if (selected instanceof Store) {
            if (command.matches(ShopMenuCommands.BUY.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, ShopMenuCommands.BUY.toString());
                String output = gameController.buyMatcherHandler(matcher, game.getCurrentPlayer());
                System.out.println(output);
            } else if (command.matches(ShopMenuCommands.SELL.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, ShopMenuCommands.SELL.toString());
                String output = gameController.sellMatcherHandler(matcher, game.getCurrentPlayer());
                System.out.println(output);
            } else if (command.matches(ShopMenuCommands.SHOW_PRICE_LIST.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, ShopMenuCommands.SHOW_PRICE_LIST.toString());
                String output = gameController.showPriceList(game.getCurrentPlayer());
                System.out.println(output);
            }
        } else if (selectedUnits.size() != 0) {
            if (command.matches(GameMenuCommands.DISBAND_UNIT.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.DISBAND_UNIT.toString());
                gameController.disbandUnit(this);
                System.out.println("successfull dabsh");
            } else if (command.matches(GameMenuCommands.ATTACK_PLACE.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.ATTACK_PLACE.toString());
                String output = gameController.attackMatcherHandler(matcher, this);
                System.out.println(output);
            } else if (command.matches(GameMenuCommands.ATTACK_BUILDING.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.ATTACK_BUILDING.toString());
                String output = gameController.attackToBuildingMatcherHandler(matcher, this);
                System.out.println(output);
            } else if (command.matches(GameMenuCommands.MOVE_UNIT.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.MOVE_UNIT.toString());
                String output = gameController.moveUnit(matcher, this);
                System.out.println(output);
            }
        }
        throw new Transition(this);
    }

    // map
    public void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        String selectedName = "nothing selected";
        if (selected != null) {
            selectedName = selected.getClass().getSimpleName();
        }
        game.getMap().showMap(3);
        System.out.println(formatPrinter(TEXT_GREEN, "", "turn:" + game.getTurn() +
                " currentPlayer:"+ game.getCurrentPlayer().getUser().getNickname() + "  selected:" + selectedName));
        System.out.println(formatPrinter(TEXT_YELLOW, "", "common possible command formats:\n" +
                "1.select menu 2." + GameMenuCommands.SELECT_BUILDING.toString() +
                " 3." + GameMenuCommands.SELECT_UNIT.toString() + " 4." + MapMenuCommands.MOVE_ALI.getRegex() + " 5." +
                "next turn"));
        if (selected instanceof Barracks) {
            Barracks barracks = ((Barracks) selected);
            System.out.println(formatPrinter(TEXT_BG_CYAN, "", "Type: " + barracks.getType() +
                    " HP:" + barracks.getHP() + "\ncommand to create unit: " + GameMenuCommands.CREATE_UNIT.toString()));
        }
        if (selected instanceof Store) {
            Store store = ((Store) selected);
            System.out.println(formatPrinter(TEXT_BG_CYAN, "", "Type: " + "Market" +
                    " HP:" + store.getHP() + "\npossible format of command: 1." + ShopMenuCommands.SELL.toString() +
                    " 2." + ShopMenuCommands.BUY.toString() + " 3." + ShopMenuCommands.SHOW_PRICE_LIST.toString()));
        }
        if (selected instanceof CastleBuilding) {
            CastleBuilding castleBuilding = ((CastleBuilding) selected);
            System.out.println(formatPrinter(TEXT_BG_CYAN, "", "Type: " + castleBuilding.getClass().getSimpleName() +
                    " HP:" + castleBuilding.getHP() + "\ncommand to repair:" + GameMenuCommands.REPAIR.toString()));
        }
        if (selected instanceof Keep) {

        }
        if (selected instanceof Unit) {
            Unit unit = ((Unit) selected);
            System.out.println(formatPrinter(TEXT_BG_CYAN, "", "Type: " + unit.getClass().getSimpleName() +
                    " HP:" + unit.getHP() + "\npossible format of command: 1." + GameMenuCommands.ATTACK_BUILDING.toString() +
                    " 2." + GameMenuCommands.ATTACK_PLACE.toString() + " 3." + GameMenuCommands.MOVE_UNIT.toString() + " 4." +
                    GameMenuCommands.DISBAND_UNIT.toString()));
        }
    }

    public void SelectMenuGuide() {
        System.out.println(formatPrinter(TEXT_GREEN, "", "select a menu:" +
                " 1.Castle Buildings 2.Town Buildings 3.Farm Buildings 4.Food Processing 5.Industry 6.Weopon Buildings"
                + "\n" + "or select a building: 1.Market 2.Keep 3.Barracks 4.Engineer Guild"));
    }

    public Menu handleMenu() {
        Menu output = null;
        while (true) {
            String input = scanner.nextLine();
            if (input.matches("Castle Buildings")) {
                return castleBuildingMenu;
            } else if (input.matches("Town Buildings")) {
                return townBuidingMenu;
            } else if (input.matches("Farm Buildings")) {
                return farmBuidingMenu;
            } else if (input.matches("Food Processing")) {
                return foodProcessingMenu;
            } else if (input.matches("Industry")) {
                return industryBuildingMenu;
            } else if (input.matches("Weopon Buildings")) {
                return weaponBuidingMenu;
            } else if (input.matches("Market")) {

            } else if (input.matches("Keep menu")) {
                return keepMenu;
            } else if (input.matches("Barracks")) {

            } else if (input.matches("Engineer Guild")) {

            } else if (input.matches("back")) {
                return output;
            }
            if (output == null) {
                continue;
            } else {
                return output;
            }
        }

    }

    public Game getGame() {
        return game;
    }

    public Drawable getSelected() {
        return selected;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSelected(Drawable selected) {
        this.selected = selected;
    }

    public ArrayList<Unit> getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(ArrayList<Unit> selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void showMap(Matcher matcher){}

    private void moveMap(Matcher matcher){}

    private void showDetails(Matcher matcher){}

    // kingdom

    private void changeFoodRate(Matcher matcher){}

    private void changeTaxRate(Matcher matcher){}

    private void changeFearRate(Matcher matcher){}

    // show rates are handled in the controller as they have no errors

    // buildings


    private void selectBuilding(Matcher matcher){}

    private void createUnit(Matcher matcher){}

    private void repair(){}

    // units and workers

    private void selectUnit(Matcher matcher){}

    // the following functions must give an error if no units are selected

    private void moveUnit(Matcher matcher){}

    private void patrolUnit(Matcher matcher){}

    private void setUnitState(Matcher matcher){
        // sets unit state (standing|defensive|offensive)
    }

    private void attack(Matcher matcher){}

    private void pourOil(Matcher matcher){}

    private void buildSiegeStructures(Matcher matcher){
        // only if selected unit is an engineer
    }

    private void disbandUnit(Matcher matcher){}

    // change environment

    private void setTexture(Matcher matcher){}

    private void setTextureRectangle(Matcher matcher){}

    private void clearField(Matcher matcher){
        // error if Field is already empty
    }

    private void dropRock(Matcher matcher){
        // error if field is not empty
    }

    private void dropTree(Matcher matcher){}

    private void dropBuilding(Matcher matcher){}

    private void dropUnit(Matcher matcher){}

    // trading

    private void doTrade(Matcher matcher){}

    private void tradeList(Matcher matcher){}

    private void tradeAccept(Matcher matcher){}

    private void tradeHistory(Matcher matcher){}

}
