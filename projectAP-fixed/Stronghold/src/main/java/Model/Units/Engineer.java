package Model.Units;

import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public class Engineer extends nonCombatUnit{
    public final static int price = 1;

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
    }

    @Override
    public void check() {
        super.check();
    }

    @Override
    public void print() {

    }
}
