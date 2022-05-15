package Model.Materials;

import java.io.PrintStream;
import java.lang.String;
import java.util.ArrayList;
import Controllers.FileController;
import Model.Asteroid;
import Model.Map;
import Model.Saveable;
import Model.IVisitable;
import Utils.StringPair;

import javax.management.RuntimeErrorException;

/**
 * The parent class of all the materials.
 */
public abstract class Material extends Saveable implements IVisitable {

    Material(Map m){
        super(m);
    }

    Material(int uid){
        super(uid);
    }

    /**
     * Checks if the material given as parameter is same type.
     * @param m The material, which we want to compare to.
     * @return True, if they are the same type and false if not.
     */
    public boolean isSameType(Material m){
        return m.GetTypeUnique().equals(this.GetTypeUnique());
    }

    /**
     * Tives back type in string.
     * @return With the type of the material.
     */
    protected abstract String GetTypeUnique();

    /**
     * Special action if the sun is close.
     * @param asteroid The asteroid where it happened.
     */
    public void DrilledThroughSunClose(Asteroid asteroid){}

    /**
     * Called if someone mined the material from an asteroid.
     */
    public void PickedUp(){}

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws RuntimeErrorException
     */
    @Override
    public void Link(ArrayList<StringPair> args, FileController fc) throws RuntimeErrorException {

    }

    /**
     * The save method for the Material class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("UID: " + GetUID());
    }
}
