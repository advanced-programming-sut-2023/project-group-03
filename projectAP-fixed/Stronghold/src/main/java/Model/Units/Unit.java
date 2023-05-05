package Model.Units;

import Model.Buildings.Defending.CastleBuilding;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

public abstract class Unit extends Drawable {
    private boolean isPatrol = false;
    private Tile Start;
    private Tile end;
    public Unit(Player owner, Tile position) {
        super(owner, position);
        owner.addUnit(this);
        position.addUnit(this);
        if (position.getBuilding() != null) {
            if (position.getBuilding() instanceof CastleBuilding && this instanceof Troop) {
                ((CastleBuilding) position.getBuilding()).addTroop(((Troop) this));
            }
        }
    }

    public boolean moveTo(Tile tile) {

        return true;
    }

    @Override
    public void check() {

    }

    public boolean isPatrol() {
        return isPatrol;
    }

    public void setPatrol(boolean patrol) {
        isPatrol = patrol;
    }

    public Tile getStart() {
        return Start;
    }

    public void setStart(Tile start) {
        Start = start;
    }

    public Tile getEnd() {
        return end;
    }

    public void setEnd(Tile end) {
        this.end = end;
    }

    @Override
    protected boolean shouldBreak() {
        if (this.HP <= 0) {
            erase();
            return true;
        }
        return false;
    }

    @Override
    public void erase() {
        super.erase();
        position.removeUnit(this);
        owner.getAllUnits().remove(this);
        if (position.getBuilding() != null) {
            if (position.getBuilding() instanceof CastleBuilding && this instanceof Troop) {
                ((CastleBuilding) position.getBuilding()).getTroops().remove(this);
            }
        }
    }
}
