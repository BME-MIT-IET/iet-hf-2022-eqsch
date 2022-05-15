package Controllers;

import Model.*;
import Model.Materials.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Utils.*;
import com.sun.jdi.ClassType;

/**
 * A class for file control. Save, load, etc..
 */
public class FileController {
    ArrayList<Pair<Saveable, ArrayList<StringPair>>> Linkeables = new ArrayList<>();

    static void Assert(boolean Condition,String Text) throws Exception {
        if(!Condition)
            throw new AssertException(Text);
    }

    /**
     * Returns an object, that has the given UID.
     * @param UID The given UID.
     * @return The matching object
     * @throws LinkerException
     */
    public Object GetWithUID(int UID) throws LinkerException {
        for(Pair<Saveable, ArrayList<StringPair>> it : Linkeables){
            if(it.first.GetUID() == UID){
                return it.first;
            }
        }
        throw new LinkerException(UID);
    }

    // trim
    static String Trim(String s){
        s = s.replaceAll("\t","");
        return s.replaceAll(" ","");
    }


    /**
     * The file next line should start with the UID
     * @param FScanner The file scanner
     * @param map The map we are building
     * @return With a new UID
     * @throws BadFileFormat
     */
    private int NextLineShouldBeUID(Scanner FScanner,Map map) throws BadFileFormat {
        String data = Trim(FScanner.nextLine());
        if(data.contains(":")){
            String[] args= data.split(":");
            if(args[0].equals("UID")){
                int newid = Integer.parseInt(args[1]);
                if(map.GetUIDMax()<=newid)
                    map.SetMaxId(newid+1);
                return newid;
            }
        }
        //System.out.println("Failed with:" + data);
        throw(new BadFileFormat(data,"Invalid UID. First line of object definition should always be UID"));
    }

