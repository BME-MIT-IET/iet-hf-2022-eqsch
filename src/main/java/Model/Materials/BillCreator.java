package Model.Materials;

import Model.Map;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Creates bills for the crafting and building.
 */
public class BillCreator {
    /**
     * The instance of the singleton BillCreator class.
     */
    private static BillCreator instance=null;

    private BillCreator(){}

    /**
     * @return Singleton class. Returns the only instance of BillCreator that exists.
     */
    public static BillCreator GetInstance(){
        if(instance==null){
            instance = new BillCreator();
        }
        return instance;
    }

    /**
     * Counts a specific material in a gived inventory
     * @param inventory the inventory, where it counts the given material
     * @param comparator the material we want to count in the inventory
     * @return with the number of the given material type in the inventory
     */
    public int Count(ArrayList<Material> inventory, Material comparator){
        int count = 0;
        for(Material it: inventory){
            if(comparator.isSameType(it)){
                count++;
            }
        }
        return count;
    }

    /**
     * Compares all materials in an inventory to a given reference material, and if they are of the same type adds them to the given BillOfMaterial.
     * @param inventory The list of materials where we search for the needed material.
     * @param Comparator The material what we are looking for.
     * @param bill The BillOfMaterial, where we add the matching material if needed.
     * @param Needed How many materials we need for the bill.
     * @return True, if there was enough materials in the given inventory, and false if not.
     */
    public boolean CountAndAdd(ArrayList<Material> inventory,Material Comparator,BillOfMaterial bill,int Needed){
        int Count=0;
        for(Material it : inventory){
            if(Comparator.isSameType(it)){
                if(Count < Needed)
                    bill.Add(it);
                Count++;
            }
        }
        return Count>=Needed;
    }

    /**
     * Prepares a BillOfMaterial for teleport gates.
     * @param inventory The list of materials where we search for the needed ones.
     * @return The bill, if possible, null if there is not enough materials in the inventory.
     */
    public BillOfMaterial CreateForTeleport(ArrayList<Material> inventory){
        BillOfMaterial bill = new BillOfMaterial();
        if(!CountAndAdd(inventory,new Uranium(new Map()),bill,1) || !CountAndAdd(inventory,new Ice(new Map()),bill,1) || !CountAndAdd(inventory,new Iron(new Map()),bill,2)){
            return null;
        }
        return bill;
    }

    /**
     * It's for searching an exact material in a player inventory
     * @param inventory where we want to search
     * @param Comparator what we search
     * @return the UID of the searched material
     */
    public int Search(ArrayList<Material> inventory,Material Comparator){
        int Count=0;
        for(Material it : inventory){
            if(Comparator.isSameType(it)){
                return it.GetUID();
            }
        }
        return -1;
    }

    /**
     * It's for searching coal in a player inventory
     * @param inventory where we want to search coal
     * @return
     */
    public int SearchCoal(ArrayList<Material> inventory){
       return Search(inventory,new Coal(new Map()));
    }

    /**
     * It's for searching iron in a player inventory
     * @param inventory where we want to search iron
     * @return
     */
    public int SearchIron(ArrayList<Material> inventory){
        return Search(inventory,new Iron(new Map()));
    }

    /**
     * It's for searching ice in a player inventory
     * @param inventory where we want to search ice
     * @return
     */
    public int SearchIce(ArrayList<Material> inventory){
        return Search(inventory,new Ice(new Map()));
    }

    /**
     * It's for searching uranium in a player inventory
     * @param inventory where we want to search uranium
     * @return
     */
    public int SearchUranium(ArrayList<Material> inventory){
        return Search(inventory,new Uranium(new Map()));
    }

    /**
     * Prepares a BillOfMaterial for a base foundation.
     * @param inventory The list of materials where we search for the needed ones.
     * @return The bill, if possible, null if there is not enough materials in the inventory.
     */
    public BillOfMaterial CreateForBaseFoundation(ArrayList<Material> inventory){
        BillOfMaterial bill = new BillOfMaterial();
        if(!CountAndAdd(inventory,new Iron(new Map()),bill,3)){
            return null;
        }
        return bill;
    }

    /**
     * Prepares a BillOfMaterial for a robot.
     * @param inventory The list of materials where we search for the needed ones.
     * @return The bill, if possible, null if there is not enough materials in the inventory.
     */
    public BillOfMaterial CreateForRobot(ArrayList<Material> inventory){
        BillOfMaterial bill = new BillOfMaterial();
        if(!CountAndAdd(inventory,new Uranium(new Map()),bill,1) || !CountAndAdd(inventory,new Coal(new Map()),bill,1)  || !CountAndAdd(inventory,new Iron(new Map()),bill,1)){
            return null;
        }
        return bill;
    }

    /**
     * Prepares a BillOfMaterial for a completed base.
     * @param inventory The list of mterials where we search for the needed ones.
     * @return The bill, if possible, null if there is not enough materials in the inventory.
     */
    public BillOfMaterial CreateForBase(ArrayList<Material> inventory){
        BillOfMaterial bill = new BillOfMaterial();
        if(!CountAndAdd(inventory,new Uranium(new Map()),bill,3) || !CountAndAdd(inventory,new Coal(new Map()),bill,3) || !CountAndAdd(inventory,new Iron(new Map()),bill,3) || !CountAndAdd(inventory,new Ice(new Map()),bill,3)){
            return null;
        }
        return bill;
    }
}