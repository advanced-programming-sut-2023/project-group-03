package Model.Buildings;

import Model.Buildings.Enums.InventoryTypes;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Player;

import java.util.HashMap;

public class Inventory extends Building {
    private InventoryTypes type;
    private HashMap<Resources,Integer> inventory;
    int capacity;

    public Inventory(Player owner, Tile position, InventoryTypes type) {
        super(owner, position, type.getSize());
        inventory = new HashMap<>();
        for (Resources now : type.getResources()) {
            inventory.put(now,0);
        }
        this.capacity = type.getCapacity();
        if (type.equals(InventoryTypes.ARMOURY)) {
            owner.getKeep().setArmoury(this);
        }
        if (type.equals(InventoryTypes.STOCKPILE)) {
            owner.getKeep().setStockPile(this);
        }
        if (type.equals(InventoryTypes.FOOD_STORAGE)) {
            owner.getKeep().setFoodStorage(this);
        }
    }

    public InventoryTypes getType() {
        return type;
    }

    public HashMap<Resources, Integer> getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }


    @Override
    public void check() {
        if(shouldBreak()){
            return;
        }
        // should do sth else?? //NO
    }

    @Override
    public void print() {

    }
}
