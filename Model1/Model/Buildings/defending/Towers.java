package Model1.Model.Buildings.defending;

import Model1.Model.Buildings.Enums.TowerTypes;
import Model1.Model.Units.Throwers;
import Model1.Model.Units.Troop;
import Model1.Model.User;

import java.util.ArrayList;

class Towers extends CastleBuilding
{

    private TowerTypes type;
    private Throwers thrower;
    private ArrayList<Troop> troops;

    public Towers(int xPos, int yPos, User owner, TowerTypes towerTypes) {
        super(xPos, yPos, owner);
    }

    public TowerTypes getType() {
        return type;
    }

    public Throwers getThrower() {
        return thrower;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setThrower(Throwers thrower) {
        this.thrower = thrower;
    }

    public void addTroop(Troop troop) {

    }

    public void removeTroop(Troop troop) {

    }

    @Override
    public void getHit() {

    }

    @Override
    public void action() {

    }

    @Override
    public void print() {

    }
}
