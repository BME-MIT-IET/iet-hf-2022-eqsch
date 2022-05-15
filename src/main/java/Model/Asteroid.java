package Model;

import Controllers.FileController;
import Controllers.NotificationManager;
import Model.Materials.Material;
import Utils.LinkerException;
import Utils.StringPair;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * It bases from Field.
 * Represents the asteroid in the game.
 */
public class Asteroid extends Field {
    /**
     * The ships on the asteroid.
     */
    private ArrayList<Ship> ships;
    /**
     * A list of the removable ships for the iterations.
     */
    private ArrayList<Ship> Removables;
    /**
     * The material in the asteroid.
     */
    private Material core;
    /**
     * The base on the asteroid, if there is no base, it's null.
     */
    private Base base;
    /**
     * The shell of the asteroid, ships can only mine if it's 0.
     */
    private int shell;
    /**
     * True, if the asteroid was hit by a sun storm recently, false if not.
     */
    private boolean SunStorm = false;


    public Asteroid(Sector s){
        super(s);
        ships = new ArrayList<>();
        Removables = new ArrayList<>();
        base=null;
        shell = new Random().nextInt(6)+4;
        x=0;
        y=0;
    }
    public Asteroid(Sector s,int Shell){
        super(s);
        ships = new ArrayList<>();
        Removables = new ArrayList<>();
        base=null;
        shell = Shell;
        x=0;
        y=0;
    }
    public Asteroid(Sector s,Material Core,int Shell){
        super(s);
        ships = new ArrayList<>();
        Removables = new ArrayList<>();
        core = Core;
        base=null;
        shell = Shell;
        x=0;
        y=0;
    }
    public Asteroid(int UID, Sector s, Material _core, int _shell) {
        super(UID, s);
        ships = new ArrayList<>();
        Removables = new ArrayList<>();
        core = _core;
        base = null;
        shell = _shell;
        x=0;
        y=0;
    }
    public Asteroid(int UID) {
        super(UID);
        ships = new ArrayList<>();
        Removables = new ArrayList<>();
        base=null;
        shell = new Random().nextInt(6)+4;
        x=0;
        y=0;
    }

    /**
     * The setter of the shell.
     * @param Shell The value we want to set for the asteroid's shell.
     */
    public void SetShell(int Shell){
        shell = Shell;
    }

    /**
     * The getter of the shell.
     * @return With the value of the shell.
     */
    public int GetShell(){
        return shell;
    }

    /**
     * The setter of the base.
     * @param b The base we want to set for the asteroid's base.
     */
    public void SetBase(Base b){
        base=b;
    }

    /**
     * The getter of the base.
     * @return With the base on the asteroid.
     */
    public Base GetBase(){return base;}

    /**
     * The setter of the core.
     * @param m The material we want to set for the asteroid's core.
     * @return True, if the set was successful and false if not.
     */
    public boolean SetCore(Material m){
        if(shell != 0){
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Asteroid is not drilled through, can't put back material.");
        }
        else if(core != null){
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Asteroid is empty, can't put back material.");
        }
        if(shell == 0 && core == null){
            core = m;
            if(this.getSunClose()){
                core.DrilledThroughSunClose(this);
            }
            return true;
        }
        return false;
    }

    /**
     * The getter of the core.
     * @return With the asteroid's core.
     */
    public Material GetCore(){
        return core;
    }

    /**
     * itt bizony feketemágia történik.
     * @param s
     */
    public void Remove(Ship s){
        if(!SunStorm)
            ships.remove(s);
        else
            Removables.add(s);
    }

    /**
     * Adds a ship to the asteroid.
     * @param s The ship we want to be added to the asteroid.
     */
    public void Add(Ship s){
        if(!ships.contains(s)) ships.add(s);
    }

    /**
     * The method which handles if a ship drills the asteroid.
     */
    public boolean GetDrilled(){
        if(shell<=0){
            return false;
        }
        shell--;
        // if shell size is zero then checks whether it is in sun close area
        if(shell==0){
            if (this.getSunClose()) {
                core.DrilledThroughSunClose(this);
            }
        }
        return true;
    }

