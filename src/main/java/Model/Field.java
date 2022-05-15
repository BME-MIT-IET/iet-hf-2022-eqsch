package Model;

import Controllers.FileController;
import Utils.LinkerException;
import Utils.StringPair;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents the fields in the game.
 */
public abstract class Field extends Saveable implements IVisitable{
    /**
     * The neighbours (fields) of the field.
     */
    ArrayList<Field> Neighbours = new ArrayList<>();

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Sector getSector() {
        return sector;
    }

    /**
     * The sector where the field is.
     */
    Sector sector;
    double x;

    double y;


    //Getter of the x coordinate
    public double getX(){
        return x;
    }
    //Getter of the y coordinate
    public double getY(){
        return y;
    }
    //Setter of the x coordinate
    public void setX(double X){
        x=X;
    }
    //Setter of the y coordinate
    public void setY(double Y){
        y=Y;
    }

    Field(Map m){
        super(m);
    }

    Field(Sector s){
        super(s.map);
        sector = s;
        Neighbours = new ArrayList<>();
    }
    public Field(int UID) {
        super(UID);
        Neighbours = new ArrayList<>();
    }
    public Field(int UID, Sector s) {
        super(UID);
        sector = s;
        Neighbours = new ArrayList<>();
    }

    /**
     * Called when a ship moves to a field.
     * @return With the destination asteroid.
     */
    public abstract Asteroid MovedTo();

    /**
     * Removes a neighbour.
     * @param f The field we want to be removed from neighbours.
     */
    public void RemoveNeighbour(Field f){
        Neighbours.remove(f);
    }

    /**
     * Adds a neighbour.
     * @param f The field we want to be added to the neighbour.
     */
    public void AddNeighbour(Field f){
        if(!Neighbours.contains(f)) Neighbours.add(f);
    }

    // Getter for the neighbours
    public ArrayList<Field> getNeighbours() {
        return Neighbours;
    }

    /**
     * Called when a SunStorm happened, a specific field reacts differently.
     */
    public abstract void SunStorm();

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws LinkerException
     */
    @Override
    public void Link(ArrayList<StringPair> args, FileController fc) throws LinkerException {
        for(StringPair it : args) {
            if(it.first.equals("Neighbours")){
                String[] ids = it.second.split(",");
                for(String idIt : ids){
                    Neighbours.add((Field)fc.GetWithUID(Integer.parseInt(idIt)));
                }
            }
            else if(it.first.equals("Sector")){
                sector = (Sector) fc.GetWithUID(Integer.parseInt(it.second));
            }
            else if(it.first.equals("X")){
                x = Double.parseDouble(it.second);
            }
            else if(it.first.equals("Y")){
                y = Double.parseDouble(it.second);
            }
        }
    }

    /**
     * The save method for the Field class.
     * @param os The stream, where the class will be written.
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("UID: " + GetUID());
        os.println("X: " + x);
        os.println("Y: " + y);
        if(sector!=null) {
            os.println("Sector: " + sector.GetUID());
        }
        if(Neighbours.size()>0) {
            os.print("Neighbours: ");
            Neighbours.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.GetUID()-o2.GetUID();
                }
            });
            for (Field it : Neighbours) {
                os.print(it.GetUID());
                if (it != Neighbours.get(Neighbours.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
        }
    }
}
