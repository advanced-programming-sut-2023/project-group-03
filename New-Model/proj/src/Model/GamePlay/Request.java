package Model.GamePlay;

import Model.Buildings.Enums.Resources;
import Model.User;

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

    }

    public void removeFromList(Resources resources, int number) {

    }

    public void addTarget(Player player) {

    }

    public void removeTarget(Player player) {

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