    /**
     * Build and links the objects,
     * @param f The source file.
     * @param GC The game controller.
     * @param map The map we are building.
     * @return With a list of objects and their arguments.
     * @throws FileNotFoundException
     * @throws BadFileFormat
     */
    ArrayList<Pair<Saveable, ArrayList<StringPair>>> BuildLinkeables(File f,GameController GC,Map map) throws FileNotFoundException, BadFileFormat {
        map.SetMaxId(1);
        ArrayList<Pair<Saveable, ArrayList<StringPair>>> linkables = new ArrayList<>();
        Scanner FScanner = new Scanner(f);
        ArrayList<Integer> Order = new ArrayList<>();
        int CurrentPS = 0;
        int Target = 0;
        int Rounds = -1;
        while (FScanner.hasNextLine()) {
            boolean SpecificLine = false;
            String data = FScanner.nextLine();
            if(data.contains("{")){
                String ClassType = Trim((data.substring(0,data.indexOf('{'))));
                Pair<Saveable, ArrayList<StringPair>> current = null;
                switch (ClassType) {
                    case "Asteroid":
                        current = new Pair<Saveable, ArrayList<StringPair>>(new Asteroid(NextLineShouldBeUID(FScanner,map)), new ArrayList<>());
                        break;
                    case "Base":
                        current = new Pair<Saveable, ArrayList<StringPair>>(new Base(NextLineShouldBeUID(FScanner,map)), new ArrayList<>());
                        break;
                    case "Coal":
                        current = new Pair<Saveable, ArrayList<StringPair>>(new Coal(NextLineShouldBeUID(FScanner,map)), new ArrayList<>());
                        break;
                    case "Iron":
                        current = new Pair<Saveable, ArrayList<StringPair>>(new Iron(NextLineShouldBeUID(FScanner,map)), new ArrayList<>());
                        break;
                    case "Ice":
                        current = new Pair<Saveable, ArrayList<StringPair>>(new Ice(NextLineShouldBeUID(FScanner,map)), new ArrayList<>());
                        break;
                    case "PlayerShip":
                        PlayerShip ps = new PlayerShip(NextLineShouldBeUID(FScanner,map));
                        current = new Pair<Saveable, ArrayList<StringPair>>(ps, new ArrayList<>());
                        GC.ps.add(ps);
                        break;
                    case "RobotShip":
                        RobotShip rs = new RobotShip(NextLineShouldBeUID(FScanner,map));
                        current = new Pair<Saveable, ArrayList<StringPair>>(rs, new ArrayList<>());
                        GC.rs.add(rs);
                        break;
                    case "Sector":
                        Sector s = new Sector(NextLineShouldBeUID(FScanner,map),map);
                        current = new Pair<Saveable, ArrayList<StringPair>>(s, new ArrayList<>());
                        map.AddSector(s);
                        break;
                    case "TeleportGate":
                        TeleportGate t = new TeleportGate(NextLineShouldBeUID(FScanner,map));
                        current = new Pair<Saveable, ArrayList<StringPair>>(t, new ArrayList<>());
                        GC.tgs.add(t);
                        break;
                    case "UFO":
                        UFO u = new UFO(NextLineShouldBeUID(FScanner,map));
                        current = new Pair<Saveable, ArrayList<StringPair>>(u, new ArrayList<>());
                        GC.ufos.add(u);
                        break;
                    case "Uranium":
                        Uranium Uran = new Uranium(NextLineShouldBeUID(FScanner,map));
                        current = new Pair<Saveable, ArrayList<StringPair>>(Uran, new ArrayList<>());
                        GC.urans.add(Uran);
                        break;
                    case "Controller":
                    case "Sun":
                        SpecificLine = true;
                        break;
                    default:
                        throw(new BadFileFormat(data,"Invalid class identifier."));
                }
                if(!SpecificLine) {
                    do {
                        data = Trim(FScanner.nextLine());
                        if (!data.contains("}") && !data.equals("")) {
                            current.second.add(new StringPair(data.split(":")));
                        }
                    } while (!data.contains("}"));
                    linkables.add(current);
                }
                else{
                    do {
                        data = Trim(FScanner.nextLine());
                        if (!data.contains("}")) {
                            if (ClassType.equals("Controller")) {
                                StringPair val = new StringPair(data.split(":"));
                                if (val.first.equals("controller")) {
                                    GC.setController(Boolean.parseBoolean(val.second));
                                }
                                else if (val.first.equals("Order")) {
                                    String[] ids = val.second.split(",");
                                    for(String s : ids){
                                        Order.add(Integer.parseInt(s));
                                    }
                                }
                                else if (val.first.equals("Current")) {
                                    CurrentPS = Integer.parseInt(val.second);
                                }
                            }
                            else if(ClassType.equals("Sun")){
                                StringPair val = new StringPair(data.split(":"));
                                if (val.first.equals("Target")) {
                                    Target = Integer.parseInt(val.second);
                                }
                                else if (val.first.equals("Rounds")) {
                                    Rounds = Integer.parseInt(val.second);
                                }
                            }
                        }
                    } while (!data.contains("}"));
                }
            }
        }
        //Linking Sun's things and Controller's things
        Sun.GetInstance().SetMap(map);
        if(Order.size() > 0){
            ArrayList<PlayerShip> order = new ArrayList<>();
            for(Integer i : Order) {
                for (PlayerShip p : GC.ps) {
                    if(i.equals(p.GetUID())){
                        order.add(p);
                        break;
                    }
                }
            }
            if(GC.ps.size() == order.size()){
                GC.ps = order;
            }
        }
        if(CurrentPS != 0){
            PlayerShip c = null;
            for (PlayerShip p : GC.ps) {
                if(CurrentPS == p.GetUID()){
                    c = p;
                    break;
                }
            }
            if(c != null)
                GC.setCurrentPlayer(c);
        }
        if(Target != 0){
            Sector t = null;
            for(Sector s : map.getSectors()){
                if(s.GetUID() == Target){
                    t = s;
                    break;
                }
            }
            if(t != null)
                Sun.GetInstance().setTarget(t);
        }
        if(Rounds != -1)
            Sun.GetInstance().setRoundsUntillStorm(Rounds);
        return linkables;
    }

    /**
     * Loads a map from file.
     * @param file The source file, from we want to load the map
     * @param GC The game controller.
     * @return With the loaded map.
     * @throws FileNotFoundException
     * @throws BadFileFormat
     */
    //To be vastly expanded
    public Map Load(File file, GameController GC) throws FileNotFoundException, BadFileFormat {
        Map map = new Map();

        //Nuking everything that might have been used previously
        Linkeables = new ArrayList<>();
        GC.ps = new ArrayList<>();
        GC.rs = new ArrayList<>();
        GC.ufos = new ArrayList<>();
        GC.tgs = new ArrayList<>();
        GC.urans = new ArrayList<>();
        GC.setCurrentPlayer(null);
        Linkeables = BuildLinkeables(file,GC,map);
        for(Pair<Saveable, ArrayList<StringPair>> it : Linkeables){
            try {
                it.second.removeIf(x -> x.second.equals("@Ignore"));
                it.first.Link(it.second,this);
            }
            catch (LinkerException e){
                String data = "Unknown type Object with UID: " + it.first.GetUID();
                throw(new BadFileFormat(data,"Couldn't find parameter UID: " + e.GetUID()));
            }catch (Exception e){
                String data = "Unknown type Object with UID: " + it.first.GetUID();
                throw(new BadFileFormat(data,"Unknown issue:" + e.getClass() + ": " + Arrays.toString(e.getStackTrace())));
            }
        }
        return map;
    }

