package Model.Units;

import Model.Buildings.Building;
import Model.Buildings.Enums.InventoryTypes;
import Model.Buildings.Enums.Resources;
import Model.Buildings.Generators;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Worker extends nonCombatUnit{

    Generators job;
    public Worker(Player owner, Tile position, Generators job) {
        super(owner, position,"worker");
        this.job = job;

        Resources product = job.getProduct();
        InventoryTypes inventoryType = null;
        for (InventoryTypes inventory : InventoryTypes.values()) {
               if (inventory.getResource().equals(product.getType())) {
                   inventoryType = inventory;
                   break;
               }
        }
        if (inventoryType.equals(InventoryTypes.ARMOURY)) {
            if (owner.getKeep().getArmoury() != null) this.setEnd(owner.getKeep().getArmoury().getPosition());
        }
        else if (inventoryType.equals(InventoryTypes.STOCKPILE)) {
            if (owner.getKeep().getStockPile() != null) this.setEnd(owner.getKeep().getStockPile().getPosition());
        }
        else if (inventoryType.equals(InventoryTypes.FOOD_STORAGE)) {
            if (owner.getKeep().getFoodStorage() != null) this.setEnd(owner.getKeep().getFoodStorage().getPosition());
        }
        else this.setEnd(owner.getKeep().getStockPile().getPosition());


        this.setPatrol(true);
        this.setStart(owner.getKeep().getPosition());
        this.setStart(job.getPosition());
    }


    public Generators getJob() {
        return job;
    }

    public void setJob(Generators job) {
        this.job = job;
    }

    @Override
    public void check() {
        super.check();
    }

    @Override
    public void print() {

    }

    public void goToWork(Building building) {
        this.setStart(building.getPosition());
        if (building instanceof Generators) {
            Generators generators = ((Generators) building);
            this.setEnd(owner.getKeep().getInventoryByType(generators.getProduct().getType()).getPosition());
            this.setPatrol(true);
        }
    }
}
