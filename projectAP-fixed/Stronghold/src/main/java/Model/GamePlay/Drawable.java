package Model.GamePlay;

import Model.Field.Tile;

import java.util.ArrayList;

public abstract class Drawable {
    protected String name;
    protected Player owner;
    protected Tile position;
    protected ArrayList<Tile> tiles;
    protected Material material;
    protected int HP;
    private static ArrayList<Drawable> drawables = new ArrayList<>();


    public String getName() {
        return name;
    }

    public Drawable(Player owner, Tile position, String name) {
        this.name = name;
        this.owner = owner;
        this.position = position;
        drawables.add(this);
    }

    public void getHit(int value) {

    }

    public void erase() {
        drawables.remove(this);
    }

    public abstract void check();

    public abstract void print();

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    protected abstract boolean shouldBreak();
    public static ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public static void setDrawables(ArrayList<Drawable> drawables) {
        Drawable.drawables = drawables;
    }

    public void remove() {
    }
}
