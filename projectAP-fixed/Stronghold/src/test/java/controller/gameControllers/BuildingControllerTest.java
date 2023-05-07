package controller.gameControllers;


import static view.Enums.GameMenuCommands.*;
import static controller.ControllerFunctions.*;
import static controller.Enums.Response.*;

import Model.Buildings.Defending.Enums.WallTypes;
import Model.Buildings.Defending.Wall;
import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.GeneratorTypes;
import Model.Buildings.Enums.Resources;
import Model.Buildings.Keep;
import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Enums.TroopTypes;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingControllerTest {
    GameMap gameMap;
    Player player;
    BuildingController buildingController;

    @BeforeEach
    void setup() {
        gameMap = new GameMap(200);
        Tile.setGameMap(gameMap);
        User user = new User(null, "ali", "ali", "ali@.com", null);
        player = new Player(user, null);
        new Keep(player, gameMap.getMap()[100][100]);
        buildingController = new BuildingController(gameMap);
    }

    @Test
    void checkCoordinates() {
        assertEquals(INVALID_Y_MAP.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x 5777 -t dard -y thing", DROP_BUILDING.toString()), player
                ));

        assertEquals(INVALID_X_MAP.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x 201 -t dard -y 30", DROP_BUILDING.toString()), player
                ));
    }

    @Test
    void checkInvalidBuildingType() {
        assertEquals(INVALID_BUILDING_TYPE.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x 80 -t dard -y 80", DROP_BUILDING.toString()), player
                ));
        assertEquals(INVALID_BUILDING_TYPE.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x 80 -t \"weird building\" -y 80", DROP_BUILDING.toString()), player
                ));
    }

    @Test
    void checkResource() {
        int stoneCost = BarracksType.BARRACK.getStoneCost();
        player.getInventory().replace(Resources.STONE, stoneCost - 1);
        assertEquals(NOT_ENOUGH_STONE_BUILDING.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x 80 -t barrack -y 80", DROP_BUILDING.toString()), player
                ));

        int goldCost = BarracksType.BARRACK.getGold();
        player.getInventory().replace(Resources.STONE, stoneCost);
        player.setGold(goldCost - 1);
        assertEquals(NOT_ENOUGH_GOLD_BARRACKS.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x 80 -t barrack -y 80", DROP_BUILDING.toString()), player
                ));
    }

    @Test
    void checkIfNotFit() {
        int size = BarracksType.BARRACK.getSize();
        assertEquals(NOT_FIT.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x " + (200 - size / 2 + 1) + " -t barrack -y 100", DROP_BUILDING.toString()), player
                ), "building doesn't fit in the map");
        assertEquals(SUCCESSFUL_DROP_BUILDING.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x " + (200 - size / 2) + " -t barrack -y 150", DROP_BUILDING.toString()), player
                ), "successful drop building");
        for (int x = 199 - size / 2 - size / 2; x < 199; x++) {
            for (int y = 149 - size / 2; y < 149 + size / 2; y++) {
                assertTrue(gameMap.getMap()[x][y].getBuilding() != null, "error in set buildings" + x + " " + y);
            }
        }
    }

    @Test
    void checkBuildingExist() {
        int size = BarracksType.BARRACK.getSize();
        Tile targetTile = gameMap.getMap()[199][199];
        gameMap.getMap()[199][199].setBuilding(new Wall(player, targetTile, WallTypes.SMALL));
        assertEquals(BUILDING_EXIST.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x " + (200 - size / 2) + " -t barrack -y "+ (200 - size / 2), DROP_BUILDING.toString()), player
                ), "a building exist in one of the required tiles");
    }

    @Test
    void checkBadTexture() {
        int size = BarracksType.BARRACK.getSize();
        Tile targetTile = gameMap.getMap()[199][199];
        targetTile.setTexture(Texture.WATER);
        assertEquals(DROP_BUILDING_TEXTURE.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x " + (200 - size / 2) + " -t barrack -y "+ (200 - size / 2), DROP_BUILDING.toString()), player
                ), "water in one of the tiles");
        targetTile.setTexture(Texture.STONE_SLAB);
        assertEquals(DROP_BUILDING_TEXTURE.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x " + (200 - size / 2) + " -t barrack -y "+ (200 - size / 2), DROP_BUILDING.toString()), player
                ), "stone slab in one of the tiles");
    }

    @Test
    void checkAcquisition() {
        int size = BarracksType.TUNNELER_GUILD.getSize();
        Tile targetTile = gameMap.getMap()[199][199];
        targetTile.setOwner(new Player(null, null));
        assertEquals(ACQUISITION.getOutput(),
                buildingController.dropBuildingMatcherHandler(
                        getMatcher("drop building -x " + (200 - size / 2) +
                                " -t \"tunneler guild\" -y "+ (200 - size / 2), DROP_BUILDING.toString()), player
                ), "not owning one of the required tiles");
    }

    @Nested
    class checkEachBuilding {
        @Nested
        class checkTrap {
            @Test
            void checkUnitExist() {
                Tile targetTile = gameMap.getMap()[5][5];
                new Troop(player, targetTile, TroopTypes.SWORDMEN);
                assertEquals(UNIT_EXIST_TRAP.getOutput(),
                        buildingController.dropBuildingMatcherHandler(
                                getMatcher("drop building -x 5 -t \"caged war dogs\" -y 5", DROP_BUILDING.toString()), player
                        ), "the needed tile has a unit");
            }

            @Test
            void checkBadTextureForTrap() {
                Tile targetTile = gameMap.getMap()[0][0];
                assertEquals(BAD_TEXTURE_TRAP.getOutput(),
                        buildingController.dropBuildingMatcherHandler(
                                getMatcher("drop building -x 1 -t \"pitch ditch\" -y 1", DROP_BUILDING.toString()), player
                        ), "not a valid texture for the given trap");

                assertEquals(NOT_FIT.getOutput(),
                        buildingController.dropBuildingMatcherHandler(
                                getMatcher("drop building -x 1 -t \"caged war dogs\" -y 1", DROP_BUILDING.toString()), player
                        ), "check if fit");

                targetTile.setTexture(Texture.OIL);
                assertEquals(SUCCESSFUL_DROP_BUILDING.getOutput(),
                        buildingController.dropBuildingMatcherHandler(
                                getMatcher("drop building -x 1 -t \"pitch ditch\" -y 1", DROP_BUILDING.toString()), player
                        ), "valid size for trap");

            }

        }

        @Test
        void checkStockPileFoodStorage() {
            assertEquals(SUCCESSFUL_DROP_BUILDING.getOutput(),
                    buildingController.dropBuildingMatcherHandler(
                            getMatcher("drop building -x 1 -t stockpile -y 1", DROP_BUILDING.toString()), player
                    ), "not giving error for putting the first stockpile");

            assertEquals(NO_INVENTORY_AROUND.getOutput(),
                    buildingController.dropBuildingMatcherHandler(
                            getMatcher("drop building -x 1 -t stockpile -y 3", DROP_BUILDING.toString()), player
                    ), "stockpile not being neighboured with another stockpile");

            assertEquals(SUCCESSFUL_DROP_BUILDING.getOutput(),
                    buildingController.dropBuildingMatcherHandler(
                            getMatcher("drop building -x 1 -t stockpile -y 2", DROP_BUILDING.toString()), player
                    ), "successful stockpile");
        }

        @Test
        void checkEnoughPopulationGenerator() {
            int neededWorkers = GeneratorTypes.BAKERY.getWorker();
            player.setCurrentPopulation(neededWorkers - 1);
            assertEquals(NOT_ENOUGH_WORKER_BUILDING.getOutput(),
                    buildingController.dropBuildingMatcherHandler(
                            getMatcher("drop building -x 1 -t bakery -y 1", DROP_BUILDING.toString()), player
                    ), "not enough worker for generator");

            player.setCurrentPopulation(neededWorkers);
            assertEquals(SUCCESSFUL_DROP_BUILDING.getOutput(),
                    buildingController.dropBuildingMatcherHandler(
                            getMatcher("drop building -x 50 -t bakery -y 50", DROP_BUILDING.toString()), player
                    ), "successful generator");
        }
    }
}