package Model.Buildings;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.CombatUnit;
import Model.Units.Enums.TroopTypes;
import Model.graphics.MapFX;
import view.Enums.ConsoleColors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static Model.Units.Enums.TroopTypes.*;

public class Barracks extends Building {

    BarracksType type;
    HashSet<TroopTypes> Products = new HashSet<>();

    public Barracks(Player owner, Tile position, BarracksType type, MapFX mapFX) {
        super(owner, position, type.getSize(), type.getName(), mapFX);
        this.material = type.getMaterial();
        this.type = type;
        this.HP = type.getHP();
        owner.decreaseInventory(Resources.WOOD, type.getWood());
        owner.decreaseInventory(Resources.OIL, type.getOil());
        owner.decreaseGold(type.getGold());
        owner.getKeep().getBarracks().put(type, this);
        if (type.equals(BarracksType.BARRACK))
            Products = new HashSet<>(
                    Arrays.asList(ARCHER, CROSSBOWMEN, SPEARMEN, PIKEMEN, MACEMEN, SWORDMEN, KNIGHT, BLACKMONK));
        else if (type.equals(BarracksType.ENGINEER_GUILD))
            Products = new HashSet<>(
                    List.of(LADDERMEN));
        else if (type.equals(BarracksType.MERCENARY_POST))
            Products = new HashSet<>(
                    Arrays.asList(
                            SLAVES, SLINGERS, ASSASSINS, HORSE_ARCHERS, ARABIAN_BOWS
                    ));
        else if (type.equals(BarracksType.TUNNELER_GUILD))
            Products = new HashSet<>(
                    List.of(TUNELLER));
    }
    public HashSet<TroopTypes> getProducts() {
        return Products;
    }

    public BarracksType getType() {
        return type;
    }

    public CombatUnit make(TroopTypes types) {
        return null;
    }

    @Override
    public void check() {
        if (shouldBreak()) {
        }
    }

    @Override
    public void erase() {
        super.erase();
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" + type.getName() + ">distroyed");
    }

    @Override
    public void print() {

    }
}
