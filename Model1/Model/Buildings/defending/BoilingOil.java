package Model1.Model.Buildings.defending;

import Model1.Model.User;

public class BoilingOil extends CastleBuilding{
    private int damage;
    public BoilingOil(int xPos, int yPos, User owner, int damage) {
        super(xPos, yPos, owner);
    }

    @Override
    public void action() {
        //kam shodan ghir
        //check
        //damage
    }

    @Override
    public void print() {

    }
}
