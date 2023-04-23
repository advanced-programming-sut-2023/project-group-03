package Model.Units;

import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public class engineer extends nonCombatUnit{

    Drawable job;

    public engineer(Player owner, Tile position) {
        super(owner, position);
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
