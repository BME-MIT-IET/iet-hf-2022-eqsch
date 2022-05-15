package Model.Materials;

import Controllers.FileController;
import Model.Map;
import Model.IVisitor;
import Utils.StringPair;
import javax.management.RuntimeErrorException;
import java.io.PrintStream;
import java.lang.String;
import java.util.ArrayList;

/**
 * Represents the iron material in the game.
 */
public class Iron extends Material {

    public Iron(Map m){
        super(m);
    }

    public Iron(int uid) {
        super(uid);
    }

    /**
     * Gives back type in string.
     * @return With the type of the material, in this case "Iron".
     */
    protected  String GetTypeUnique(){
        return "Iron";
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
     * The save method for the Iron class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("Iron{");
        super.Save(os, CallChildren);
        os.println("}");
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
