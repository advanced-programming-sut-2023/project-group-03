package Model1.Model.Buildings;

import Model1.Model.Buildings.Enums.BarracksTypes;
import Model1.Model.Buildings.defending.CastleBuilding;
import Model1.Model.Units.Unit;
import Model1.Model.User;

import java.util.HashSet;

class Baracks extends CastleBuilding
{
    BarracksTypes type;
     HashSet<Unit> Products;

    public Baracks(int xPos, int yPos, User owner,BarracksTypes type) {
        super(xPos, yPos, owner);
        this.type=type;
    }

    public boolean Make(Unit unit, int number)
    {
        return true;
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
