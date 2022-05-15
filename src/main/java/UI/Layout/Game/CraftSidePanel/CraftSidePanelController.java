package UI.Layout.Game.CraftSidePanel;

import Model.Materials.BillCreator;
import Model.PlayerShip;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.Game.ISidePanelController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
/**
 * The Craft Side Panel Controller class
 */
public class CraftSidePanelController implements ISidePanelController {

    /**
     * The control of the game UI
     */
        GameUIController gameUIController;
    /**
     * Represents the Robot button on the panel
     */
        @FXML
        Button RobotButton;
    /**
     * Represents the Teleports button on the panel
     */
        @FXML
        Button TeleportsButton;
    /**
     * Represents the Base button on the panel
     */
        @FXML
        Button BaseButton;
    /**
     * Represents the Cancel button on the panel
     */
        @FXML
        Button CancelButton;

    public CraftSidePanelController(GameUIController GUIC){
        gameUIController = GUIC;
    }

    /**
     * Initialisation for the Craft Side Panel
     */
    public void Init(){
        new SelectHandler(RobotButton);
        new SelectHandler(TeleportsButton);
        new SelectHandler(BaseButton);
        new SelectHandler(CancelButton);
        Refresh();
    }

    /**
     * Generates the command for the robot crafting
     */
    @FXML
    public void Robot(){
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " craft robot");
            gameUIController.SwitchToInventory();
            gameUIController.SwitchToActionSidePanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Generates the command for the teleports crafting
     */
    @FXML
    public void Teleports(){try {
        gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " craft teleports");
        gameUIController.SwitchToInventory();
        gameUIController.SwitchToActionSidePanel();
    } catch (Exception e) {
        e.printStackTrace();
    }}

    /**
     *  Generates the command for the base crafting
     */
    @FXML
    public void Base(){ try {
        gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " craft base");
        gameUIController.SwitchToInventory();
        gameUIController.SwitchToActionSidePanel();
    } catch (Exception e) {
        e.printStackTrace();
    } }

    /**
     * If the player clicks the Cancel button, then the Action Side Panel shows up
     */
    @FXML
    public void Cancel(){ gameUIController.SwitchToActionSidePanel(); }

    /**
     * Refreshes the Craft Side Panel if the player has enough materials for crafting something
     */
    @Override
    public void Refresh() {
        PlayerShip curr = gameUIController.getGameController().getCurrentPlayer();
        BillCreator bc = BillCreator.GetInstance();
        if(bc.CreateForRobot(curr.getMaterials()) == null){
            RobotButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            RobotButton.setEffect(null);
        }

        if(bc.CreateForTeleport(curr.getMaterials()) == null){
            TeleportsButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            TeleportsButton.setEffect(null);
        }

        if(bc.CreateForBaseFoundation(curr.getMaterials()) == null){
            BaseButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            BaseButton.setEffect(null);
        }
    }
}
