package UI.Layout.Game.PutDownSidePanel;

import Model.PlayerShip;
import Model.TeleportGate;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.Game.ISidePanelController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;

import java.util.ArrayList;
/**
 * The Put Down Side Panel Controller class
 */
public class PutDownSidePanelController implements ISidePanelController {
    /**
     * The control of the game UI
     */
    GameUIController gameUIController;
    /**
     * Button for putting down Teleport1
     */
    @FXML
    Button Teleport1Button;
    /**
     * Button for putting down Teleport2
     */
    @FXML
    Button Teleport2Button;
    /**
     * Button for putting down Teleport3
     */
    @FXML
    Button Teleport3Button;
    /**
     * Cancel button
     */
    @FXML
    Button CancelButton;

    public PutDownSidePanelController(GameUIController GUIC) {
            gameUIController = GUIC;
        }

    /**
     * Initialization for the Options Side Panel
     */
    public void Init() {
        new SelectHandler(Teleport1Button);
        new SelectHandler(Teleport2Button);
        new SelectHandler(Teleport3Button);
        new SelectHandler(CancelButton);

        int teleportCnt = gameUIController.getGameController().getCurrentPlayer().getTeleports().size();
        if(teleportCnt >= 1)
            Teleport1Button.setText("X");
        if(teleportCnt >= 2)
            Teleport2Button.setText("X");
        if (teleportCnt >= 3)
            Teleport3Button.setText("X");
        Refresh();
    }


    /**
     * Generates the command for the teleportgate1
     */
    @FXML
    public void Teleport1(){
        try {
            if(gameUIController.getGameController().getCurrentPlayer().getTeleports().size() >= 1){
                Teleport1Button.setText("");
                gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_down " + gameUIController.getGameController().getCurrentPlayer().getTeleports().get(0).GetUID());
                gameUIController.SwitchToInventory();
                gameUIController.SwitchToActionSidePanel();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the command for the teleportgate2
     */
    @FXML
    public void Teleport2(){
        try {
            if(gameUIController.getGameController().getCurrentPlayer().getTeleports().size() >= 2) {
                Teleport2Button.setText("");
                gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_down " + gameUIController.getGameController().getCurrentPlayer().getTeleports().get(1).GetUID());
                gameUIController.SwitchToInventory();
                gameUIController.SwitchToActionSidePanel();
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    /**
     * Generates the command for the teleportgate3
     */
    @FXML
    public void Teleport3(){
        try {
            if (gameUIController.getGameController().getCurrentPlayer().getTeleports().size() >= 3){
                Teleport3Button.setText("");
                gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_down " + gameUIController.getGameController().getCurrentPlayer().getTeleports().get(2).GetUID());
                gameUIController.SwitchToInventory();
                gameUIController.SwitchToActionSidePanel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method for cancel the current panel, switch to the Action Side Panel
     */
    @FXML
    public void Cancel() {
        gameUIController.SwitchToActionSidePanel();
    }

    /**
     * Refreshes the Put Down Side Panel if the player puts down a teleportgate
     */
    @Override
    public void Refresh() {
        PlayerShip curr = gameUIController.getGameController().getCurrentPlayer();

        if(curr.getTeleports().size() == 0){
            Teleport1Button.setEffect(new ColorAdjust(0, 1, 0, 0));
            Teleport2Button.setEffect(new ColorAdjust(0, 1, 0, 0));
            Teleport3Button.setEffect(new ColorAdjust(0, 1, 0, 0));
        }
        else if(curr.getTeleports().size() == 1){
            Teleport1Button.setEffect(null);
            Teleport2Button.setEffect(new ColorAdjust(0, 1, 0, 0));
            Teleport3Button.setEffect(new ColorAdjust(0, 1, 0, 0));
        }
        else if(curr.getTeleports().size() == 2){
            Teleport1Button.setEffect(null);
            Teleport2Button.setEffect(null);
            Teleport3Button.setEffect(new ColorAdjust(0, 1, 0, 0));
        }
        else{
            Teleport1Button.setEffect(null);
            Teleport2Button.setEffect(null);
            Teleport3Button.setEffect(null);
        }
    }
}
