package UI.Components;

import Model.*;
import Model.Materials.Coal;
import Model.Materials.Ice;
import Model.Materials.Iron;
import Model.Materials.Uranium;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * visitor class for setting the right images
 */
public class ImageVisitor implements IVisitor {
    /**
     * the image
     */
    Image image = null;
    /**
     * the path where the image is
     */
    String filePath;
    /**
     * the ships on the field
     */
    ArrayList<Ship> ships = null;
    /**
     * the base on the field
     */
    Base base = null;
    /**
     * the pair of the teleport
     */
    TeleportGate pair = null;
    /**
     * the image of the asteroid
     */
    static Image AsteroidImage = null;
    /**
     * the image of the active teleport
     */
    static Image ActiveTeleportImage = null;
    /**
     * the image of the crazy teleport
     */
    static Image InsaneTeleportImage = null;
    /**
     * the image of the inactive teleport
     */
    static Image InactiveTeleportImage = null;
    /**
     * the image of the first player
     */
    static Image Player1 = null;
    /**
     * the image of the second player
     */
    static Image Player2 = null;
    /**
     * the image of the third player
     */
    static Image Player3 = null;
    /**
     * the image of the forth player
     */
    static Image Player4 = null;
    /**
     * the image of the fifth player
     */
    static Image Player5 = null;
    /**
     * the image of the robot
     */
    static Image Robot = null;
    /**
     * the image of the UFO
     */
    static Image UFO = null;
    /**
     * the image of the uranium
     */
    static Image Uranium = null;
    /**
     * the image of the iron
     */
    static Image Iron = null;
    /**
     * the image of the ice
     */
    static Image Ice = null;
    /**
     * the image of the coal
     */
    static Image Coal = null;
    /**
     * the image of the base foundation
     */
    static Image BaseFoundation = null;
    /**
     * the image of the completed base
     */
    static Image BaseComplete = null;

    public ImageVisitor(IVisitable v){
        filePath = new File("").getAbsolutePath();
        filePath+="\\img\\";
        v.accept(this);
    }

    /**
     * getter for image
     * @return the current image
     */
    public Image getImage(){
        return image;
    }

    /**
     * getter for ships
     * @return the ships
     */
    public ArrayList<Ship> getShips(){return ships;}

    /**
     * getter for base
     * @return the base
     */
    public Base getBase(){return base;}

    /**
     * getter for pair
     * @return the pair of the field (teleport)
     */
    public TeleportGate getPair(){return pair;}

    /**
     * visit method for the teleport, it selects which image should be loaded
     * @param tg the teleport
     */
    @Override
    public void visit(TeleportGate tg){
        if(tg.getWasHitByStorm()){
            if(InsaneTeleportImage == null)
            {
                try {
                    filePath += "Insane_teleportgate.png";
                    InsaneTeleportImage = new Image(new FileInputStream(filePath));
                } catch (FileNotFoundException e){
                    //System.out.println(filePath);
                    e.printStackTrace();
                }
            }
            image = InsaneTeleportImage;
        }
        else if(tg.isActive()){
            if(ActiveTeleportImage == null){
                try{
                    filePath += "Active_teleportgate.png";
                    ActiveTeleportImage = new Image(new FileInputStream(filePath));
                } catch (FileNotFoundException e){
                    //System.out.println(filePath);
                    e.printStackTrace();
                }
            }
            image = ActiveTeleportImage;
        }
        else{
            if(InactiveTeleportImage == null){
                try{
                    filePath += "Not_active_teleportgate.PNG";
                    InactiveTeleportImage = new Image(new FileInputStream(filePath));
                } catch (FileNotFoundException e){
                    //System.out.println(filePath);
                    e.printStackTrace();
                }
            }
            image = InactiveTeleportImage;
        }
        pair = tg.getPair();
    }

