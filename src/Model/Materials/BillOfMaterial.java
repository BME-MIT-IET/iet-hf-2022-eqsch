package Model.Materials;

import java.util.ArrayList;

/**
 * Represents the bills which are needed for the crafting and building.
 */
public class BillOfMaterial {
    /**
     * The materials in the bill.
     */
    ArrayList<Material> materials = new ArrayList<>();

    /**
     * Adds the argument material to the stored materials of the bill.
     * @param m The material, which we want to add to the bill.
     */
    public void Add(Material m){
        materials.add(m);
    }

    /**
     * Basically a getter for the bill's materials.
     * @return With the materials of the bill.
     */
    public final ArrayList<Material> GetMaterials(){
        return materials;
    }
}