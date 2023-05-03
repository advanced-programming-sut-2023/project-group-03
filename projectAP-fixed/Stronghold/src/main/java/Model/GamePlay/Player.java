package Model.GamePlay;

import Model.Buildings.Enums.Resources;
import Model.Buildings.Keep;
import Model.Units.Unit;
import Model.User;
import Model.Field.flagColors;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private flagColors flagColor;
    private Game game;
    private User user;
    private Government government;
    private Keep keep = null;
    ArrayList<Request> incomeRequests;
    ArrayList<Request> myRequests;
    ArrayList<Unit> allUnits;
    int gold;
    int popularity;
    int fearFactor;
    int taxRate;
    int FoodRate;
    HashMap<Resources, Integer> inventory = new HashMap<>();

    public Player(User user, Government government) {
        this.user = user;
        this.government = government;
        Resources[] RList = Resources.values();
        for (Resources now : RList) {
            inventory.put(now,0);
        }
    }

    public void increaseGold(int number){
        gold+=number;
    }

    public void decreaseGold(int number){
        gold-=number;
    }

    public int getResourceAmount(Resources resources){
        return inventory.get(resources);
    }

    public void increaseInventory(Resources resources, int number) {
        inventory.replace(resources,Integer.sum(inventory.get(resources), number));
    }

    public void decreaseInventory(Resources resources, int number) {
        inventory.replace(resources,Integer.sum(inventory.get(resources), -number));
    }

    public void addUnit(Unit unit) {
        allUnits.add(unit);
    }

    public void removeUnit(Unit unit) {
        allUnits.remove(unit);
    }

    public void update() {

    }

    public void addToMyRequest(Request request) {
        myRequests.add(request);
    }

    public void addToIncomeRequest(Request request) {
        incomeRequests.add(request);
    }
    public void answerRequest(Request request) {

    }

    public void sendRequest(Request request) {

    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getFearFactor() {
        return fearFactor;
    }

    public void setFearFactor(int fearFactor) {
        this.fearFactor = fearFactor;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFoodRate() {
        return FoodRate;
    }

    public void setFoodRate(int foodRate) {
        FoodRate = foodRate;
    }

    public Keep getKeep() {
        return keep;
    }

    public void setKeep(Keep keep) {
        this.keep = keep;
    }

    public Game getGame() {
        return game;
    }

    public User getUser() {
        return user;
    }

    public Government getGovernment() {
        return government;
    }

    public ArrayList<Request> getIncomeRequests() {
        return incomeRequests;
    }

    public ArrayList<Request> getMyRequests() {
        return myRequests;
    }

    public ArrayList<Unit> getAllUnits() {
        return allUnits;
    }

    public HashMap<Resources, Integer> getInventory() {
        return inventory;
    }

    public void setFlagColor(flagColors flagColor) {
        this.flagColor = flagColor;
    }

    public flagColors getFlagColor() {
        return flagColor;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    public Request getRequestById(int id) {
        for (int i = 0; i < incomeRequests.size(); i++) {
            if (incomeRequests.get(i).getId() == id) {
                return incomeRequests.get(i);
            }
        }
        return null;
    }
}
