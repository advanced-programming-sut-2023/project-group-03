package Model.Buildings;

import Model.Buildings.Enums.Resources;
import Model.Buildings.Enums.RestTypes;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.graphics.MapFX;
import view.Enums.ConsoleColors;

public class Rest extends Building {
    private RestTypes type;

    public Rest(Player owner, Tile position, RestTypes type, MapFX mapFX) {
        super(owner, position, type.getSize(), type.getName(), mapFX);
        owner.decreaseGold(type.getGold());
        owner.decreaseInventory(Resources.WOOD, type.getWood());
        if (type.equals(RestTypes.HOVEL)) {
            owner.setMaxPopulation(owner.getMaxPopulation() + 8);
        }
        material = Material.WOOD;
    }

    @Override
    public void check() {
        super.check();
        if (shouldBreak()) {
        }
    }

    @Override
    public void erase() {
        super.erase();
        if (type.equals(RestTypes.HOVEL)) {
            owner.setMaxPopulation(owner.getMaxPopulation() - 8);
        }
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" + type.getName() + "> distroyed in (" +
                        position.getRowNum() + "," + position.getColumnNum() + ")");
        System.out.println(log);
    }

    @Override
    public void print() {

    }
}
