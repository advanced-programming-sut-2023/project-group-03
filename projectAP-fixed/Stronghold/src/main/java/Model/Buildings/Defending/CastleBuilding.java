package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public abstract class CastleBuilding extends Building {
    protected int stoneCost;
    protected ArrayList<Troop> troops;
    public CastleBuilding(Player owner, Tile position, int size,String name) {
        super(owner, position, size,name);
        this.setMaterial(Material.STONE);
        this.troops = new ArrayList<>();
        owner.decreaseInventory(Resources.STONE,stoneCost);
    }

    public void setStoneCost(int stoneCost) {
        this.stoneCost = stoneCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public void addTroop(Troop troop) {
        troops.add(troop);
    }

    public void removeTroop(Troop troop) {
        troops.remove(troop);
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }
}
