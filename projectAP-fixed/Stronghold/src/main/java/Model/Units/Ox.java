package Model.Units;

import Model.Buildings.Generators;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class Ox extends Worker {
    private int cargo = 0;

    public Ox(Player owner, Tile position, Generators generator, MapFX mapFX) {
        super(owner, position, generator, mapFX);
    }

    public void check() {
        if (position.getBuilding() != null && position.getBuilding().equals(job)) {
            int cargoAmount = Math.min(job.getCapacity() / 3, job.getCurrentResourceAmount());
            cargo = cargoAmount;
            job.loseCurrentResource(cargoAmount);
        } else if (position.equals(getEnd()) || position.equals(getStart())) {
            owner.increaseInventory(job.getProduct(), cargo);
            cargo = 0;
        }
        super.check();
    }
}
