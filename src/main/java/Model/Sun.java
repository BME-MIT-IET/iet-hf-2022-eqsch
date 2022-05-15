package Model;

import Controllers.NotificationManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * It represents the Sun in the game.
 * It controls the SunStorms.
 */
public class Sun {
    /**
     * The instance of the singleton Sun class.
     */
    private static Sun instance=null;
    /**
     * True, if there is a sun storm right now, false if not.
     */
    private static boolean StormNow=false;
    /**
     * The map where the sun is.
     */
    private Map map;
    /**
     * Round until the next sun storm-
     */
    private int RoundsUntillStorm;
    /**
     * The sector where the sun storm will happpen.
     */
    private Sector target;

    private Sun(){
        RoundsUntillStorm = new Random().nextInt(6)+4;
    }

    /**
     * @return Singleton class. Returns the only instance of Sun that exists.
     */
    public static Sun GetInstance(){
        if(instance == null){
            instance = new Sun();
        }
        return instance;
    }

    /**
     * Setter for the map, the targeted sector and the rounds until the next sun storm.
     * @param m The map we want to set for the sun.
     */
    public void SetMap(Map m){
        map = m;
        target = m.getSectors().get(new Random().nextInt(m.getSectors().size()));
        RoundsUntillStorm = new Random().nextInt(6)+4;
    }

    /**
     * Cause a sun storm in a specific sector.
     * @param s The sector where the sun storm will happen.
     */
    public void SunStorm(Sector s){
        //System.out.println("ss");
        NotificationManager.AddWarning("Sunstorm in Sector" + target.GetUID() + ".");
        s.SunStorm();
        RoundsUntillStorm = new Random().nextInt(6)+4;
    }

    /**
     * Calls sun storm if zero turns left until sun storm.
     */
    public void TurnOver(){
        RoundsUntillStorm--;
        if(RoundsUntillStorm==0){
            SunStorm(target);
            ArrayList<Sector> sectors = map.getSectors();
            Random rand = new Random();
            target = sectors.get(rand.nextInt(sectors.size()));
        }
        else{
            NotificationManager.AddWarning("Sunstorm will happen in Sector" + target.GetUID() + " in " + RoundsUntillStorm + "turns.");
        }
    }

    /**
     * The getter of the sun storm's target.
     * @return With the current targeted sector.
     */
    public Sector getTarget(){
        return target;
    }

    /**
     * The setter of the sun storm's target.
     * @param sector With the sector we want to target for the next sun storm.
     */
    public void setTarget(Sector sector){
        target = sector;
    }

    /**
     * The setter of the rounds until the next sun storm occurs.
     * @param val The round we want to set.
     */
    public void setRoundsUntillStorm(int val){
        RoundsUntillStorm = val;
    }

    /**
     * The getter of the rounds until the next sun storm occurs.
     * @return With the current "time" until the next sun storm.
     */
    public int getRoundsUntillStorm(){
        return RoundsUntillStorm;
    }

    /**
     * Resets the instance of the Sun.
     */
    public static void Reset(){
        instance = null;
    }
}
