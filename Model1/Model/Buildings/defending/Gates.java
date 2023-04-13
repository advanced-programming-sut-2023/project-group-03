package Model1.Model.Buildings.defending;

import Model1.Model.Buildings.Enums.GatesTypes;
import Model1.Model.Units.Troop;
import Model1.Model.User;

import java.util.ArrayList;

class Gates extends CastleBuilding
{
    private GatesTypes type;
    private ArrayList<Troop> troops;
    boolean isOpen;
    public Gates(int xPos, int yPos, User owner) {
        super(xPos, yPos,owner);
    }

    @Override
    public void action() {

    }

    private void findOwner() {

    }

    private void findCloseOrOpen() {

    }
    @Override
    public void print() {

    }
}
