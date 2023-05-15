package Model.Buildings;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.ResourceTypes;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Engineer;

import java.util.ArrayList;
import java.util.HashMap;

import static Model.Buildings.Enums.Resources.getFoods;

public class Keep extends Building {
    private Inventory armoury = null;
    private Inventory stockPile = null;
    private Inventory foodStorage = null;
    private Store store = null;
    protected HashMap<BarracksType, Barracks> barracks = new HashMap<>();
    private static Keep instance;
    private int taxRate;
    private int fearRate;
    private int foodRate;
    private int typeOfFood;

    private int maxEngineerPopulation = 0;
    private int currentEngineerPopulation = 0;

    private final ArrayList<Engineer> engineers = new ArrayList<>();

    public Keep(Player owner, Tile position) {
        super(owner, position, 5, "keep");
        owner.setKeep(this);
        HP = 10000;
        material = Material.IRON;
        taxRate = 0;
        foodRate = -2;
        fearRate = 0;
        typeOfFood = 0;
    }

    public static void setInstance(Player owner, Tile tile) {
        Keep.instance = instance;
    }

    public void addEngineer(Engineer engineer) {
        engineers.add(engineer);
        maxEngineerPopulation++;
    }

    public ArrayList<Engineer> getEngineers(int amount) {
        ArrayList<Engineer> answer = new ArrayList<>();
        for (Engineer engineer : engineers) {
            if (amount <= 0) break;
            if (engineer.getJob() == null) {
                amount--;
                answer.add(engineer);
            }
        }
        currentEngineerPopulation += amount;
        return answer;
    }

    public int getMaxEngineerPopulation() {
        return maxEngineerPopulation;
    }

    public void setMaxEngineerPopulation(int maxEngineerPopulation) {
        this.maxEngineerPopulation = maxEngineerPopulation;
    }

    public int getCurrentEngineerPopulation() {
        return currentEngineerPopulation;
    }

    public void setCurrentEngineerPopulation(int currentEngineerPopulation) {
        this.currentEngineerPopulation = currentEngineerPopulation;
    }

    public static Keep getInstance() {
        return instance;
    }

    @Override
    public void check() {
        RateHandler();
        FearHandler();
        foodHandler();
        TaxHandler();
        popularityHandler();
    }

    @Override
    public void print() {

    }

    private void RateHandler() {
        if (owner.getGold() == 0) {
            taxRate = 0;
        } else {
            taxRate = owner.getTaxRate();
        }
        fearRate = owner.getFearFactor();
        typeOfFood = 0;
        for (Resources food : Resources.getFoods()) {
            if (owner.getResourceAmount(food) > 0) {
                typeOfFood++;
            }
        }
        if (typeOfFood == 0) {
            foodRate = -2;
        } else {
            foodRate = owner.getFoodRate();
        }
    }

    private void FearHandler() {
        owner.setPopularity(owner.getPopularity() + fearRate);
    }

    private void foodHandler() {
        double Avrage = 0;
        if (foodRate == -2) {
            Avrage = 0;
            owner.setPopularity(owner.getPopularity() - 8);
        }
        if (foodRate == -1) {
            Avrage = 0.5;
            owner.setPopularity(owner.getPopularity() - 4);
        }
        if (foodRate == 0) {
            Avrage = 1;
            owner.setPopularity(owner.getPopularity());
        }
        if (foodRate == 1) {
            Avrage = 1.5;
            owner.setPopularity(owner.getPopularity() + 4);
        }
        if (foodRate == 2) {
            Avrage = 2;
            owner.setPopularity(owner.getPopularity() + 8);
        }
        owner.setPopularity(owner.getPopularity() + typeOfFood - 1);
        int amountToDecrease = ((int) Math.ceil(owner.getMaxPopulation() * Avrage / typeOfFood));
        for (Resources food : getFoods()) {
            owner.decreaseInventory(food, amountToDecrease);
        }
    }

    private void TaxHandler() {
        double avrage = 0;
        if (taxRate == -3) {
            avrage = -1;
            owner.setPopularity(owner.getPopularity() + 7);
        }
        if (taxRate == -2) {
            avrage = -0.8;
            owner.setPopularity(owner.getPopularity() + 5);
        }
        if (taxRate == -1) {
            avrage = -0.6;
            owner.setPopularity(owner.getPopularity() + 3);
        }
        if (taxRate == 0) {
            avrage = 0;
            owner.setPopularity(owner.getPopularity() + 1);
        }
        if (taxRate == 1) {
            avrage = 0.6;
            owner.setPopularity(owner.getPopularity() - 2);
        }
        if (taxRate == 2) {
            avrage = 0.8;
            owner.setPopularity(owner.getPopularity() - 4);
        }
        if (taxRate == 3) {
            avrage = 1;
            owner.setPopularity(owner.getPopularity() - 6);
        }
        if (taxRate == 4) {
            avrage = 1.2;
            owner.setPopularity(owner.getPopularity() - 8);
        }
        if (taxRate == 5) {
            avrage = 1.4;
            owner.setPopularity(owner.getPopularity() - 12);
        }
        if (taxRate == 6) {
            avrage = 1.6;
            owner.setPopularity(owner.getPopularity() - 16);
        }
        if (taxRate == 7) {
            avrage = 1.8;
            owner.setPopularity(owner.getPopularity() - 20);
        }
        if (taxRate == 8) {
            avrage = 2;
            owner.setPopularity(owner.getPopularity() + 24);
        }
        int cost = ((int) Math.ceil(owner.getMaxPopulation() * avrage));
        owner.increaseGold(cost);
    }

    public Inventory getArmoury() {
        return armoury;
    }

    public void setArmoury(Inventory armoury) {
        this.armoury = armoury;
    }

    public Inventory getStockPile() {
        return stockPile;
    }

    public void setStockPile(Inventory stockPile) {
        this.stockPile = stockPile;
    }

    public Inventory getFoodStorage() {
        return foodStorage;
    }

    public void setFoodStorage(Inventory foodStorage) {
        this.foodStorage = foodStorage;
    }

    public Inventory getInventoryByType(ResourceTypes resourceTypes) {
        if (resourceTypes.equals(ResourceTypes.FOOD)) {
            return this.foodStorage;
        }
        if (resourceTypes.equals(ResourceTypes.STOCK)) {
            return this.stockPile;
        }
        if (resourceTypes.equals(ResourceTypes.WEAPON)) {
            return this.armoury;
        }
        return null;
    }

    public void popularityHandler() {
        if (this.getOwner().getPopularity() > 100) {
            this.getOwner().setPopularity(100);
        }
    }

    public HashMap<BarracksType, Barracks> getBarracks() {
        return barracks;
    }

    public void setBarracks(HashMap<BarracksType, Barracks> barracks) {
        this.barracks = barracks;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
