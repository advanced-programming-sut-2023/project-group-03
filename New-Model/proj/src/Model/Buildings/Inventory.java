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
        super(owner, position);

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

    }

    @Override
    public void print() {

    }
}
