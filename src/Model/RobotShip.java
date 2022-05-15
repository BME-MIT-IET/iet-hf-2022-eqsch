package Model;

import Controllers.FileController;
import Controllers.NotificationManager;
import Utils.LinkerException;
import Utils.StringPair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * It represents the robots, the controller is the AIController
 * It bases from the Ship class.
 */
public class RobotShip extends Ship {

    public RobotShip(Asteroid a){
        super(a);
    }

    public RobotShip(int uid){
        super(uid);
    }

    /**
     * In case of asteroid exploding robot ship flies over to a close asteroid
     */
    public void AsteroidExploding(){
        ArrayList<Field> neighbours = asteroid.getNeighbours();
        Move(neighbours.get(new Random().nextInt(neighbours.size())));
    }

    /**
     * Robot ship dies.
     */
    public void Die(){
        asteroid.Remove(this);
        NotificationManager.AddMessage("Robot" + GetUID() + " died");
        asteroid = null;
    }

    /**
     * Robot ship drills.
     */
    public void Drill(){
        asteroid.GetDrilled();
        NotificationManager.AddMessage("Robot" + GetUID() + " drilled Asteroid" + asteroid.GetUID());
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
    }

    /**
     * The save method for the RobotShip class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("RobotShip{");
        super.Save(os, CallChildren);
        os.println("}");
    }

    /**
     * The move method for the RobotShip class.
     * @param f The field where we want to move the ship.
     */
    @Override
    public void Move(Field f){
        Asteroid a = asteroid;
        super.Move(f);
        NotificationManager.AddMessage("Robot" + GetUID() + " moved to Asteroid" + asteroid.GetUID());
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
