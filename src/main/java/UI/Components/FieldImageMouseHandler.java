package UI.Components;

import Model.Field;
import UI.Layout.Game.GameUIController;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;

/**
 * mouse event handler for the fields of the game
 */
public class FieldImageMouseHandler implements EventHandler<MouseEvent> {
    /**
     * the current game's UI controller
     */
    GameUIController gameUIController;
    /**
     * the selected FieldImage
     */
    FieldImage fieldImage;

    public FieldImageMouseHandler(FieldImage f, GameUIController guc){
        gameUIController = guc;
        fieldImage = f;
    }

    /**
     * handles the mouse events if it occurs on a field of the game
     * @param mouseEvent the mouse event
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED){
            gameUIController.Move(fieldImage);
        }
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED){
            gameUIController.Select(fieldImage);
        }
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED){
            gameUIController.Deselect(fieldImage,false, mouseEvent.getSceneX(),mouseEvent.getSceneY());
        }
    }
}