    /**
     * visit method for the asteroid, loads the image of the asteroid and sets it's base and ships
     * @param a the asteroid
     */
    @Override
    public void visit(Asteroid a) {
        if(AsteroidImage == null) {
            try {
                filePath += "Asteroid.png";
                AsteroidImage = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e) {
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = AsteroidImage;
        ships = a.getShips();
        base = a.GetBase();
    }

    /**
     * visit method fot the player, it selects which image should be loaded
     * @param p the player
     */
    @Override
    public void visit(PlayerShip p) {
        switch (p.GetUID() % 5 + 1){
            case 1:
                if(Player1 == null) {
                    try {
                        filePath += "PlayerShip1.png";
                        Player1 = new Image(new FileInputStream(filePath));
                    } catch (FileNotFoundException e) {
                        //System.out.println(filePath);
                        e.printStackTrace();
                    }
                }
                image = Player1;
                break;
            case 2:
                if(Player2 == null) {
                    try {
                        filePath += "PlayerShip2.png";
                        Player2 = new Image(new FileInputStream(filePath));
                    } catch (FileNotFoundException e) {
                        //System.out.println(filePath);
                        e.printStackTrace();
                    }
                }
                image = Player2;
                break;
            case 3:
                if(Player3 == null) {
                    try {
                        filePath += "PlayerShip3.png";
                        Player3 = new Image(new FileInputStream(filePath));
                    } catch (FileNotFoundException e) {
                        //System.out.println(filePath);
                        e.printStackTrace();
                    }
                }
                image = Player3;
                break;
            case 4:
                if(Player4 == null) {
                    try {
                        filePath += "PlayerShip4.png";
                        Player4 = new Image(new FileInputStream(filePath));
                    } catch (FileNotFoundException e) {
                        //System.out.println(filePath);
                        e.printStackTrace();
                    }
                }
                image = Player4;
                break;
            case 5:
                if(Player5 == null) {
                    try {
                        filePath += "PlayerShip5.png";
                        Player5 = new Image(new FileInputStream(filePath));
                    } catch (FileNotFoundException e) {
                        //System.out.println(filePath);
                        e.printStackTrace();
                    }
                }
                image = Player5;
                break;
        }
    }

    /**
     * visit method for the robot, loads the image of the robot
     * @param r
     */
    @Override
    public void visit(RobotShip r) {
        if(Robot == null){
            try{
                filePath += "Robot.png";
                Robot = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e){
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = Robot;
    }

    /**
     * visit method for the UFO, loads the image of the UFO
     * @param u the UFO
     */
    @Override
    public void visit(UFO u) {
        if(UFO == null){
            try{
                filePath += "UFO.png";
                UFO = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e){
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = UFO;
    }

    /**
     * visit method for the uranium, loads the image of the uranium
     * @param u the uranium
     */
    @Override
    public void visit(Uranium u) {
        if(Uranium == null){
            try{
                filePath += "Uranium.png";
                Uranium = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e){
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = Uranium;
    }

    /**
     * visit method for the iron, loads the image of the iron
     * @param i the iron
     */
    @Override
    public void visit(Iron i) {
        if(Iron == null){
            try{
                filePath += "Iron.png";
                Iron = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e){
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = Iron;
    }

    /**
     * visit method for the ice, loads the image of the ice
     * @param i the
     */
    @Override
    public void visit(Ice i) {
        if(Ice == null){
            try{
                filePath += "Ice.png";
                Ice = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e){
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = Ice;
    }

    /**
     * visit method for the coal, loads the image of the coal
     * @param c the coal
     */
    @Override
    public void visit(Coal c) {
        if(Coal == null){
            try{
                filePath += "Coal.png";
                Coal = new Image(new FileInputStream(filePath));
            } catch (FileNotFoundException e){
                //System.out.println(filePath);
                e.printStackTrace();
            }
        }
        image = Coal;
    }

    /**
     * visit method for the base, loads the image of the base
     * @param b the
     */
    @Override
    public void visit(Base b) {
        if(b.CheckComplete()){
            if(BaseComplete == null) {
                try {
                    filePath += "BaseDone.png";
                    BaseComplete = new Image(new FileInputStream(filePath));
                } catch (FileNotFoundException e) {
                    //System.out.println(filePath);
                    e.printStackTrace();
                }
            }
            image = BaseComplete;
        }
        else{
            if(BaseFoundation == null) {
                try {
                    filePath += "BaseFoundation.png";
                    BaseFoundation = new Image(new FileInputStream(filePath));
                } catch (FileNotFoundException e) {
                    //System.out.println(filePath);
                    e.printStackTrace();
                }
            }
            image = BaseFoundation;
        }

    }
}
