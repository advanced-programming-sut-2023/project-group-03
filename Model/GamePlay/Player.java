package Model.GamePlay;

import Model.Buildings.Enums.Resources;
import Model.Units.Unit;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private Game game;
    private User user;
    private Governant governant;
    ArrayList<Request> IncomeRequests;
    ArrayList<Request> MyRequests;
    ArrayList<Unit> allUnits;
    int gold;
    int popularity;
    int fearFactor;
    int taxRate;
    int FoodRate;
    HashMap<Resources, Integer> inventory;

    public Player(User user, Governant governant) {
        this.user = user;
        this.governant = governant;
    }

    public void increaseInventory(Resources resources, int number) {

    }

    public void decreaseInventory(Resources resources, int number) {

    }

    public void addUnit(Unit unit) {

    }

    public void removeUnit(Unit unit) {

    }

    public void update() {

    }

    public void addToMyRequest(Request request) {

    }

    public void addToIncomeRequest(Request request) {

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

    public Game getGame() {
        return game;
    }

    public User getUser() {
        return user;
    }

    public Governant getGovernant() {
        return governant;
    }

    public ArrayList<Request> getIncomeRequests() {
        return IncomeRequests;
    }

    public ArrayList<Request> getMyRequests() {
        return MyRequests;
    }

    public ArrayList<Unit> getAllUnits() {
        return allUnits;
    }

    public HashMap<Resources, Integer> getInventory() {
        return inventory;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