    /**
     * The method which handles if a ship mines on the asteroid.
     * @return With the core if the mining was successful and false if not.
     */
    public Material GetMined(){
        // if shell size is not zero cannot get mined
        if(shell > 0){
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Asteroid shell isn't drilled through, can't mine");
            return null;
        }
        if(core == null){
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Asteroid is empty, can't mine");
            return null;
        }
        Material ret = core;
        core = null;
        ret.PickedUp();
        return ret;
    }

    public ArrayList<Ship> getShips(){
        return ships;
    }

    /**
     * Called when a ship moves to the asteroid.
     * @return With the asteroid.
     */
    public Asteroid MovedTo(){
        return this;
    }

    /**
     * The asteroid reacts to a sunstorm.
     */
    @Override
    public void SunStorm() {
        SunStorm = true;
        for(Ship it : ships){
            it.SunStormNow();
        }
        for(Ship it : Removables){
            ships.remove(it);
        }
        SunStorm = false;
    }

    /**
     * The asteroid explodes.
     */
    public void Explode(){
        // calls for each ship exploding action
        for (int i=0;i<ships.size();i++) {
            for (Ship ship : ships) {
                ship.AsteroidExploding();
                break;
            }
        }
        ships.clear();
        // Removes asteroid from all neighbours.
        for(Field f : Neighbours){
            f.RemoveNeighbour(this);
        }
        sector.Remove(this);
        Neighbours.clear();
        core.PickedUp();
        core = null;
        NotificationManager.AddExplodedAsteroid(this);
        NotificationManager.AddWarning("Asteroid" + GetUID() + " exploded");
    }

    /**
     * Evaporates the core material.
     */
    public void Evaporate(){
        core = null;
        NotificationManager.AddWarning("Ice from Asteroid" + GetUID() + " evaporated");
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
            if(it.first.equals("Ships")){
                String[] ids = it.second.split(",");
                for(String idIt : ids){
                    ships.add((Ship) fc.GetWithUID(Integer.parseInt(idIt)));
                }
            }
            else if(it.first.equals("Core")){
                core = (Material) fc.GetWithUID(Integer.parseInt(it.second));
            }
            else if(it.first.equals("Shell")){
                shell = Integer.parseInt(it.second);
            }
            else if(it.first.equals("Base")){
                base = (Base) fc.GetWithUID(Integer.parseInt(it.second));
            }
        }
    }

    /**
     * The save method for the Asteroid class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("Asteroid{");
        super.Save(os, CallChildren);
        os.println("Shell: " + shell);
        if(ships.size()>0) {
            ships.sort(new Comparator<Ship>() {
                @Override
                public int compare(Ship o1, Ship o2) {
                    return o1.GetUID()-o2.GetUID();
                }
            });
            os.print("Ships: ");
            for (Ship it : ships) {
                os.print(it.GetUID());
                if (it != ships.get(ships.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
        }
        if(base!=null)
            os.println("Base:" + base.GetUID());
        if(core!=null)
            os.println("Core:" + core.GetUID());
        os.println("}");
        if(CallChildren) {
            if (core != null)
                core.Save(os, CallChildren);
            if (base != null)
                base.Save(os, CallChildren);
            for (Ship s : ships) {
                s.Save(os, CallChildren);
            }
        }
    }

    /**
     * Distance between an Asteroid and coordinates
     * @param a1 an Asteroid
     * @param x coordinate x axle
     * @param y coordinate y axle
     * @return with a double what is the distance between the coordinates
     */
    public double distance(Asteroid a1, double x, double y){
        return Math.sqrt(Math.pow(a1.getX()-x,2) + Math.pow(a1.getY()-y,2));
    }

    /**
     * The method which handles if the Asteroid is close to the Sun
     * @return true if close, false if not
     */
    public boolean getSunClose(){
        if(distance(this,0.0,0.0)<0.6){
            return true;
        }
        return false;
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
