package Model1.Model.Buildings;

import Model1.Model.Buildings.Enums.GenaratorTypes;
import Model1.Model.Units.Unit;
import Model1.Model.User;

public class Cathedral extends ResourceGenerator {

    public Cathedral(int xPos, int yPos, User owner, GenaratorTypes type) {
        super(xPos, yPos, owner, type);
    }
    public boolean Make(int number)
    {
        return true;
    }

    @Override
    public void action() {

    }

    @Override
    public void print() {

    }
}
