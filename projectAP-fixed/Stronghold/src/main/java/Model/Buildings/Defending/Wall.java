package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.WallTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;
import view.Enums.ConsoleColors;

public class Wall extends CastleBuilding {
    private final WallTypes type;
    private Trap trap;

    public Wall(Player owner, Tile position, WallTypes type, MapFX mapFX) {
        super(owner, position, 1, type.getName(), mapFX);
        this.type = type;
        this.HP = type.getHP();
        this.stoneCost = type.getStoneCost();
        size = 1;
        manageCost();
    }

    public Trap getTrap() {
        return trap;
    }

    public boolean setTrap(Trap trap) {
        if (this.trap == null) {
            return false;
        }
        this.trap = trap;
        return true;
    }

    @Override
    public void check() {
        if (shouldBreak()) {
        }
    }

    public void erase() {
        super.erase();
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" + type.getName() + "> distroyed in (" +
                        position.getRowNum() + "," + position.getColumnNum() + ")");
        System.out.println(log);
    }

    @Override
    public void print() {

    }

    public WallTypes getType() {
        return type;
    }
}
