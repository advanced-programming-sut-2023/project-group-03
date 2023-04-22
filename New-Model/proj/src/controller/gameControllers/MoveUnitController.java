package controller.gameControllers;

import model.Units.Unit;

import java.util.regex.Matcher;

public class MoveUnitController {
    GameMenuController gameMenuController;

    public MoveUnitController(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }

    public boolean checkIfImpossibleDestination(int x, int y) {
        return false;
    }

    public void findProperPath(int x, int y, int dir, Unit unit) {

    }

    public void moveUnit(Matcher matcher) {

    }

    public String patrolUnit(Matcher matcher) {
        //move between x1,y1 and x2,y2
        //check if the positions are valid
        return "";
    }

    public String disbandUnit(Matcher matcher) {
        return "";
    }
}
