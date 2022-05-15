package UI.Components;

import Model.Base;
import Model.Field;
import Model.Ship;
import Model.TeleportGate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * represents the shown fields and the base or ships on it
 */
public class FieldImage extends ImageView {
    /**
     * the actual field
     */
    Field field;
    /**
     * a gridpane for the ships on the field
     */
    GridPane ships = null;
    /**
     * an imageview for the base on the field
     */
    ImageView base = null;
    /**
     * the field's pair if it's a teleport
     */
    TeleportGate pair = null;
    /**
     * the x coordinate
     */
    public double x=0;
    /**
     * the y coordinate
     */
    public double y=0;
    /**
     * the size of the image
     */
    public double size;

    /**
     * prints out the field, the base and the ships if it has any
     * @param f the field we want to show
     */
    public FieldImage(Field f){
        field = f;
        ImageVisitor IV= new ImageVisitor(f);
        Image im = IV.getImage();
        setImage(im);
        this.setFitWidth(MagicConstants.AsteroidSize);
        this.setFitHeight(MagicConstants.AsteroidSize);
        size = MagicConstants.AsteroidSize;
        x=f.getX();
        y=f.getY();
        this.setPreserveRatio(true);
        ArrayList<Ship> Ships = IV.getShips();
        if(Ships != null && Ships.size() != 0){
            ships = new GridPane();
            int i =0;
            for(Ship s : Ships){
                ships.add(new ShipImage(s),i,0);
                i++;
            }
        }
        Base b = IV.getBase();
        if(b != null){
            ImageVisitor baseVisit = new ImageVisitor(b);
            base = new ImageView(baseVisit.image);
            base.setPreserveRatio(true);
            base.setFitWidth(MagicConstants.BaseImageSize);
            base.setFitHeight(MagicConstants.BaseImageSize);
        }
        pair = IV.getPair();
    }

    /**
     * getter for ships
     * @return the gridpane
     */
    public GridPane getShips(){
        return ships;
    }

    /**
     * getter for field
     * @return the field
     */
    public Field getField(){
        return field;
    }

    /**
     * getter for base
     * @return the imageview of the base
     */
    public ImageView getBase(){return base;}

    /**
     * getter for pair
     * @return the pair of the field
     */
    public TeleportGate getPair(){return pair;}
}
