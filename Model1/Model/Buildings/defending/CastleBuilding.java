package Model1.Model.Buildings.defending;

import Model1.Model.Buildings.Building;
import Model1.Model.User;

public abstract class CastleBuilding extends Building
{
    private int HP;

    public CastleBuilding(int xPos, int yPos, User owner) {
        super(xPos, yPos,owner);
    }
}
