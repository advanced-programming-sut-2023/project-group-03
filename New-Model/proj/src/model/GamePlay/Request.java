package model.GamePlay;

import model.Buildings.Enums.Resources;

import java.util.ArrayList;
import java.util.HashMap;

public class Request {
    Player owner;
    HashMap<Resources, Integer> list;
    ArrayList<Player> target;

    public Request(Player owner) {
        this.owner = owner;
    }

    public void addToList(Resources resources, int number) {
        if(list.containsKey(resources)){
            list.replace(resources,Integer.sum(list.get(resources),number));
            return;
        }
        list.put(resources,number);
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

    public HashMap<Resources, Integer> getList() {
        return list;
    }

    public ArrayList<Player> getTarget() {
        return target;
    }
}
