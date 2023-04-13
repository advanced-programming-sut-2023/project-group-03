package Model1.Model.Buildings;

import Model1.Model.Buildings.Enums.Resources;
import Model1.Model.Buildings.Enums.TypeOfInventories;
import Model1.Model.User;

import java.util.HashMap;

class Inventory extends Building {
    private TypeOfInventories type;
    private HashMap<Resources,Integer> inventory;
    int capacity;

    public Inventory(int xPos, int yPos, User owner, TypeOfInventories type) {
        super(xPos, yPos,owner);
        this.type=type;
        if (type.equals(TypeOfInventories.FOOD_STORAGE)) {

        } else if (type.equals(TypeOfInventories.STOCKPILE)) {

        } else {

        }
    }

    @Override
    public void action() {
    }

    @Override
    public void print() {

    }

}
