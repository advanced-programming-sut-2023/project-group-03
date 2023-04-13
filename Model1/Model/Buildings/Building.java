package Model1.Model.Buildings;

import Model1.Model.Drawable;
import Model1.Model.User;

public abstract class Building extends Drawable
{
    private int Size;

    public Building(int xPos, int yPos, User owner) {
        super(xPos, yPos,owner);
    }

    public abstract void getHit();
    public abstract void action();
}
