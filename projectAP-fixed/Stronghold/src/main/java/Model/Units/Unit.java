package Model.Units;

import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public abstract class Unit extends Drawable {
    private boolean isPatrol = false;
    private Tile Start;
    private Tile end;
    public Unit(Player owner, Tile position) {
        super(owner, position);
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
}
