package Model.Buildings;

import Model.Buildings.Enums.GeneratorTypes;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Worker;
import Model.graphics.MapFX;
import view.Enums.ConsoleColors;

import java.util.ArrayList;

public class Generators extends Building {
    private final GeneratorTypes type;
    private final int useRate;
    private final int produceRate;
    private final int inventory;
    private final int capacity;
    private int currentResourceAmount = 0;
    private final Resources product;
    private final Resources use;
    private final int wood;
    private final int gold;
    private final int worker;

    private final ArrayList<Worker> workers = new ArrayList<>();

    private boolean isInFire;

    public Generators(Player owner, Tile position, GeneratorTypes type, MapFX mapFX) {
        super(owner, position, type.getSize(), type.getName(), mapFX);
        this.type = type;
        this.useRate = type.getUseRate();
        this.produceRate = type.getProduceRate();
        this.HP = type.getHP();
        this.inventory = type.getInventory();
        this.capacity = type.getCapacity();
        this.product = type.getProduct();
        this.use = type.getUse();
        this.wood = type.getWood();
        this.gold = type.getGold();
        this.worker = type.getWorker();
        this.setMaterial(Material.WOOD);
        owner.setCurrentPopulation(owner.getCurrentPopulation() + worker);
        owner.decreaseInventory(Resources.WOOD, type.getWood());
        owner.decreaseGold(type.getGold());
    }

    public int getCurrentResourceAmount() {
        return currentResourceAmount;
    }

    public void loseCurrentResource(int amount) {
        currentResourceAmount -= amount;
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void removeWorker(Worker worker) {
        workers.remove(worker);
    }

    public GeneratorTypes getType() {
        return type;
    }

    public boolean isInFire() {
        return isInFire;
    }

    public int getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public Resources getProduct() {
        return product;
    }

    public Resources getUse() {
        return use;
    }

    public void makeResource() {
        if (type.equals(GeneratorTypes.STONE_MINE) || type.equals(GeneratorTypes.IRON_MINE)) {
            if (currentResourceAmount >= capacity) return;
            currentResourceAmount += Math.min(produceRate, capacity - currentResourceAmount);
        } else {
            if (use != null && useRate > owner.getResourceAmount(use)) {
                return;
            }
            if (use != null) {
                owner.decreaseInventory(use, useRate);
            }
            if (product != null) {
                owner.increaseInventory(product, produceRate);
            }
        }
    }

    @Override
    public void check() {
        if (shouldBreak()) {
            return;
        }
        makeResource();
    }

    public void erase() {
        for (Worker worker : workers) {
            worker.erase();
        }
        owner.getKeep().setCurrentEngineerPopulation(owner.getCurrentPopulation() - type.getWorker());
        workers.clear();
        super.erase();
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" + type.getName() + "> distroyed in (" +
                        position.getRowNum() + "," + position.getColumnNum() + ")");
        System.out.println(log);
    }

    @Override
    public void print() {
    }
}
