package Model;

import Controllers.FileController;
import Controllers.MapBuilder;
import Controllers.NotificationManager;
import UI.Components.MagicConstants;
import Utils.LinkerException;
import Utils.StringPair;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the teleports in the game.
 */
public class TeleportGate extends Field{
    /**
     * The pair of the teleport.
     */
    private TeleportGate pair;
    /**
     * Stores if the teleport is crazy or not.
     */
    private boolean WashHitByStorm;


    TeleportGate(Sector s){
        super(s);
        WashHitByStorm = false;
        pair = null;
    }

    public TeleportGate(int UID){
        super(UID);
        pair = null;
    }

    public void SetWashHitByStorm(boolean washHitByStorm){
        WashHitByStorm = washHitByStorm;
    }

    //getter for the Teleportgate hit by storm
    public boolean getWasHitByStorm(){return WashHitByStorm;}

    //getter for the Teleportgate pair
    public TeleportGate getPair(){return pair;}

    /**
     * The setter for the teleport gate's sector.
     * @param s The sector, we want to set for the teleport gate.
     */
    public void SetSector(Sector s){
        sector = s;
    }

    /**
     * Called when a ship moves to a teleport gate.
     * @return With the neighbour of the teleport gate's pair if it's possible(active)
     * and with the teleport gate's neighbour if not.
     */
    public Asteroid MovedTo(){
        if(!isActive()) {
            return Neighbours.get(0).MovedTo();
        }
        Asteroid ret = pair.Neighbours.get(0).MovedTo();
        return ret;
    }

    /**
     * Check whether the teleport gate is active or not.
     * @return True if it's active (it's pair is placed and has a neighbour), false if not.
     */
    public boolean isActive(){
        return pair != null && pair.Neighbours.size() != 0;
    }

    /**
     * Sets the pair of teleport gate.
     * @param t The teleport gate, which we want to pair the teleport.
     */
    void pair(TeleportGate t){
        pair = t;
        t.pair = this;
    }

    /**
     * If the teleport gate was hit by a sun storm, it starts to move and this is it's method to do so.
     * @param f The field where the teleport gate tries to move.
     */
    public void Move(Field f){
        Asteroid dest = f.MovedTo();
        Neighbours.get(0).RemoveNeighbour(this);
        Neighbours.remove(Neighbours.get(0));
        Neighbours.add(dest);
        dest.AddNeighbour(this);
        Reposition();
        NotificationManager.AddMessage("Teleport" + GetUID() + " moved to Asteroid" + f.GetUID() + ".");
    }

    /**
     * Called every turn, if the teleport was hit by a sunstorm, it randomly moves (with some chance).
     */
    public void TurnOver(){
        Random rand = new Random();
        if(WashHitByStorm && rand.nextInt(3)<2){
            ArrayList<Field> possibilities = Neighbours.get(0).getNeighbours();
            if(possibilities.size()>1) {
                Field dest = null;
                while (dest == this || dest == null)
                    dest = possibilities.get(rand.nextInt(possibilities.size()));
                Move(dest);
            }
        }
    }

    /**
     * The method for the crazy Teleportgate reposition
     */
    public void Reposition(){
        Random r = new Random();
        ArrayList<Field> AllFields = new ArrayList<>();
        Field asteroid = getNeighbours().get(0);
        for(Sector s : asteroid.getSector().getMap().getSectors()){
            AllFields.addAll(s.getFields());
        }
        do{
            double alfa = r.nextDouble() * 2.0 * Math.PI;
            double dist = r.nextDouble() * (MagicConstants.neighbourMaxDistance - MagicConstants.asteroidTooClose) + MagicConstants.asteroidTooClose;
            setX(asteroid.x+Math.cos(alfa)*dist);
            setY(asteroid.y+Math.sin(alfa)*dist);
        }while(MapBuilder.tooClose(AllFields,this) || MapBuilder.tooCloseToSun(this));
    }

    @Override
    public  String toString(){
        return "TeleportGate";
    }

    /**
     * Removes a neighbour.
     * @param f The field we want to be removed from neighbours.
     */
    @Override
    public void RemoveNeighbour(Field f){
        Neighbours.remove(f);
        sector.Remove(this);
    }

    /**
     * The teleport reacts to a sunstorm (the teleport goes crazy).
     */
    @Override
    public void SunStorm() {
        WashHitByStorm = true;
        NotificationManager.AddMessage("Teleport" + GetUID() + " was hit by sunstorm.");
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
            if(it.first.equals("WasHitBySunStorm")){
                WashHitByStorm = Boolean.parseBoolean(it.second);
            }
            else if(it.first.equals("Pair")){
                pair = (TeleportGate) fc.GetWithUID(Integer.parseInt(it.second));
            }
        }
    }

    /**
     * The save method for the TeleportGate class.
     * @param os The stream, where the class will be written.
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("TeleportGate{");
        super.Save(os, CallChildren);
        os.println("WasHitBySunStorm: " + WashHitByStorm);
        if(pair!=null)
            os.println("Pair:" + pair.GetUID());
        os.println("}");
    }

    /**
     * The method for the distance between a teleportgate and coordinates
     * @param a1 a Teleportgate
     * @param x coordinate x axle
     * @param y coordinate y axle
     * @return double distance between the teleportgate and the coordinates
     */
    public double distance(TeleportGate a1, double x, double y){
        return Math.sqrt(Math.pow(a1.getX()-x,2) + Math.pow(a1.getY()-y,2));
    }

   //Teleportgate is close to the SUn or not
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
