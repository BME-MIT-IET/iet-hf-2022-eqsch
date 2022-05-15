package UI.Components;

import Model.Ship;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * represents the ship's image
 */
public class ShipImage extends ImageView {
    /**
     * the ship
     */
    Ship ship;
    /**
     * the size of the ship
     */
    public double size;

    ShipImage(Ship s){
        ImageVisitor IV= new ImageVisitor(s);
        Image im = IV.getImage();
        setImage(im);
        this.setFitWidth(MagicConstants.ShipSize);
        this.setFitHeight(MagicConstants.ShipSize);
        size = MagicConstants.ShipSize;
        this.setPreserveRatio(true);
        ship = s;
    }

    /**
     * getter for the ship
     * @return the ship
     */
    public Ship getShip(){
        return ship;
    }

    @Override
    public String toString(){
        return "ShipImage";
    }
}
