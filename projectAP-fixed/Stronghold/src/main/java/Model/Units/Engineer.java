package Model.Units;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Keep;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public class Engineer extends nonCombatUnit{
    public final static int price = 5;

    Drawable job = null;

    public Engineer(Player owner, Tile position) {
        super(owner, position,"engineer");
        this.HP = 25;
        this.speed = 10;
        owner.decreaseGold(price);
        owner.getKeep().addEngineer(this);
        owner.setCurrentPopulation(owner.getCurrentPopulation() + 1);
    }

    public Drawable getJob() {
        return job;
    }

    public void setJob(Drawable thing) {
        this.job = thing;
        this.setPatrol(false);
    }

    public void getFired() {
        job = null;
    }

    @Override
    public void check() {
        super.check();
        if (job != null) setCurrentTarget(job.getPosition());
        else setBufferTarget(owner.getKeep().getBarracks().get(BarracksType.ENGINEER_GUILD).getPosition());
        AutoMove();
    }

    @Override
    public void print() {

    }
}
