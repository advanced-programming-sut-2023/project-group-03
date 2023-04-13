package Model1.Model.Buildings.defending;

import Model1.Model.Buildings.Enums.TrapTypes;
import Model1.Model.User;

class Trap extends CastleBuilding
{

    public TrapTypes type;
    public Trap(int xPos, int yPos, User owner,TrapTypes type) {
        super(xPos, yPos,owner);
        this.type=type;
    }

    @Override
    public void action() {

    }

    @Override
    public void print() {

    }
}