    /**
     * Saving process.
     * @param f The file where we want to save.
     * @param map The map we want to save.
     * @param gc The game controller.
     * @throws FileNotFoundException
     */
    public void Save(File f,Map map,GameController gc) throws FileNotFoundException {
        if(f.exists())
            f.delete();
        PrintStream os = new PrintStream(
                new FileOutputStream(f, false));
        map.Save(os);

        os.println("Controller{");
        os.println("controller: " + gc.controller);
        if(gc.ps.size() > 0) {
            os.print("Order: ");
            for (PlayerShip it : gc.ps) {
                os.print(it.GetUID());
                if (it != gc.ps.get(gc.ps.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
            if(gc.getCurrentPlayer() != null) {
                os.println("Current: " + gc.getCurrentPlayer().GetUID());
            }
        }
        os.println("}");
        os.println("Sun{");
        if(Sun.GetInstance().getTarget() != null)
            os.println("Target: " + Sun.GetInstance().getTarget().GetUID());
        os.println("Rounds: " + Sun.GetInstance().getRoundsUntillStorm());
        os.println("}");
        os.close();
    }

    /**
     * Compares two files.
     * @param a The first file.
     * @param b The second file.
     * @return True, if they have the same content, false if not.
     * @throws Exception
     */
    public boolean Compare(File a, File b) throws Exception {
        boolean success = false;
        Scanner FScanner = new Scanner(a);
        ArrayList<Pair<Saveable, ArrayList<StringPair>>> ALinkeables = BuildLinkeables(a,new GameController(),new Map());
        ArrayList<Pair<Saveable, ArrayList<StringPair>>> BLinkeables = BuildLinkeables(b,new GameController(),new Map());
        Assert(ALinkeables.size() == BLinkeables.size(),"Object number mismatch: " + ALinkeables.size() + " versus " + BLinkeables.size());
        for(Pair<Saveable, ArrayList<StringPair>> it1 : ALinkeables){
            boolean found = false;
            for(Pair<Saveable, ArrayList<StringPair>> it2 : BLinkeables){
                if(it1.first.GetUID() == it2.first.GetUID()){
                    found = true;
                    Assert( it1.second.size() == it2.second.size() , "Comparison objects: UID1:" + it1.first.GetUID() + " UID2:" + it2.first.GetUID() + "| Parameter size mismatch->"
                            + " It1:" + it1.second.size() + " It2:" + it2.second.size());
                    for(StringPair ParamIt1 : it1.second){
                        boolean paramFound = false;
                        for(StringPair ParamIt2 : it2.second){
                            if(ParamIt1.first.equals(ParamIt2.first)){
                                paramFound = true;
                                if(!ParamIt1.second.equals("@Ignore") && !ParamIt2.second.equals("@Ignore")) {
                                    Assert(ParamIt1.second.equals(ParamIt2.second), "Comparison objects: UID1:" + it1.first.GetUID() + " UID2:" + it2.first.GetUID() + "| Parameter mismatch-> "
                                            + "Field:" + ParamIt1.first + "  It1:" + ParamIt1.second + " It2:" + ParamIt2.second);
                                }
                            }
                        }
                        Assert(paramFound , "Parameter not found: Object UID:" + it1.first.GetUID() + " Paramtype: " + ParamIt1.first);
                    }
                    break;
                }
            }
            Assert( found , "Object not found: UID" + it1.first.GetUID());
        }

        for(Pair<Saveable, ArrayList<StringPair>> it1 : BLinkeables){
            boolean found = false;
            for(Pair<Saveable, ArrayList<StringPair>> it2 : ALinkeables){
                if(it1.first.GetUID() == it2.first.GetUID()){
                    found = true;
                    Assert( it1.second.size() == it2.second.size() , "Comparison objects: UID1:" + it1.first.GetUID() + " UID2:" + it2.first.GetUID() + "| Parameter size mismatch->"
                            + " It1:" + it1.second.size() + " It2:" + it2.second.size());
                    for(StringPair ParamIt1 : it1.second){
                        boolean paramFound = false;
                        for(StringPair ParamIt2 : it2.second){
                            if(ParamIt1.first.equals(ParamIt2.first)){
                                paramFound = true;
                                if(!ParamIt1.second.equals("@Ignore") && !ParamIt2.second.equals("@Ignore")) {
                                    Assert(ParamIt1.second.equals(ParamIt2.second), "Comparison objects: UID1:" + it1.first.GetUID() + " UID2:" + it2.first.GetUID() + "| Parameter mismatch-> "
                                            + "Field:" + ParamIt1.first +  "  It1:" + ParamIt1.second + " It2:" + ParamIt2.second);
                                }
                            }
                        }
                        Assert( paramFound , "Parameter not found: Object UID" + it1.first.GetUID() + " Paramtype: " + ParamIt1.first);
                    }
                    break;
                }
            }
            Assert( found , "Object not found: UID" + it1.first.GetUID());
        }

        return success;
    }
}
