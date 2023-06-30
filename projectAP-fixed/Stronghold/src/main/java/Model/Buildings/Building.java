package Model.Buildings;

import Model.Buildings.Defending.Enums.GateTypes;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Buildings.Defending.Enums.WallTypes;
import Model.Buildings.Defending.Gates;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Defending.Wall;
import Model.Buildings.Enums.*;
import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.graphics.MapFX;
import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public abstract class Building extends Drawable {
    protected int length;
    protected int width;
    protected static int size;
    protected int stoneCost;
    protected int woodCost;
    protected int goldCost;
    protected BuildingGraphics buildingShape;
    protected boolean onFire = false;

    public void setName(String name) {
        this.name = name;
    }

    protected static HashSet<Texture> textures;
    protected MapFX.BuildingShape mapBuildingShape;

    public Building(Player owner, Tile position, int size, String name, MapFX mapFX) {
        super(owner, position, name);
        buildingShape = BuildingGraphics.getBuildingByName(name);

        //keep get made when we load maps so we don't have mapFX
        if (!name.equals("keep")) {
            Platform.runLater(() -> {
                mapBuildingShape = new MapFX.BuildingShape(this, mapFX);
                if (mapBuildingShape == null)
                    System.out.println("there was a problem in getting buildingGraphics for " + name);
            });
        }

        Building.size = size;

        GameMap gameMap = Tile.getGameMap();
        int xCenter = position.getRowNum();
        int yCenter = position.getColumnNum();
        int halfSize = size / 2;
        if (size == 0) {
            halfSize = 0;
        }
        for (int x = xCenter - halfSize; x <= xCenter + halfSize; x++) {
            for (int y = yCenter - halfSize; y <= yCenter + halfSize; y++) {
                gameMap.getMap()[x][y].setBuilding(this);
            }
        }
    }

    public static HashMap<String, Integer> findCost(String type) {
        HashMap<String, Integer> ans=new HashMap<>();

        BarracksType barracksType = BarracksType.getTypeByName(type);
        if (barracksType != null) {
            ans.put("gold", barracksType.getGold());
            ans.put("wood", barracksType.getWood());
            ans.put("stone", barracksType.getStoneCost());
        }

        GeneratorTypes generatorType = GeneratorTypes.getTypeByName(type);
        if (generatorType != null) {
            ans.put("gold", generatorType.getGold());
            ans.put("wood", generatorType.getWood());
            ans.put("stone", 0);
        }

        RestTypes restType = RestTypes.getTypeByName(type);
        if (restType != null) {
            ans.put("gold", restType.getGold());
            ans.put("wood", restType.getWood());
            ans.put("stone", 0);
        }

        InventoryTypes inventoryType = InventoryTypes.getTypeByName(type);
        if (inventoryType != null) {
            ans.put("gold", 0);
            ans.put("wood", inventoryType.getWood());
            ans.put("stone", inventoryType.getStoneCost());
        }

        GateTypes gateType = GateTypes.getTypeByName(type);
        if (gateType != null) {
            ans.put("gold", 0);
            ans.put("wood", gateType.getWoodCost());
            ans.put("stone", gateType.getStoneCost());
        }

        TowerTypes towerType = TowerTypes.getTypeByName(type);
        if (towerType != null) {
            ans.put("gold", 0);
            ans.put("wood", 0);
            ans.put("stone", towerType.getStoneCost());
        }

        TrapsTypes trapsType = TrapsTypes.getTypeByName(type);
        if (trapsType != null) {
            ans.put("gold", trapsType.getGold());
            ans.put("wood", trapsType.getWood());
            ans.put("stone", 0);
        }

        WallTypes wallType = WallTypes.getTypeByName(type);
        if (wallType != null) {
            ans.put("gold", 0);
            ans.put("wood", 0);
            ans.put("stone", wallType.getStoneCost());
        }

        if (type.equals("store")) {
            ans.put("gold", 10);
            ans.put("wood", 5);
            ans.put("stone", 5);
        }

        if (type.equals("keep")) {
            ans.put("gold", 0);
            ans.put("wood", 0);
            ans.put("stone", 0);
        }
        return ans;
    }
    protected void manageCost() {
        owner.decreaseGold(goldCost);
        owner.decreaseInventory(Resources.WOOD, woodCost);
        owner.decreaseInventory(Resources.STONE, stoneCost);
    }

    public BuildingGraphics getBuildingShape() {
        return buildingShape;
    }

    public static HashSet<Texture> getTextures() {
        return textures;
    }

    @Override
    public void check() {
        if (!onFire){
            int getFireChance = new Random().nextInt(30);
            if (getFireChance > 27) {
                onFire = true;
                Platform.runLater(() -> {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setSpread(0.3);
                    dropShadow.setColor(Color.RED);
                    mapBuildingShape.polygon.setEffect(dropShadow);
                });
            }
        }
        else HP -= 30;
    }


    public boolean shouldBreak() {
        if (HP <= 0) {
            Platform.runLater(() -> {
                mapBuildingShape.removeBuildingShape();
            });
            this.erase();
            return true;
        }
        return false;
    }

    public void erase() {
        super.erase();
        GameMap gameMap = Tile.getGameMap();
        int halfSize = size / 2;
        for (int x = position.getRowNum() - halfSize; x <= position.getRowNum() + halfSize; x++) {
            for (int y = position.getColumnNum() - halfSize; y <= position.getColumnNum() + halfSize; y++) {
                gameMap.getMap()[x][y].setBuilding(null);
            }
        }

        if (this instanceof Gates) {
            Gates gates = ((Gates) this);
            for (Tile tile : gates.getTerminals()) tile.setBuilding(null);
        }
    }

    public static char getTagOfBuilding(Building building) {
        if (building instanceof Keep) {
            return 'K';
        }
        if (building instanceof Gates) {
            return 'G';
        }
        if (building instanceof Towers) {
            return 'T';
        }
        if (building instanceof Generators) {
            Generators generator = ((Generators) building);
            if (generator.getType().getProduct().getType().equals(ResourceTypes.STOCK)) {
                return 's';
            } else if (generator.getType().getProduct().getType().equals(ResourceTypes.WEAPON)) {
                return 'w';
            } else if (generator.getType().getProduct().getType().equals(ResourceTypes.FOOD)) {
                return 'f';
            }
        }
        if (building instanceof Inventory) {
            Inventory inventory = ((Inventory) building);
            if (inventory.getType().equals(InventoryTypes.ARMOURY)) {
                return 'A';
            } else if (inventory.getType().equals(InventoryTypes.FOOD_STORAGE)) {
                return 'F';
            } else if (inventory.getType().equals(InventoryTypes.STOCKPILE)) {
                return 'S';
            }
        }
        if (building instanceof Store) {
            return 'O';
        }
        if (building instanceof Barracks) {
            Barracks barracks = ((Barracks) building);
            if (barracks.getType().equals(BarracksType.BARRACK)) {
                return 'B';
            } else if (barracks.getType().equals(BarracksType.ENGINEER_GUILD)) {
                return 'E';
            } else if (barracks.getType().equals(BarracksType.MERCENARY_POST)) {
                return 'M';
            } else {
                return 'D';
            }
        }
        if (building instanceof Wall) {
            return 'x';
        }
        return 'z';
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        size = size;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public void setStoneCost(int stoneCost) {
        this.stoneCost = stoneCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public void setWoodCost(int woodCost) {
        this.woodCost = woodCost;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

    public static void setTextures(HashSet<Texture> textures) {
        Building.textures = textures;
    }

    public MapFX.BuildingShape getMapBuildingShape() {
        return mapBuildingShape;
    }
}
