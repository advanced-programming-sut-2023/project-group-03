package Model.Units;

import Model.Buildings.Defending.CastleBuilding;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import controller.gameControllers.MoveUnitController;

import java.util.ArrayList;

public abstract class Unit extends Drawable {
    private boolean isPatrol = false;
    protected int speed;
    private Tile Start;
    private Tile end;
    protected Tile currentTarget = null;
    protected ArrayList<Tile> currentPath = new ArrayList<>();

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

    public void AutoMove() {
        if (currentTarget == null) {
            return;
        }
        if (currentTarget.equals(position)) {
            return ;
        }
        GameMap map = owner.getGame().getMap();
        currentPath = MoveUnitController.findPath(position, currentTarget, map);
        if (speed >= currentPath.size() - 1) {
            position = currentPath.get(currentPath.size() - 1);
        } else {
            position = currentPath.get(speed);
        }
    }

    @Override
    public void check() {
        shouldBreak();
        if (isPatrol) {
            Patrol();
            return;
        }
    }

    public void Patrol() {
        if (isPatrol) {
            if (currentTarget == null) {
                if (position != end) {
                    currentTarget = end;
                } else {
                    currentTarget = Start;
                }
            }
            currentPath = MoveUnitController.findPath(position, currentTarget, owner.getGame().getMap());
            if (speed >= currentPath.size() - 1) {
                position = currentPath.get(currentPath.size() - 1);
                if (currentTarget.equals(end)) {
                    currentTarget = Start;
                } else {
                    currentTarget = end;
                }
            } else {
                position = currentPath.get(speed);
            }
        }
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
