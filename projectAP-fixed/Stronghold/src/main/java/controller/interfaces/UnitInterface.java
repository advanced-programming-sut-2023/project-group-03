package controller.interfaces;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Unit;
import view.Game.GameMenu;

import java.util.regex.Matcher;

public interface UnitInterface {
//    String addTroopMatcherHandler(Matcher matcher, Player player, Tile tile);
//    String addThrowerMatcherHandler(Matcher matcher, Player player);
//    String addEngineer(Player player, String amountString, Tile tile);
    String addUnitMatcherHandler(Matcher matcher, Player player, Barracks barracks);
    String patrol(Matcher matcher, GameMenu gameMenu);
    String selectUnitMatcherHandler(Matcher matcher, Player player, GameMenu gameMenu);
    String setState(Matcher matcher, Player player, GameMenu gameMenu);
    String attackMatcherHandler(Matcher matcher, GameMenu gameMenu);
    String moveUnit(Matcher matcher, GameMenu gameMenu);
}
