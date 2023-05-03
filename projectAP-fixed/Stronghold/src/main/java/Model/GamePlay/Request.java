package Model.GamePlay;

import Model.Buildings.Enums.Resources;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Request {
    Player owner;
    Resources resource;
    int amount;
    int price;
    ArrayList<Player> target;
    private static int counter = 1000;
    private int id;
    public Request(Player owner,Resources resource,int amount,int price) {
        this.owner = owner;
        this.resource = resource;
        this.amount = amount;
        this.price = price;
        counter++;
        id = counter;
    }

//    public void addToList(Resources resources, int number) {
//        if(list.containsKey(resources)){
//            list.replace(resources,Integer.sum(list.get(resources),number));
//            return;
//        }
//        list.put(resources,number);
//    }

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

//    public HashMap<Resources, Integer> getList() {
//        return list;
//    }

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

    @Override
    public String toString() {
        return "Request{" +
                "resource=" + resource +
                ", amount=" + amount +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
