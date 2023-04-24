package controller.gameControllers;

import java.util.regex.Matcher;

public class BuildingController {
    GameController gameController;

    BuildingController(GameController gameController) {
        this.gameController = gameController;
    }

    public String selectBuilding(Matcher matcher) {
        //return a proper response if there is no building
        //select if there is no problem
        return "";
    }

    public String repair(Matcher matcher) {
        //check if you have the elements and there is no enemy around
        return "";
    }
}
