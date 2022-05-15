package UI.Components;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * class for handling if the player selected a button or pressed it
 */
public class SelectHandler implements EventHandler<MouseEvent> {
    Button button;

    /**
     * the button's border is transparent by default
     * @param b
     */
    public SelectHandler(Button b){
        button = b;
        b.getStylesheets().add(this.getClass().getResource("CustomButton.css").toExternalForm());
        b.addEventHandler(MouseEvent.ANY,this);
        b.getStyleClass().add("transparent");
    }

    /**
     * handles if the player move the cursor above a button and clicks on it
     * @param mouseEvent the mouse event
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED){
            button.getStyleClass().removeAll("transparent");
            button.getStyleClass().removeAll("pressed");
            button.getStyleClass().add("selected");
        }
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED){
            button.getStyleClass().removeAll("selected");
            button.getStyleClass().removeAll("pressed");
            button.getStyleClass().add("transparent");
        }
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED){
            button.getStyleClass().removeAll("selected");
            button.getStyleClass().removeAll("transparent");
            button.getStyleClass().add("pressed");
        }
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED){
            button.getStyleClass().removeAll("pressed");
            if(!button.getStyleClass().contains("transparent"))
                button.getStyleClass().add("selected");
        }
    }

}
