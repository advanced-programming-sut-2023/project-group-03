package view.Game;

import Model.Buildings.*;
import Model.Buildings.Defending.CastleBuilding;
import Model.Buildings.Enums.BarracksType;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.GamePlay.Player;
import Model.Units.Unit;
import Model.User;
import controller.ControllerFunctions;
import controller.Enums.Response;
import controller.gameControllers.GameController;
import controller.gameControllers.GameMenuController;
import controller.gameControllers.GeneralGameController;
import controller.gameControllers.MarketController;
import view.Enums.ConsoleColors;
import view.Enums.GameMenuCommands;
import view.Enums.MapMenuCommands;
import view.Enums.ShopMenuCommands;
import view.Menu;
import view.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

import static view.Enums.ConsoleColors.*;

public class GameMenu extends Menu {
    private Game game;
    User user;

    private Drawable selected = null;
    private ArrayList<Unit> selectedUnits = new ArrayList<>();
    private HashMap<Player, ArrayList<Tile>> selectedTiles = new HashMap<>();

    CastleBuildingMenu castleBuildingMenu;
    FarmBuidingMenu farmBuidingMenu;
    FoodProcessingMenu foodProcessingMenu;
    IndustryBuildingMenu industryBuildingMenu;
    TownBuidingMenu townBuidingMenu;
    WeaponBuidingMenu weaponBuidingMenu;
    BaracksMenu baracksMenu;
    KeepMenu keepMenu;
    int halfSide = 3;

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
        GameController gameController = new GameController(game.getMap());
        game.getMap().showMap(halfSide);
        setStockPile(gameController);
        for (Player player : game.getPlayers()) selectedTiles.put(player, new ArrayList<>());
    }

    @Override
    public void run() throws Transition {
        showGuide();
        GameController gameController = new GameController(game.getMap());
        String command = scanner.nextLine();
        if (command.matches("select menu")) {
            SelectMenuGuide();
            Menu next = handleMenu();
            if (next == null) {
                throw new Transition(this);
            } else {
                throw new Transition(next);
            }
        }
        else if(command.matches("change size of map"))
        {
            int size = changeSizeOfMap();
            GeneralGameController.setGameWidth(size);
        }
        else if (command.matches(GameMenuCommands.SHOW_INVENTORY.toString())) {
            String output = gameController.showInventories(game.getCurrentPlayer());
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.SELECT_BUILDING.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_BUILDING.toString());
            String output = gameController.selectBuilding(matcher, game.getCurrentPlayer(), this);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.SELECT_UNIT.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_UNIT.toString());
            String output = gameController.selectUnitMatcherHandler(matcher, game.getCurrentPlayer(), this);
            System.out.println(output);
        }
        else if (command.matches(MapMenuCommands.MOVE_ALI.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.MOVE_ALI.getRegex());
            String output = gameController.moveMap(matcher);
            System.out.println(output);
        }
        else if (command.matches(MapMenuCommands.SHOW_MAP.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_MAP.getRegex());
            String output = gameController.moveMap(matcher);
            System.out.println(output);
        } else if (command.matches(MapMenuCommands.SHOW_DETAILS_ALI.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_DETAILS_ALI.getRegex());
            String output = gameController.showDetail(matcher);
            System.out.println(output);
        } else if (command.matches("next turn")) {
            selectedUnits = new ArrayList<>();
            selected = null;
            if (game.nextTurn()) {
                throw new Transition(new MapMenu(scanner, user));
            }
        } else if (selected instanceof Building) {
            if (command.matches(GameMenuCommands.REPAIR.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.REPAIR.toString());
                String output = gameController.repair(((Building) selected), game.getCurrentPlayer());
                System.out.println(output);
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
                } else if (selected instanceof Generators) {
                    if (command.matches(GameMenuCommands.BUILD_OX_TETHER.toString())) {
                        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_OX_TETHER.toString());
                        System.out.println(gameController.buildOxTetherMatcherHandler(matcher, game.getCurrentPlayer(), ((Generators) selected)));
                    }
                }
            }
        } else if (selectedUnits.size() != 0) {
            if (command.matches(GameMenuCommands.DISBAND_UNIT.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.DISBAND_UNIT.toString());
                gameController.disbandUnit(this);
                System.out.println("successfull dabsh");
            } else if (command.matches(GameMenuCommands.SET.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SET.toString());
                System.out.println(gameController.setState(matcher, game.getCurrentPlayer(), this));
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

    public void executeGameMenuOrder(String command) {
        GameController gameController = new GameController(game.getMap());
        if(command.matches("change size of map"))
        {
            int size = changeSizeOfMap();
            GeneralGameController.setGameWidth(size);
        }
        else if (command.matches(GameMenuCommands.SHOW_INVENTORY.toString())) {
            String output = gameController.showInventories(game.getCurrentPlayer());
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.SELECT_BUILDING.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_BUILDING.toString());
            String output = gameController.selectBuilding(matcher, game.getCurrentPlayer(), this);
            System.out.println(output);
        }
        else if (command.matches(GameMenuCommands.SELECT_UNIT.toString())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_UNIT.toString());
            String output = gameController.selectUnitMatcherHandler(matcher, game.getCurrentPlayer(), this);
            System.out.println(output);
        }
        else if (command.matches(MapMenuCommands.MOVE_ALI.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.MOVE_ALI.getRegex());
            String output = gameController.moveMap(matcher);
            System.out.println(output);
        }
        else if (command.matches(MapMenuCommands.SHOW_MAP.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_MAP.getRegex());
            String output = gameController.moveMap(matcher);
            System.out.println(output);
        } else if (command.matches(MapMenuCommands.SHOW_DETAILS_ALI.getRegex())) {
            Matcher matcher = ControllerFunctions.getMatcher(command, MapMenuCommands.SHOW_DETAILS_ALI.getRegex());
            String output = gameController.showDetail(matcher);
            System.out.println(output);
        } else if (command.matches("next turn")) {
            //todo
        } else if (selected instanceof Building) {
            if (command.matches(GameMenuCommands.REPAIR.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.REPAIR.toString());
                String output = gameController.repair(((Building) selected), game.getCurrentPlayer());
                System.out.println(output);
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
                } else if (selected instanceof Generators) {
                    if (command.matches(GameMenuCommands.BUILD_OX_TETHER.toString())) {
                        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_OX_TETHER.toString());
                        System.out.println(gameController.buildOxTetherMatcherHandler(matcher, game.getCurrentPlayer(), ((Generators) selected)));
                    }
                }
            }
        } else if (selectedUnits.size() != 0) {
            if (command.matches(GameMenuCommands.DISBAND_UNIT.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.DISBAND_UNIT.toString());
                gameController.disbandUnit(this);
                System.out.println("successfull dabsh");
            } else if (command.matches(GameMenuCommands.SET.toString())) {
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SET.toString());
                System.out.println(gameController.setState(matcher, game.getCurrentPlayer(), this));
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
    }

    public void showGuide() {
        colorPrint(TEXT_RED,"================================================");
        String selectedName = "NOTHING";
        String selectedUnitName = "NOTHING";
        if (selected != null) {
            selectedName = selected.getClass().getSimpleName();
        }
        if (selectedUnits.size() > 0) {
            selectedUnitName = selectedUnits.get(0).getTypeOfUnit();
        }
        game.getMap().showMap(halfSide);
        System.out.println(formatPrinter(TEXT_GREEN, "", "turn:" + game.getTurn() +
                "   currentPlayer:" + game.getCurrentPlayer().getUser().getNickname() + "  Selected:" + selectedName +
                "  SelectedUnits:" + selectedUnitName + "  Number:" + selectedUnits.size()));
        showPlayersLeft();
        colorPrint(TEXT_BRIGHT_YELLOW, "common possible command formats:");
        System.out.println(formatPrinter(TEXT_YELLOW, "", "1.select menu\n2."
                + GameMenuCommands.SELECT_BUILDING.toString() +
                "\n3." + GameMenuCommands.SELECT_UNIT.toString() + "\n4." + MapMenuCommands.MOVE_ALI.getRegex() + "\n5." +
                "next turn\n6.change size of map\n7." + GameMenuCommands.SHOW_INVENTORY));
        if (selectedUnits.size() > 0) {
            System.out.println(formatPrinter(TEXT_PURPLE, "", "Type: " + selectedUnitName +
                    " number:" + selectedUnits.size() + "\npossible format of command: 1." + GameMenuCommands.ATTACK_BUILDING.toString() +
                    " 2." + GameMenuCommands.ATTACK_PLACE.toString() + " 3." + GameMenuCommands.MOVE_UNIT.toString() + " 4." +
                    GameMenuCommands.DISBAND_UNIT.toString()));
        } else selectedUnits = new ArrayList<>();
        if (selected instanceof Barracks) {
            Barracks barracks = ((Barracks) selected);
            System.out.println(formatPrinter(TEXT_PURPLE, "", "Type: " + barracks.getType() +
                    " HP:" + barracks.getHP() + "\ncommand to create unit: " + GameMenuCommands.CREATE_UNIT.toString()));
        }
        else if (selected instanceof Store) {
            Store store = ((Store) selected);
            System.out.println(formatPrinter(TEXT_PURPLE, "", "Type: " + "Market" +
                    " HP:" + store.getHP() + "\npossible format of command: 1." + ShopMenuCommands.SELL.toString() +
                    " 2." + ShopMenuCommands.BUY.toString() + " 3." + ShopMenuCommands.SHOW_PRICE_LIST.toString()));
        }
        else if (selected instanceof CastleBuilding) {
            CastleBuilding castleBuilding = ((CastleBuilding) selected);
            System.out.println(formatPrinter(TEXT_PURPLE, "", "Type: " + castleBuilding.getClass().getSimpleName() +
                    " HP:" + castleBuilding.getHP() + "\ncommand to repair:" + GameMenuCommands.REPAIR.toString()));
        }
        else if (selected instanceof Building) {
            System.out.println(formatPrinter(TEXT_PURPLE, "",
                    " HP:" + selected.getHP() + "\ncommand to create unit: " + GameMenuCommands.CREATE_UNIT.toString()));
        }

    }

    public void SelectMenuGuide() {
        System.out.println(formatPrinter(TEXT_GREEN, "", "select a menu:" +
                " 1.Castle Buildings 2.Town Buildings 3.Farm Buildings 4.Keep menu 5.Industry 6.Weopon Buildings"
                + "\n" + "or select a building: 1.Market 2.Barracks "));
    }

    public Menu handleMenu() {
        Menu output = null;
        while (true) {
            String input = scanner.nextLine();
            if (input.matches("Castle Buildings")) {
                return castleBuildingMenu;
            }
            else if (input.matches("Town Buildings")) {
                return townBuidingMenu;
            }
            else if (input.matches("Farm Buildings")) {
                return farmBuidingMenu;
            }
            else if (input.matches("Food Processing")) {
                return foodProcessingMenu;
            }
            else if (input.matches("Industry")) {
                return industryBuildingMenu;
            }
            else if (input.matches("Weopon Buildings")) {
                return weaponBuidingMenu;
            }
            else if (input.matches("Market")) {
                if (game.getCurrentPlayer().getKeep().getStore() == null) {
                    colorPrint(TEXT_RED, "you have not build it yet");
                } else {
                    selected = game.getCurrentPlayer().getKeep().getStore();
                    selectedUnits = new ArrayList<>();
                    System.out.println("successfuly selected");
                }
            }
            else if (input.matches("Keep menu")) {
                return keepMenu;
            }
            else if (input.matches("Barracks")) {
                HandleBarrackType();
                return output;
            }
            else if (input.matches("back")) {
                return output;
            }
            else{
                ConsoleColors.colorPrint(TEXT_RED, "invalid command");
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

    public HashMap<Player, ArrayList<Tile>> getSelectedTiles() {
        return selectedTiles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStockPile(GameController gameController) {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            ConsoleColors.colorPrint(TEXT_RED, "", "====================================");
            System.out.println("player number " + (i + 1) + " set your stockPile");
            ConsoleColors.colorPrint(TEXT_BRIGHT_YELLOW,"format: build Inventory -x () -y () -t stockpile");
            while (true) {
                String command = scanner.nextLine();
                if (command.matches("build Inventory .* -t stockpile")) {
                    Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.BUILD_INVENTORY.toString());
                    String output = gameController.buildInventoryMatcherHandler(matcher, game.getPlayers().get(i));
                    System.out.println(output);
                    if (output.matches(Response.SUCCESSFUL_DROP_BUILDING.getOutput())) {
                        break;
                    }
                } else {
                    ConsoleColors.colorPrint(TEXT_RED, "", "invalid command");
                }
            }
        }
    }

    public int changeSizeOfMap() {
        colorPrint(TEXT_BRIGHT_YELLOW, "set size: number from 1 to 5");
        while (true) {
            String command = scanner.nextLine();
            if (!command.matches("[12345]")) {
                colorPrint(TEXT_RED, "invalid number");
            } else {
                halfSide = Integer.parseInt(command);
                return halfSide;
            }
        }
    }

    public boolean HandleBarrackType() {
        colorPrint(TEXT_BRIGHT_YELLOW, "select type of Barrack: 1." + BarracksType.BARRACK.getName()
                + " 2." + BarracksType.MERCENARY_POST.getName() + " 3." + BarracksType.ENGINEER_GUILD.getName());

        while (true) {
            String command = scanner.nextLine();
            BarracksType barrack = null;
            if (command.matches(BarracksType.MERCENARY_POST.getName())) {
                barrack = BarracksType.MERCENARY_POST;
            }
            else if (command.matches(BarracksType.BARRACK.getName())) {
                barrack = BarracksType.BARRACK;
            }
            else if (command.matches(BarracksType.ENGINEER_GUILD.getName())) {
                barrack = BarracksType.ENGINEER_GUILD;
            } else if (command.matches("back")) {
                return false;
            } else {
                colorPrint(TEXT_RED, "invalid command");
                continue;
            }
            if (game.getCurrentPlayer().getKeep().getBarracks().get(barrack) == null) {
                colorPrint(TEXT_RED, "you have not build it yet");
                return false;
            } else {
                selected = game.getCurrentPlayer().getKeep().getBarracks().get(barrack);
                selectedUnits = new ArrayList<>();
                System.out.println("successfuly selected");
                return true;
            }
        }
    }

    private void showPlayersLeft() {
        String list = ConsoleColors.formatPrinter(TEXT_PURPLE, TEXT_BG_BLACK, "players left:  ");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            list += formatPrinter(game.getPlayers().get(i).getFlagColor().getColor(), TEXT_BG_BLACK,
                    (i + 1) + "." + game.getPlayers().get(i).getUser().getNickname()+"  ");
        }
        System.out.println(list);
    }

    public int getHalfSide() {
        return halfSide;
    }

    public void setHalfSide(int halfSide) {
        this.halfSide = halfSide;
    }
}
