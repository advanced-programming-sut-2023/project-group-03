package controller.gameControllers;

import Model.Buildings.Barracks;
import static view.Enums.GameMenuCommands.*;
import static controller.ControllerFunctions.*;
import static controller.Enums.Response.*;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Enums.TroopTypes;
import Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UnitControllerTest {
    Matcher matcher;
    GameMap gameMap = new GameMap(200);
    UnitController unitController = new UnitController(gameMap);
    Player player = new Player(new User(null, null, null, null, null),null);

    @Test
    void checkInvalidUnitType() {
        assertEquals(INVALID_COMMAND.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit khar", CREATE_UNIT.toString()), null, null));
        assertEquals(INVALID_COMMAND.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 1 -y 2 dard", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkCoordinates() {
        assertEquals(INVALID_X_MAP.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 201 -y 2 -c 1", CREATE_UNIT.toString()),
                        null, null));
        assertEquals(INVALID_X_MAP.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x -1000 -y 2 -c 1", CREATE_UNIT.toString()),
                        null, null));
        assertEquals(INVALID_Y_MAP.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 23 -y 201 -c 1", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkAmoutn() {
        assertEquals(INVALID_UNIT_AMOUNT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 23 -y 25 -c damn", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkType() {
        assertEquals(INVALID_UNIT_TYPE.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 23 -y 2 -c 1", CREATE_UNIT.toString()),
                        null, null));
        //todo
    }
}