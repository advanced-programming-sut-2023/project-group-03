package Model1.Model.Buildings;

import Model1.Model.Buildings.Enums.Resources;
import Model1.Model.User;

import java.util.HashMap;

class Keep extends Building
{
    private HashMap<Resources, Integer> inventory;
    int population;
    int workers;
    int popularity;
    int coins;
    int taxRate;
    int fearRate;

    public Keep(int xPos, int yPos, User owner) {
        super(xPos, yPos, owner);
    }

    @Override
    public void getHit() {

    }

    public HashMap<Resources, Integer> getInventory() {
        return inventory;
    }

    public int getPopulation() {
        return population;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getCoins() {
        return coins;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public boolean Hire(int number) {
        return true;
    }
    public boolean addResource(Resources resources,int number) {
        return true;
    }

    public boolean removeResource(Resources resources, int number) {
        return true;
    }

    @Override
    public void action() {

    }

    @Override
    public void print() {

    }
}
