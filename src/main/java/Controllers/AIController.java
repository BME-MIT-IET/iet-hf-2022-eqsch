package Controllers;

import Model.Asteroid;
import Model.Field;
import Model.RobotShip;
import Model.UFO;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple AI class for the robot and the UFO
 */
public class AIController {

    private static final Random random = new Random();

    /**
     * RobotShip takes turn, it drills if the shell is not destroyed
     * if shell is destroyed it moves to the neighbour which has the highest shell
     * @param rs the RobotShip which takes the turn
     */
    public void TakeTurn(RobotShip rs){
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        int maxShell = 0;
        Asteroid maxShellAsteroid = null;

        // kills itself if it is on a isolated asteroid
        if(rs.getAsteroid().getNeighbours().size() == 0)
            rs.Die();
        // drills if the shell is not destroyed
        if (rs.getAsteroid().GetShell() > 0)
            rs.Drill();
        else{
            // gets the neighbours which ways the robot can go
            for (Field f : rs.getAsteroid().getNeighbours())
                asteroids.add(f.MovedTo());
            // getting the highest shell owner asteroid
            for (Asteroid a : asteroids)
                if (a.GetShell() > maxShell){
                    maxShell = a.GetShell();
                    maxShellAsteroid = a;
                }
            // whether the neighbours shells are all destroyed
            // it goes to a random asteroid or it chooses the highest shell owner
            if(maxShellAsteroid == null)
                rs.Move(rs.getAsteroid().getNeighbours().get(random.nextInt(rs.getAsteroid().getNeighbours().size())));
            else
                rs.Move(maxShellAsteroid);
        }
    }

    /**
     * UFO takes turn, it mines if the shell is destroyed and there is material in core
     * it goes to a neighbour if shell is still up
     * @param u the UFO which takes the turn
     */
    public void TakeTurn(UFO u){
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Asteroid zeroShellAsteroid = null;

        // kills itself if it is on a isolated asteroid
        if(u.getAsteroid().getNeighbours().size() == 0)
            u.Die();
        // mines if the shell is destroyed and there is a core
        if(u.getAsteroid().GetShell() == 0 && u.getAsteroid().GetCore() != null)
            u.Mine();
        else{
            // gets the neighbours which ways the robot can go
            for (Field f : u.getAsteroid().getNeighbours())
                asteroids.add(f.MovedTo());
            // getting the asteroid which has destroyed shell
            for (Asteroid a : asteroids)
                if (a.GetShell() == 0){
                    zeroShellAsteroid = a;
                }
            // it goes to a random asteroid if there is not an asteroid which has destroyed shell
            // it goes to the asteroid which has destroyed shell if exits one
            if(zeroShellAsteroid == null)
                u.Move(u.getAsteroid().getNeighbours().get(random.nextInt(u.getAsteroid().getNeighbours().size())));
            else
                u.Move(zeroShellAsteroid);
        }
    }
}
