package Model;

import Controllers.FileController;
import Utils.LinkerException;
import Utils.StringPair;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * It represents the ships of the game.
 */
public abstract class Ship extends Saveable implements IVisitable {
    /**
     * The asteroid where the ship is.
     */
    protected Asteroid asteroid;

    public Ship(Asteroid a){
        super(a.sector.map);
        asteroid = a;
        asteroid.Add(this);
    }

    public Ship(Map map){
        super(map);
    }

    public Ship(int uid) {
        super(uid);
    }

    /**
     * Moves the ship to the given field.
     * @param f The field where we want to move the ship.
     */
    public void Move(Field f){
        asteroid.Remove(this);
        Asteroid dest = f.MovedTo();
        dest.Add(this);
        setAsteroid(dest);
    }

    /**
     * Abstract method for the ships when they die.
     */
    public abstract void Die();

    /**
     * Hides the ship. Not implemented yet.
     */
    public void Hide(){
        //System.out.println("The ship survives.");
    }

    /**
     *  The ship gets into a sun storm.
     */
    public void SunStormNow(){
        // Whether ship can hide in empty asteroid or dies.
        if(asteroid.GetShell() == 0){
            if(asteroid.GetCore() == null){
                Hide();
            }
            else{
                Die();
            }
        }
        else{
            Die();
        }
    }

    /**
     *  Special action if the asteroid explodes.
     */
    public abstract void AsteroidExploding();


    /**
     * The getter of the ship's asteroid.
     * @return With the ship's current asteroid.
     */
    public Asteroid getAsteroid(){
        return asteroid;
    }

    /**
     * The setter of the ship's asteroid.
     * @param a The asteroid we want to set for the ship.
     */
    public void setAsteroid(Asteroid a){
        asteroid = a;
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
            if(it.first.equals("Asteroid")){
                asteroid = (Asteroid) fc.GetWithUID(Integer.parseInt(it.second));
            }
        }
    }

    /**
     * The save method for the Ship class.
     * @param os the stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("UID: " + GetUID());
        if(asteroid!=null) {
            os.println("Asteroid: " + asteroid.GetUID());
        }
    }
}
