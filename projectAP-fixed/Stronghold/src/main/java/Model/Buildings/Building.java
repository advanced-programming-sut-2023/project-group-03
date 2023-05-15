package Model.Buildings;

import Model.Buildings.Defending.Gates;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Defending.Wall;
import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.InventoryTypes;
import Model.Buildings.Enums.ResourceTypes;
import Model.Buildings.Enums.Resources;
import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

import java.util.HashSet;

public abstract class Building extends Drawable {
    protected int length;
    protected int width;
    protected static int size;
    protected int stoneCost;
    protected int woodCost;
    protected int goldCost;

    public void setName(String name) {
        this.name = name;
    }

    protected static HashSet<Texture> textures;

    public Building(Player owner, Tile position, int size, String name) {
        super(owner, position, name);
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

    protected void manageCost() {
        owner.decreaseGold(goldCost);
        owner.decreaseInventory(Resources.WOOD, woodCost);
        owner.decreaseInventory(Resources.STONE, stoneCost);
    }

    public static HashSet<Texture> getTextures() {
        return textures;
    }


    protected boolean shouldBreak() {
        if (HP <= 0) {
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
}
