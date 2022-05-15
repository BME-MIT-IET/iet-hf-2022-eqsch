package Model;

import Controllers.FileController;
import Utils.LinkerException;
import Utils.StringPair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * It represents the sectors of the game.
 */
public class Sector extends Saveable{
    /**
     * The fields of the sector.
     */
    private ArrayList<Field> fields;
    /**
     * True, if the sector is close to the sun and false if not.
     */
    private boolean SunClose;
    /**
     * The map where the sector is.
     */
    Map map = null;

    public Sector(int UID,Map m) {
        super(UID);
        SunClose = false;
        fields = new ArrayList<>();
        map = m;
    }

    public Map getMap(){
        return map;
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
            if(it.first.equals("Fields")){
                String[] ids = it.second.split(",");
                for (String idIt : ids) {
                    fields.add((Field) fc.GetWithUID(Integer.parseInt(idIt)));
                }
            }
            else if(it.first.equals("isClose")){
                SunClose = Boolean.parseBoolean(it.second);
            }
        }
    }

    /**
     * The save method for the Sector class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os, boolean CallChildren) {
        os.println("Sector{");
        os.println("UID: " + GetUID());
        if(fields.size()>0) {
            os.print("Fields: ");
            fields.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return o1.GetUID()-o2.GetUID();
                }
            });
            for (Field it : fields) {
                os.print(it.GetUID());
                if (it != fields.get(fields.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
        }
        os.println("isClose: " + SunClose);
        os.println("}");
        if(CallChildren) {
            for (Field it : fields) {
                it.Save(os, CallChildren);
            }
        }
    }

    /**
     * Adds a field to the sector.
     * @param f The field what we want to add to the sector.
     */
    public void Add(Field f){
        fields.add(f);
    }

    /**
     * Removes a field from the sector.
     * @param f The field what we want to remove from the sector.
     */
    public void Remove(Field f){
        fields.remove(f);
    }

    /**
     * It calls the SunStorm function for all the sector's fields.
     */
    public void SunStorm(){
        for(Field it : fields){
            it.SunStorm();
        }
    }

    /**
     * The getter of the sector's fields.
     * @return With the fields of the sector.
     */
    public ArrayList<Field> getFields(){return fields;}

    /**
     * The getter of the SunClose attribute.
     * @return With the SunClose attribute's current value.
     */
    public boolean getSunClose(){
        return SunClose;
    }
}
