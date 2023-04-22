package model.Units;

import model.Buildings.Generators;
import model.Field.Tile;
import model.GamePlay.Player;

public class worker extends nonCombatUnit{

    Generators job;
    public worker(Player owner, Tile position,Generators job) {
        super(owner, position);
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
