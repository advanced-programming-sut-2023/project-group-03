package Model.Units;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Keep;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public class Engineer extends nonCombatUnit{
    public final static int price = 5;

    Drawable job;

    public Engineer(Player owner, Tile position) {
        super(owner, position);
        position.addUnit(this);
        owner.decreaseGold(price);
    }

    public Drawable getJob() {
        return job;
    }

    public void setJob(Drawable job) {
        this.job = job;
        this.setPatrol(true);
        this.setStart(job.getPosition());
        this.setEnd(owner.getKeep().getBarracks().get(BarracksType.ENGINEER_GUILD).getPosition());
    }

    @Override
    public void check() {
        super.check();
    }

    @Override
    public void print() {

    }
}
