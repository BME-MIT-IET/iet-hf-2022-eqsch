package Controllers;

import Model.*;
import Model.Materials.*;
import UI.Components.MagicConstants;
import Utils.Pair;
import Utils.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class for building a map.
 */
public class MapBuilder {
    // A random generator
    private static final Random random = new Random();
    // The map
    Map map;


    /**
     * Help for making graph
     * @param a An asteroid
     * @param graph An Asteroid arraylist
     */
    private void DiscoverGraphHelper(Asteroid a,ArrayList<Asteroid> graph){
        graph.add(a);
        for(Field it :a.getNeighbours()){
            Asteroid neighbour = (Asteroid) it; //This can only be done because no other type of field exists in generation
            if(!graph.contains(neighbour))
                DiscoverGraphHelper(neighbour,graph);
        }
    }


    /**
     * Discovering the graph
     * @param a An asteroid
     * @return the Graph we discovered
     */
    private ArrayList<Asteroid> DiscoverGraph(Asteroid a){
        ArrayList<Asteroid> Graph = new ArrayList<>();
        DiscoverGraphHelper(a,Graph);
        return Graph;
    }


    /**
     * Finding the best asteroid to make neighbours (links)
     * @param graph1 an Asteroid Arraylist graph
     * @param graph2 another Asteroid Arraylist graph
     * @return with the best link what we found
     */
    private Pair<Pair<Asteroid,Asteroid>,Double> FindBestLink(ArrayList<Asteroid> graph1, ArrayList<Asteroid> graph2){
        Pair<Pair<Asteroid,Asteroid>,Double> BestLink = new Pair<Pair<Asteroid,Asteroid>,Double>(new Pair<Asteroid,Asteroid>(graph1.get(0),graph2.get(0)),distance(graph1.get(0),graph2.get(0)));
        for(Asteroid a : graph1) {
            for (Asteroid b : graph2) {
                if (distance(a, b) < BestLink.second) {
                    BestLink = new Pair<Pair<Asteroid, Asteroid>, Double>(new Pair<Asteroid, Asteroid>(a, b), distance(a, b));
                }
            }
        }
        return BestLink;
    }


