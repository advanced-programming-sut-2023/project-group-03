package Model.Units;

import Model.Buildings.Generators;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Worker extends nonCombatUnit{

    Generators job;
    public Worker(Player owner, Tile position, Generators job) {
        super(owner, position);
        owner.addUnit(this);
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
}
