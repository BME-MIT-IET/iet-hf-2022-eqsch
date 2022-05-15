package Model;

import Controllers.FileController;
import Controllers.NotificationManager;
import Model.Materials.BillCreator;
import Model.Materials.BillOfMaterial;
import Model.Materials.Material;
import Utils.LinkerException;
import Utils.StringPair;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents the base in the game.
 */
public class Base extends Saveable implements IVisitable{
    /**
     * The materials, that are in the base.
     */
    ArrayList<Material> materials = new ArrayList<>();

    public Base(int uid) {
        super(uid);
    }

    public ArrayList<Material> getMaterials(){
        return materials;
    }

    /**
     * Handles if the base will accept an offered material.
     * Returns true if it added the material, false if not.
     * @param m The material the base accepts or not.
     * @return True if the base accepted the material and false if not.
     */
    public boolean Accept(Material m){
        int count=0; // Counts the number of materials that are the same type and are already stored.
        boolean added=false; // Stores whether or not the material was added.

        for(Material it : materials){
            if(it.isSameType(m)) // Checking if they are the same type.
                count++; // Counting if yes.
        }
        if(count<3){
            Add(m);
            added = true;
            //CheckComplete(); //Checking if the base is finished with this material;
        }
        return added;
    }

    /**
     * Checks whether or not the base has enough resources to be considered complete.
     */
    public boolean CheckComplete() {
        BillCreator bc = BillCreator.GetInstance();
        BillOfMaterial Bill = bc.CreateForBase(materials); // Creating a bill to see if the base is complete
        if(Bill != null){ // If the bill is not null than the base has enough materials
            NotificationManager.PlayersWon();
            return true;
        }
        return false;
    }

    /**
     * Adds the argument material to the stored materials of the base.
     * @param material The material we want to add to the base.
     */
    private void Add(Material material){
        materials.add(material); // Adding material.
    }

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws LinkerException
     */
    @Override
    public void Link(ArrayList<StringPair> args, FileController fc) throws LinkerException {
        for(StringPair it : args) {
            if(it.first.equals("Materials")){
                String[] ids = it.second.split(",");
                for(String idIt : ids){
                    materials.add((Material)fc.GetWithUID(Integer.parseInt(idIt)));
                }
            }
        }
    }

    /**
     * The save method for the Base class.
     * @param os the stream, where the class will be written .
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("Base{");
        os.println("UID: " + GetUID());
        if(materials.size()>0) {
            os.print("\tMaterials: ");
            materials.sort(new Comparator<Material>() {
                @Override
                public int compare(Material o1, Material o2) {
                    return o1.GetUID()-o2.GetUID();
                }
            });
            for (Material it : materials) {
                os.print(it.GetUID());
                if (it != materials.get(materials.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
        }
        os.println("}");
        if(CallChildren) {
            for (Material it : materials) {
                it.Save(os, CallChildren);
            }
        }
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
