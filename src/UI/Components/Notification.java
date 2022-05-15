package UI.Components;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * represents the in-game notifications
 */
public class Notification {
    /**
     * a customized label with an error message
     */
    private Label msg;
    /**
     * the VBox, where we want to print out the notification
     */
    private VBox parent;
    /**
     * the time, when the notification starts to fade out
     */
    private final double FadeStart;
    /**
     * the time, when the notification ends to fade out
     */
    private final double FadeEnd;
    /**
     * the elapsed time
     */
    private double TimePassed;
    /**
     * a timeline for the fading
     */
    private Timeline timeline;
    /**
     * the color of the notification
     */
    private Color original;

    /**
     * sets the values and starts the timeline
     * @param Message the message
     * @param Target the VBox, where we want to print out the notification
     * @param fadeStart the time, when the notification ends to fade out
     * @param fadeEnd the time, when the notification ends to fade out
     * @param c the color of the notification
     */
    public Notification(Label Message, VBox Target, double fadeStart, double fadeEnd, Color c){
        msg = Message;
        parent = Target;
        FadeStart = fadeStart;
        FadeEnd = fadeEnd;
        TimePassed = 0;
        timeline = new Timeline(new KeyFrame(
                Duration.millis(10),
                ae -> Tick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        msg.setTextFill(c);
        Target.getChildren().add(msg);
        msg.setTextFill(c);
        parent.setVisible(true);
        original = c;
    }

    /**
     * the timeline ticks, creates the fading
     */
    private void Tick(){
        TimePassed += 10.0;
        if(TimePassed > FadeStart && TimePassed < FadeEnd){
            double alpha = ((FadeEnd - TimePassed) / (FadeEnd - FadeStart));
            Color NewColor = new Color(original.getRed(),original.getGreen(),original.getBlue(),alpha);
            msg.setTextFill(NewColor);
        }
        else if(TimePassed >= FadeEnd){
            timeline.stop();
            parent.getChildren().remove(msg);
            if(parent.getChildren().size()==0)
                parent.setVisible(false);
        }
    }

}
