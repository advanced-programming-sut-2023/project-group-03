package model.Buildings;

import model.Buildings.Enums.InventoryTypes;
import model.Buildings.Enums.Resources;
import model.Field.Tile;
import model.GamePlay.Player;

import java.util.HashMap;

public class Inventory extends Building {
    private InventoryTypes type;
    private HashMap<Resources,Integer> inventory;
    int capacity;

    public Inventory(Player owner, Tile position, InventoryTypes type) {
        super(owner, position);
        inventory = new HashMap<>();
        for (Resources now : type.getResources()) {
            inventory.put(now,0);
        }
        this.type = type;
        this.capacity = type.getCapacity();
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
        // if HP<=0
    }

    @Override
    public void print() {

    }
}
