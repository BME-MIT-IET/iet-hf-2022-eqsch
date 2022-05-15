package Model;

import Controllers.FileController;
import Controllers.NotificationManager;
import Model.Materials.Material;
import Utils.LinkerException;
import Utils.StringPair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * It represents the UFOs of the game.
 * It's controlled by the AIController.
 */
public class UFO extends Ship{
    /**
     * The UFO's materials.
     */
    ArrayList<Material> materials;

    public UFO(Asteroid a){
        super(a);
        materials = new ArrayList<>();
    }

    public UFO(int uid){
        super(uid);
        materials = new ArrayList<>();
    }

    /**
     * UFO dies.
     */
    @Override
    public void Die() {
        asteroid.Remove(this);
        asteroid = null;
        NotificationManager.AddMessage("UFO" + GetUID() + " died");
    }

    /**
     * Special action if the asteroid explodes the UFO dies.
     */
    @Override
    public void AsteroidExploding() {
        Die();
    }

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws LinkerException
     */
    @Override
    public void Link(ArrayList<StringPair> args, FileController fc) throws LinkerException {
        super.Link(args,fc);
        for(StringPair it : args) {
            if(it.first.equals("Materials")){
                String[] ids = it.second.split(",");
                for (String idIt : ids) {
                    materials.add((Material) fc.GetWithUID(Integer.getInteger(idIt)));
                }
            }
        }
    }

    /**
     * The save method for the UFO class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("UFO{");
        super.Save(os, CallChildren);
        if(materials.size()>0) {
            os.print("Materials: ");
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
            for (Material s : materials) {
                s.Save(os, CallChildren);
            }
        }
    }

    /**
     * The UFO mines asteroid's core material.
     */
    public void Mine(){
        // only mines if player ship has 9 material or less
        if(materials.size() < 10) {
            Material core;
            core = asteroid.GetMined();
            // only adds if asteroid is not empty
            if(core != null){
                materials.add(core);
                NotificationManager.AddMessage("UFO" + GetUID() + " mined Asteroid" + asteroid.GetUID());
            }
        }
    }

    /**
     * UFO moves to an asteroid
     * @param f The field where the UFO will move.
     */
    @Override
    public void Move(Field f){
        Asteroid a = asteroid;
        super.Move(f);
        NotificationManager.AddMessage("UFO" + GetUID() + " moved to Asteroid" + asteroid.GetUID());
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
