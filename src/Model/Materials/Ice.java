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

/**
 * Represents the ice material in the game.
 */
public class Ice extends Material {

    public Ice(Map m){super(m);}

    public Ice(int uid) {super(uid);}

    /**
     * Gives back type in string.
     * @return With the type of the material, in this case "Ice".
     */
    protected  String GetTypeUnique(){
        return "Ice";
    }

    /**
     * Special action if the sun is close and the asteroid's shell becomes 0.
     * In Ice case, it is evaporating.
     * @param asteroid The asteroid where the drilling happened.
     */
    @Override
    public void DrilledThroughSunClose(Asteroid asteroid){
        asteroid.Evaporate();
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
    }

    /**
     * The save method for the Ice class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("Ice{");
        super.Save(os, CallChildren);
        os.println("}");
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
