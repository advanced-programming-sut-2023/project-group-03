package Model.Units;

import Model.Buildings.Defending.CastleBuilding;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Combat.Throwers;
import Model.Units.Combat.Troop;
import Model.graphics.MapFX;
import controller.gameControllers.MoveUnitController;
import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public abstract class Unit extends Drawable {
    private boolean isPatrol = false;
    protected int speed;
    private Tile Start;
    private Tile end;
    protected Tile currentTarget = null;
    protected Tile BufferTarget = null;
    protected MapFX.UnitShape mapUnitShape;
    protected ArrayList<Tile> currentPath = new ArrayList<>();
    protected boolean ill;

    public Unit(Player owner, Tile position, String name, MapFX mapFX) {
        super(owner, position, name);
        Platform.runLater(() -> {
            mapUnitShape = new MapFX.UnitShape(this, mapFX);
        });
        currentTarget = position;
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
            return;
        }
        GameMap map = owner.getGame().getMap();
        currentPath = MoveUnitController.findPath(position, currentTarget, map, owner);
        if (speed >= currentPath.size() - 1) {
            moveToTile(currentPath.get(currentPath.size() - 1));
        } else {
            moveToTile(currentPath.get(speed));
        }
    }

    @Override
    public void check() {
        if (!ill) {
            int getFireChance = new Random().nextInt(30);
            if (getFireChance > 27) {
                ill = true;
                Platform.runLater(() -> {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setSpread(0.3);
                    dropShadow.setColor(Color.GREENYELLOW);
                    mapUnitShape.shape.setEffect(dropShadow);
                });
            }
        }
        else HP -= 30;
        
        shouldBreak();
        if (isPatrol) {
            Patrol();
        }
    }

    public void moveToTile(Tile goal) {
        position.getUnits().remove(this);
        if (position.getBuilding() != null && position.getBuilding() instanceof CastleBuilding) {
            ((CastleBuilding) position.getBuilding()).getTroops().remove(this);
        }
        position = goal;
        goal.addUnit(this);
        if (position.getBuilding() != null && position.getBuilding() instanceof CastleBuilding
                && this instanceof Troop) {
            ((CastleBuilding) position.getBuilding()).getTroops().add(((Troop) this));
        }
        currentTarget = position;
    }

    public void Patrol() {
        if (isPatrol) {
            currentTarget = end;
            currentPath = MoveUnitController.findPath(position, currentTarget, owner.getGame().getMap(), owner);
            if (speed >= currentPath.size() - 1) {
                moveToTile(currentPath.get(currentPath.size() - 1));
                Tile buff = end;
                end = Start;
                Start = buff;
            } else {
                moveToTile(currentPath.get(speed));
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
            Platform.runLater(() -> {
                mapUnitShape.removeUnitShape();
            });
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

    public String getTypeOfUnit() {
        if (this instanceof Troop) {
            return ((Troop) this).getType().getName();
        } else if (this instanceof Throwers) {
            return ((Throwers) this).getType().getName();
        } else {
            return this.getClass().getSimpleName();
        }
    }

    public boolean isIll() {
        return ill;
    }

    public void setIll(boolean ill) {
        this.ill = ill;
    }

    public Tile getBufferTarget() {
        return BufferTarget;
    }

    public void setBufferTarget(Tile bufferTarget) {
        BufferTarget = bufferTarget;
    }

    public void setCurrentTarget(Tile currentTarget) {
        this.currentTarget = currentTarget;
        BufferTarget = currentTarget;
    }
}
