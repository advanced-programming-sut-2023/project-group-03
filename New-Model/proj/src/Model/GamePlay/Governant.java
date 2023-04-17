package Model.GamePlay;

import Model.Feild.Tile;

import java.util.ArrayList;

public class Governant {
    private ArrayList<Tile> territory;
    private ArrayList<Tile> free;
    int maxFood;
    int maxWeapon;
    int maxStock;
    int maxPopulation;
    int maxCombatUnits;
    int maxHorses;

    public ArrayList<Tile> getTerritory() {
        return territory;
    }

    public void setTerritory(ArrayList<Tile> territory) {
        this.territory = territory;
    }

    public ArrayList<Tile> getFree() {
        return free;
    }

    public void setFree(ArrayList<Tile> free) {
        this.free = free;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public void setMaxFood(int maxFood) {
        this.maxFood = maxFood;
    }

    public int getMaxWeapon() {
        return maxWeapon;
    }

    public void setMaxWeapon(int maxWeapon) {
        this.maxWeapon = maxWeapon;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public int getMaxHorses() {
        return maxHorses;
    }

    public void setMaxHorses(int maxHorses) {
        this.maxHorses = maxHorses;
    }
}
