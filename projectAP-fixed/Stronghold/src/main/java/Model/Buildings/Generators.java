package Model.Buildings;

import Model.Buildings.Enums.GeneratorTypes;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Generators extends Building{
    private GeneratorTypes type ;
    private int useRate;
    private int produceRate;
    private int inventory;
    private int capacity;
    private Resources product;
    private Resources use;
    private int wood;
    private int gold;
    private int worker;

    private boolean isInFire;

    public Generators(Player owner, Tile position, GeneratorTypes type) {
        super(owner, position);
        this.useRate = type.getUseRate();
        this.produceRate = type.getProduceRate();
        this.HP = type.getHP();
        this.inventory = type.getInventory();
        this.capacity = type.getCapacity();
        this.product = type.getProduct();
        this.use = type.getUse();
        this.wood = type.getWood();
        this.gold = type.getGold();
        this.worker = type.getWorker();
        owner.decreaseInventory(Resources.WOOD,type.getWood());
        owner.decreaseGold(type.getGold());
        /////////////////////////////////////////////////////////worker ro bayad chikar kard?
    }

    public GeneratorTypes getType() {
        return type;
    }

    public boolean isInFire() {
        return isInFire;
    }

    public int getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public Resources getProduct() {
        return product;
    }

    public Resources getUse() {
        return use;
    }

    public void makeResource(){
        // TODO add details for special cases
        if(use!=null && useRate>owner.getResourceAmount(use)){
            return;
        }
        if(use!=null){
            owner.decreaseInventory(use,useRate);
        }
        if(product!=null){
            owner.increaseInventory(product,produceRate);
        }
    }

    @Override
    public void check() {
        if(shouldBreak()){
            return;
        }
        makeResource();
    }

    @Override
    public void print() {

    }
}
