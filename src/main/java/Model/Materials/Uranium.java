package Model.Materials;
import Controllers.FileController;
import Model.Asteroid;
import Model.Map;
import Model.IVisitor;
import Utils.StringPair;

import javax.management.RuntimeErrorException;
import java.io.PrintStream;
import java.lang.String;
import java.util.ArrayList;

public class Uranium extends Material{
    int ExposedFor;
    boolean isExposed;
    Asteroid LastAsteroid;

    public Uranium(Map m){
        super(m);
    }

    public Uranium(int uid) {super(uid);}

    /**
     * Gives back type in string.
     * @return With the type of the material, in this case "Uranium".
     */
    protected String GetTypeUnique(){
        return "Uranium";
    }

    /**
     * Special action if the sun is close and the asteroid's shell becomes 0.
     * In Uranium case it is exploding.
     * @param asteroid The asteroid where the drilling happened.
     */
    @Override
    public void DrilledThroughSunClose(Asteroid asteroid){
        LastAsteroid = asteroid;
        isExposed = true;
    }
    @Override
    public String toString(){
        return "Uranium";
    }

    /**
     * Called when the Uranium has been picked up, and sets it's attributes accordingly.
     */
    @Override
    public void PickedUp(){
        isExposed = false;
        LastAsteroid = null;
    }

    /**
     * The Uranium's method which is called in each turn, it handles if the uranium is about to explode or not.
     */
    public void TurnOver(){
        if(isExposed){
            ExposedFor++;
            if(ExposedFor>=3){
                LastAsteroid.Explode();
            }
        }
    }

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws RuntimeErrorException
     */
    @Override
    public void Link(ArrayList<StringPair> args, FileController fc) throws RuntimeErrorException {
        super.Link(args,fc);
        for(StringPair it : args) {
            if(it.first.equals("ExposedFor")){
                ExposedFor = Integer.parseInt(it.second);
            }
            else if(it.first.equals("isExposed")){
                isExposed = Boolean.parseBoolean(it.second);
            }
        }
    }

    /**
     * The save method for the Uranium class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("Uranium{");
        super.Save(os, CallChildren);
        os.println("ExposedFor: " + ExposedFor);
        os.println("isExposed: " + isExposed);
        os.println("}");
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
