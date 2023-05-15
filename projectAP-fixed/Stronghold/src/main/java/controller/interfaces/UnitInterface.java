package controller.interfaces;

import Model.Buildings.Barracks;
import Model.GamePlay.Player;
import view.Game.GameMenu;

import java.util.regex.Matcher;

public interface UnitInterface {
    String addUnitMatcherHandler(Matcher matcher, Player player, Barracks barracks);

    String patrol(Matcher matcher, GameMenu gameMenu);

    String selectUnitMatcherHandler(Matcher matcher, Player player, GameMenu gameMenu);

    String setState(Matcher matcher, Player player, GameMenu gameMenu);

    String attackMatcherHandler(Matcher matcher, GameMenu gameMenu);

    String moveUnit(Matcher matcher, GameMenu gameMenu);
}
