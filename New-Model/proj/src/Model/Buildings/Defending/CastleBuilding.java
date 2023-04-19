package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public abstract class CastleBuilding extends Building {
    protected int stoneCost;
    protected ArrayList<Troop> troops;
    public CastleBuilding(Player owner, Tile position) {
        super(owner, position);
        this.setMaterial(Material.STONE);
        this.troops = new ArrayList<>();
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
}
