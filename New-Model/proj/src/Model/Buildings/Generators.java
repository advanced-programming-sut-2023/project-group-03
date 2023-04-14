package Model.Buildings;

import Model.Buildings.Enums.GeneratorTypes;
import Model.Buildings.Enums.Resources;
import Model.Feild.Tile;
import Model.GamePlay.Player;

public class Generators extends Building{
    private GeneratorTypes type ;
    private int rate;
    private int Inventory;
    private int Capacity;
    private Resources product;
    private Resources Use;
    private int wood;
    private int gold;
    private int worker;

    private boolean isInfire;

    public Generators(Player owner, Tile position, GeneratorTypes type) {
        super(owner, position);
    }

    public GeneratorTypes getType() {
        return type;
    }

    public boolean isInfire() {
        return isInfire;
    }

    public int getInventory() {
        return Inventory;
    }

    public int getCapacity() {
        return Capacity;
    }

    public Resources getProduct() {
        return product;
    }

    public Resources getUse() {
        return Use;
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }
}
