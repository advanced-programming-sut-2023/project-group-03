package Model1.Model;

import Model1.Model.Buildings.Building;
import Model1.Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

class Field
{
    private Texture texture;
    private Building building;
    private ArrayList<Unit> units;
    //private Resource resource;
    private HashMap<String,Field> neighbours;

    
    public boolean addBuilding(Building bulding)
    {
        return false;
    }    
    
    
    public void removeBuilding()
    {
        
    }    
    
    
    public boolean addUnit(Unit unit)
    {
        return false;
    }    
    
    
    public final boolean removeUnit(Unit unit)
    {
        return true;
    }    
}
