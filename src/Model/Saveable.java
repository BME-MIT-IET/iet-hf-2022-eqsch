package Model;

import Controllers.FileController;
import Utils.LinkerException;
import Utils.StringPair;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * It's an abstract class. The derived classes can be saved into the output files.
 */
public abstract class Saveable {
    /**
     * The unique ID for the objets.
     */
    final int UID;

    /**
     * @param map
     */
    public Saveable(Map map){
        UID = map.GetNewUID();
    }
    public Saveable(int uid) {
        UID = uid;
    }

    /**
     * The getter of the UID.
     * @return With the UID.
     */
    public int GetUID(){
        return UID;
    }

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws LinkerException
     */
    public abstract void Link(ArrayList<StringPair> args, FileController fc) throws LinkerException;

    /**
     * The abstract save method, different for each saveable class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    public abstract void Save(PrintStream os, boolean CallChildren);
}