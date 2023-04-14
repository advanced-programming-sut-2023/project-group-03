package Model1.Model.Buildings;


import Model1.Model.Buildings.Enums.GenaratorTypes;
import Model1.Model.Buildings.Enums.Resources;
import Model1.Model.User;

class ResourceGenerator extends Building
{
     GenaratorTypes type ;
     public boolean isInfire;
     int rateProduct;		
     int rateUse;
     int hp;
     int Inventory;		
     int Capacity;
     Resources product;
     Resources Use;

     public ResourceGenerator(int xPos, int yPos, User owner,GenaratorTypes type) {
          super(xPos, yPos, owner);
          this.type=type;
     }


     @Override
     public void getHit() {

     }

     @Override
     public void action() {

     }

     @Override
     public void print() {

     }
}
