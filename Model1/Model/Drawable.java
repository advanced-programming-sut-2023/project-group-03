package Model1.Model;

public abstract class Drawable
{
    protected int xPos;
    protected int yPos;
    User Owner;

    public abstract void print();

    public Drawable(int xPos, int yPos, User owner) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.Owner= owner;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

}
