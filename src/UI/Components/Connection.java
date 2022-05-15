package UI.Components;

import Model.Field;
import javafx.scene.shape.Line;

/**
 * represents the connections between the asteroids/teleports
 */
public class Connection {
    /**
     * the first endpoint of the connection
     */
    Field f1;
    /**
     * the second endpoint of the connection
     */
    Field f2;
    /**
     * the line between the endpoints
     */
    public Line line = null;
    /**
     * true, if the connection is between 2 teleports, false if not
     */
    public boolean teleports = false;

    public Connection(Field F1,Field F2){
        f1 = F1;
        f2 = F2;
    }

    /**
     * getter for the first endpoint
     * @return the first endpoint
     */
    public Field getF1(){
        return f1;
    }

    /**
     * getter for the second endpoint
     * @return the second endpoint
     */
    public Field getF2(){
        return f2;
    }

    /**
     * checks if a connection's two endpoint are the same or not
     * @param b the connection we want to check
     * @return true, if the two endpoints are the same, false if not
     */
    public boolean isSame(Connection b){
        return (f1 == b.f1 && f2 == b.f2) || (f1 == b.f2 && f2 == b.f1);
    }
}
