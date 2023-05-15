package Model.Buildings;

import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import view.Enums.ConsoleColors;

public class Store extends Building{
    private static Store instance;

    public static Store getInstance() {
        return instance;
    }

    public static void setInstance(Player owner, Tile position) {

    }


    public Store(Player owner, Tile position) {
        super(owner, position, 3, "store");
        setMaterial(Material.STONE);
        owner.getKeep().setStore(this);
        stoneCost = 5;
        woodCost = 5;
        goldCost = 10;
        manageCost();
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

    @Override
    public void erase() {
        super.erase();
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" +"Store"+ "> distroyed in (" +
                        position.getRowNum() + "," + position.getColumnNum() + ")");
        System.out.println(log);
    }
}
