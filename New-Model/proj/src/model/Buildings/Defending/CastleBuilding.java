package model.Buildings.Defending;

import model.Buildings.Building;
import model.Buildings.Enums.Resources;
import model.Field.Tile;
import model.GamePlay.Material;
import model.GamePlay.Player;
import model.Units.Combat.Troop;

import java.util.ArrayList;

public abstract class CastleBuilding extends Building {
    protected int stoneCost;
    protected ArrayList<Troop> troops;
    public CastleBuilding(Player owner, Tile position) {
        super(owner, position);
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
