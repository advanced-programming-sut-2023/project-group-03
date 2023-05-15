package Model.GamePlay;

import Model.Buildings.Enums.Resources;
import Model.Element;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Request extends Element {
    Player owner;
    Resources resource;
    int amount;
    int price;
    String massage;
    ArrayList<Player> target;
    private static int counter = 1000;
    private int id;
    Player acceptedBy = null;

    public Request(Player owner,Resources resource,int amount,int price) {
        this.owner = owner;
        this.resource = resource;
        this.amount = amount;
        this.price = price;
        counter++;
        id = counter;
    }

    public void removeFromList(Resources resources, int number) {

    }

    public void addTarget(Player player) {
        target.add(player);
    }

    public void removeTarget(Player player) {
        target.remove(player);
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Player> getTarget() {
        return target;
    }

    public int getId() {
        return id;
    }

    public void sendToAll() {
        for (int i = 0; i < owner.getGame().getPlayers().size(); i++) {
            Player player = owner.getGame().getPlayers().get(i);
            if (player != this.owner) {
                player.addToIncomeRequest(this);
            }
        }
    }

    public Resources getResource() {
        return resource;
    }

    public void setResource(Resources resource) {
        this.resource = resource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public void setAcceptedBy(Player acceptedBy) {
        this.acceptedBy = acceptedBy;
        acceptedBy.decreaseInventory(resource, amount);
        owner.increaseInventory(resource, amount);
        acceptedBy.increaseGold(price);
        owner.decreaseGold(price);
    }

    @Override
    public String toString() {
        return "Request{" +
                "owner=" + owner +
                ", resource=" + resource +
                ", amount=" + amount +
                ", price=" + price +
                ", massage='" + massage + '\'' +
                ", id=" + id +
                ", acceptedBy=" + acceptedBy.getUser().getUsername() +
                '}';
    }
}
