package Model.Buildings;

import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;

public class Store extends Building{
    private static Store instance;

    public static Store getInstance() {
        return instance;
    }

    public static void setInstance(Player owner, Tile position) {

    }

    private Store(Player owner, Tile position) {
        super(owner, position);
        this.setMaterial(Material.STONE);
        this.setHP(200);
        this.setSize(3);
        this.setGoldCost(0);
        this.setStoneCost(20);
        this.setWoodCost(0);
    }

    public void buy(Resources resources, int number) {
        owner.decreaseGold(number * resources.getGold());
        owner.increaseInventory(resources,number);
    }

    public void sell(Resources resources, int number) {
        owner.increaseGold(number * resources.getGold());
        owner.decreaseInventory(resources,number);
    }

    @Override
    public void check() {
        shouldBreak();
    }

    @Override
    public void print() {

    }



}
