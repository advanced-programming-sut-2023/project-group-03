package controller.gameControllers;

import Model.Units.Unit;

import java.util.regex.Matcher;

public class UnitController {
    GameController gameController;

    UnitController(GameController gameController) {
        this.gameController = gameController;
    }
    public String createUnit(Matcher matcher) {
        //check if you have the requirements
        return "";
    }
    public String selectUnit(Matcher matcher) {
        //check if there is any unit there
        //check if it is ours
        return "";
    }

    public void setUnitState(Matcher matcher) {

    }

    //attack
    public String attack(Matcher matcher, Unit attacker) {
        return "";
    }

    public String archerAttack(Matcher matcher, Unit archers) {
        //check if they are in range and then attack
        return "";
    }

    public boolean canPourOil(Unit unit, int x, int y) {
        //check if unit is an engineer
        //check if it is a proper building
        return false;
    }

    public void pourOil(Matcher matcher, Unit engineer) {

    }
}
