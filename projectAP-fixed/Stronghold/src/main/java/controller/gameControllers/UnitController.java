package controller.gameControllers;

import Model.Field.GameMap;
import Model.Units.Unit;
import controller.interfaces.UnitInterface;

import java.util.regex.Matcher;

public class UnitController extends GeneralGameController implements UnitInterface {
    GameController gameController;
    GameMap gameMap;

    UnitController(GameController gameController) {
        super(gameController.getGameMap());
        this.gameController = gameController;
    }

}
