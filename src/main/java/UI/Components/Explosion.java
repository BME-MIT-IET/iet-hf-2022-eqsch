package UI.Components;

import Model.Asteroid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * represents the explosion in the game
 */
public class Explosion {
    /**
     * the original size of the explosion
     */
    double originalSize;
    /**
     * the actual size of the explosion
     */
    double size;
    /**
     * the x and y coordinates of the explosion
     */
    double x,y;
    /**
     * imageview for rendering the image of the explosion
     */
    ImageView image;
    /**
     * a timeline for the animation
     */
    Timeline timeline;
    /**
     * the elapsed time
     */
    double TimePassed = 0;
    /**
     * the end of the animation
     */
    double TimeEnd;
    /**
     * the speed of the animation
     */
    double Growth;
    /**
     * the image of the explosion
     */
    static Image ExplosionIMG =null;

    /**
     *
     * @param a the asteroid, where the explosion happened
     * @param duration the duration of the explosion
     * @param growth the speed of the animation
     */
    public Explosion(Asteroid a,double duration,double growth){
        x = a.getX();
        y = a.getY();
        originalSize = MagicConstants.AsteroidSize;
        size = originalSize;

        // loads the explosion image if it's not loaded yet
        if(ExplosionIMG == null){
            try {
                ExplosionIMG = new Image(new FileInputStream(new File("").getAbsolutePath()+"\\img\\explosion.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // sets the values
        image = new ImageView(ExplosionIMG);
        image.setPreserveRatio(true);
        TimeEnd = duration;
        Growth = growth;

        // ticks the timeline
        timeline = new Timeline(new KeyFrame(
                Duration.millis(10),
                ae -> Tick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * the timelines tick, creates the animation
     */
    public void Tick(){
        TimePassed += 10;
        if(TimePassed < TimeEnd){
            size = originalSize * Growth * (TimePassed/TimeEnd + 0.5);
            image.setOpacity((1 - (TimePassed/TimeEnd)));
        }
        else{
            timeline.stop();
            timeline = null;
            image.setOpacity(0);
        }
    }

    /**
     * getter for the height of the explosion
     * @return the value of the explosion's width
     */
    public double getHeight(){
        return size;
    }

    /**
     * getter for the width of the explosion
     * @return the value of the explosion's width
     */
    public double getWidth(){
        return size * 1.63211839;
    }

    /**
     * getter for x
     * @return the value of x
     */
    public double getX(){
        return x;
    }

    /**
     * getter for y
     * @return the value of y
     */
    public double getY(){
        return y;
    }

    /**
     * getter for image
     * @return the image
     */
    public ImageView getImage(){
        return image;
    }

    /**
     * checks if the explosion ended or not
     * @return true, if it ended, false if not
     */
    public boolean isDone(){
        return TimePassed >= TimeEnd;
    }
}
