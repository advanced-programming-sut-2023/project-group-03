package Model.Buildings;

import Model.Buildings.Enums.InventoryTypes;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;

import java.util.HashMap;

public class Inventory extends Building {
    private InventoryTypes type;
    private HashMap<Resources,Integer> inventory;
    int capacity;

    public Inventory(Player owner, Tile position, InventoryTypes type) {
        super(owner, position, type.getSize(),type.getName());
        goldCost = 10;
        stoneCost = type.getStoneCost();
        woodCost = type.getWood();
        inventory = new HashMap<>();
        this.type = type;
        this.HP = this.type.getHP();
        for (Resources now : type.getResources()) {
            inventory.put(now,0);
        }
        this.capacity = type.getCapacity();
        if (type.equals(InventoryTypes.ARMOURY)) {
            owner.getKeep().setArmoury(this);
            material = Material.IRON;
        }
        if (type.equals(InventoryTypes.STOCKPILE)) {
            owner.getKeep().setStockPile(this);
            material = Material.IRON;
        }
        if (type.equals(InventoryTypes.FOOD_STORAGE)) {
            owner.getKeep().setFoodStorage(this);
            material = Material.IRON;
        }
        manageCost();
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
    }

    @Override
    public void print() {

    }
}
