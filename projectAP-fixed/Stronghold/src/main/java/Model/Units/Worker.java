package Model.Units;

import Model.Buildings.Building;
import Model.Buildings.Generators;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Worker extends nonCombatUnit{

    Generators job;
    public Worker(Player owner, Tile position, Generators job) {
        super(owner, position,"worker");
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
