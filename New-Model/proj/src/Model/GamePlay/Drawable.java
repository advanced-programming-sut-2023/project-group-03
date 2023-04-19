package Model.GamePlay;

import Model.Feild.Tile;

public abstract class Drawable {
    Player owner;
    Tile position;
    Material material;
    int HP;

    public Drawable(Player owner, Tile position) {
        this.owner = owner;
        this.position = position;
    }

    public void getHit(int value) {

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
}