    /**
     * Makes the asteroids traversable
     * @param asteroids An Asteroid Arraylist
     */
    private void MakeGraphTraversable(ArrayList<Asteroid> asteroids){
        ArrayList<ArrayList<Asteroid>> Graphs = new ArrayList<>();
        do {
            Graphs = new ArrayList<>();
            for (Asteroid a : asteroids) {
                boolean found = false;
                for (ArrayList<Asteroid> graph : Graphs) {
                    if (graph.contains(a)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Graphs.add(DiscoverGraph(a));
                }
            }
            if(Graphs.size() > 1){
                Pair<Pair<Asteroid, Asteroid>, Double> BestLink = null;
                for(ArrayList<Asteroid> graph1 : Graphs){
                    for(ArrayList<Asteroid> graph2 : Graphs){
                        if(graph1 != graph2) {
                            Pair<Pair<Asteroid, Asteroid>, Double> Link = FindBestLink(graph1,graph2);
                            if(BestLink == null || BestLink.second > Link.second){
                                BestLink = Link;
                            }
                        }
                    }
                }
                BestLink.first.first.AddNeighbour(BestLink.first.second);
                BestLink.first.second.AddNeighbour(BestLink.first.first);
            }
        }while (Graphs.size() > 1);
    }

    /**
     * This method's objective is to build up a simple random generated starter map.
     * @return the map that was built up
     */
    public Map BuildMap(GameController GC) {
        GC.ps = new ArrayList<>();
        GC.rs = new ArrayList<>();
        GC.ufos = new ArrayList<>();
        GC.tgs = new ArrayList<>();
        GC.urans = new ArrayList<>();
        map = new Map();
        ArrayList<Asteroid> asteroids = new ArrayList<>();

        // creating players on random asteroid
        for (int i = 0; i < MagicConstants.shipNumber; i++) {
            PlayerShip p = new PlayerShip(map);
            GC.ps.add(p);
        }



        // creating sectors
        for (int i = 0; i < MagicConstants.sectorNumber; i++)
            map.AddSector(new Sector(map.GetNewUID(),map));

        // creating asteroids and adding to sectors
        Random r = new Random();
        for (int i = 0; i < MagicConstants.asteroidNumber; i++){
            ArrayList<Field> idc = new ArrayList<>();
            idc.addAll(asteroids);
            Asteroid a = genRndAsteroid(GC, idc, r);
            //System.out.println(a.getX()+" "+a.getY());
            asteroids.add(a);
        }

        // placing playerships on asteroid
        for(PlayerShip ps: GC.ps){
            Asteroid a = asteroids.get(random.nextInt(asteroids.size()));
            ps.setAsteroid(a);
            a.Add(ps);
        }

        // linking asteroids in max distance
        for(Asteroid a1 : asteroids){
            for(Asteroid a2 : asteroids){
                if(closeEnough(a1,a2, MagicConstants.neighbourMaxDistance)){
                    a1.AddNeighbour(a2);
                    a2.AddNeighbour(a1);
                }
            }
        }

        // linking asteroids which has 0 neighbours
        for(Asteroid a1 : asteroids){
            if(a1.getNeighbours().size()==0){
                ////System.out.println("NO NE");
                double minDistance = 2.0;
                Asteroid minAsteroid = null;
                for(Asteroid a2 : asteroids){
                    if(a1 != a2) {
                        if (distance(a1, a2) < minDistance) {
                            minDistance = distance(a1, a2);
                            minAsteroid = a2;
                        }
                    }
                }
                a1.AddNeighbour(minAsteroid);
                minAsteroid.AddNeighbour(a1);
            }
        }
        MakeGraphTraversable(asteroids);

        // linking asteroids with sectors based on angles
        for(Asteroid a : asteroids) {
            Sector s = whichSectorItIs(a);
            a.setSector(s);
            s.Add(a);
        }

//        // creating players on random asteroid
//        for (int i = 0; i < 1; i++) {
//            PlayerShip p = new PlayerShip(asteroids.get(random.nextInt(asteroids.size())));
//            GC.ps.add(p);
//        }


        // creating UFOs on random asteroid
        for (int i = 0; i < MagicConstants.shipNumber; i++) {
            UFO p = new UFO(asteroids.get(random.nextInt(asteroids.size())));
            GC.ufos.add(p);
        }

        return map;
    }

    // gives back Sector base on angles (0.0,0.0 is the sun)
    private Sector whichSectorItIs(Asteroid a) {
        int sectorCnt = map.getSectors().size();
        double angleSize = 360.0/sectorCnt;
        double dX = a.getX() - 0.0;
        double dY = a.getY() - 0.0;
        double deg = Math.atan2(dY, dX)*180.0/Math.PI;
        if(deg < 0)
            deg += 360;
        int index = (int) Math.floor(deg/angleSize);
        return map.getSectors().get(index);
    }


    /**
     * Returns true if the fields are too close to each other
     * @param asteroids A Field Arraylist what makes our map
     * @param a a Field
     * @return true if the field is too close for the asteroids, false if not
     */
    public static boolean tooClose(ArrayList<Field> asteroids, Field a){
        for(Field a2: asteroids)
            if(closeEnough(a,a2,MagicConstants.asteroidTooClose))
                return true;

        return false;
    }

    /**
     * Says if the field is too close for the Sun
     * @param a a Field
     * @return true if the field is too close for the Sun, false if not
     */
    public static boolean tooCloseToSun(Field a){
        if(closeEnough(a,0,0,MagicConstants.sunTooClose))
            return true;
        return false;
    }


    /**
     * Generates random Asteroid
     * @param GC the GameCOntroller
     * @param asteroids a Field ArrayList
     * @param r random number
     * @return with an Asteroid we just generated
     */
    private Asteroid genRndAsteroid(GameController GC, ArrayList<Field> asteroids, Random r) {
        Asteroid a = new Asteroid(map.GetNewUID(), null, genRndMaterial(GC) , random.nextInt(6)+4);
        do{
            double alfa = r.nextDouble() * 2.0 * Math.PI;
            double dist = r.nextDouble() * MagicConstants.MaxGeneratedDistance;
            a.setX(Math.cos(alfa)*dist);
            a.setY(Math.sin(alfa)*dist);
        }while(tooClose(asteroids,a) || tooCloseToSun(a));

        return a;
    }

    /**
     * Generating random Material which is not the given param
     * @return the sector which should be ignored
     */
    private Sector genRndSector(Sector s){
        while(true){
            Sector rndSector = map.getSectors().get(random.nextInt(map.getSectors().size()));
            if(s == rndSector)
                continue;
            return rndSector;
        }
    }

    /**
     * Generating random Material and setting new UID for that.
     * @return random Material instance
     */
    private Material genRndMaterial(GameController gc){
        int rnd = random.nextInt(4);
        switch (rnd){
            case 0:
                return new Coal(map.GetNewUID());
            case 1:
                return new Ice(map.GetNewUID());
            case 2:
                return new Iron(map.GetNewUID());
            case 3:
                Uranium u = new Uranium(map.GetNewUID());
                gc.urans.add(u);
                return u;
            default:
                return null;
        }
    }

    /**
     * Generating random Point in the given limits.
     * @param min value for generating
     * @param max value for generating
     * @return a pseudo random Point
     */
    private Point genRndPoint( double min, double max) {
        double x = min + random.nextDouble() * (max - min);
        double y = min + random.nextDouble() * (max - min);

        return new Point(x,y);
    }

    /**
     * Distance between two Fields
     * @param a1 a Field
     * @param a2 another Field
     * @return with a double what is the distance between the two fields
     */
    public static double distance(Field a1, Field a2){
        return Math.sqrt(Math.pow(a1.getX()-a2.getX(),2) + Math.pow(a1.getY()-a2.getY(),2));
    }

    /**
     * Distance between a Field and coordinates
     * @param a1 a Field
     * @param x coordinate x axle
     * @param y coordinate y axle
     * @return with a double what is the distance between the  field and the coordinate
     */
    public static double distance(Field a1, double x, double y){
        return Math.sqrt(Math.pow(a1.getX()-x,2) + Math.pow(a1.getY()-y,2));
    }


    /**
     * Helps to make neighbours when the fields are close enough for each other
     * @param a1 a Field
     * @param a2 another Field
     * @param maxDistance double for the max distance
     * @return true if the distance is equal or smaller than the maximum distance, false if not
     */
    private static boolean closeEnough(Field a1, Field a2, double maxDistance){
        if(a1 == a2){
            return false;
        }
        double d = distance(a1, a2);
        return d <= maxDistance;
    }

    /**
     * Helps to make neighbours when a field and the coordinates are close enough for each other
     * @param a1 a Field
     * @param x coordinate x axle
     * @param y coordinate y axle
     * @param maxDistance double for the max distance
     * @return true if the distance is equal or smaller than the maximum distance, false if not
     */
    private static boolean closeEnough(Field a1, double x, double y, double maxDistance){
        double d = distance(a1, x, y);
        return d <= maxDistance;
    }

}
