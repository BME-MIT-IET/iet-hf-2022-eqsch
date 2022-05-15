package UI.Layout.Game.ActionSidePanel;

import Model.Materials.BillCreator;
import Model.PlayerShip;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.Game.ISidePanelController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;

import java.awt.*;

/**
 * The Action Panel's controller
 */
public class ActionSidePanelController implements ISidePanelController {
    /**
     * the control of the gameUI
     */
    GameUIController gameUIController;
    /**
     * it represent the Drill button on the panel
     */
    @FXML
    Button DrillButton;
    /**
     * it represent the Mine button on the panel
     */
    @FXML
    Button MineButton;
    /**
     * it represent the Build button on the panel
     */
    @FXML
    Button BuildButton;
    /**
     * it represent the Craft button on the panel
     */
    @FXML
    Button CraftButton;
    /**
     * it represent the PutBack button on the panel
     */
    @FXML
    Button PutBackButton;
    /**
     * it represent the PutDown button on the panel
     */
    @FXML
    Button PutDownButton;
    /**
     * it represent the Skip button on the panel
     */
    @FXML
    Button SkipButton;

    public ActionSidePanelController(GameUIController GUIC){
        gameUIController = GUIC;
    }

    /**
     * It's initialize the panel
     */
    public void Init(){
        new SelectHandler(DrillButton);
        new SelectHandler(MineButton);
        new SelectHandler(BuildButton);
        new SelectHandler(CraftButton);
        new SelectHandler(PutBackButton);
        new SelectHandler(PutDownButton);
        new SelectHandler(SkipButton);
        Refresh();
    }

    /**
     * If you click the Drill button the player will drill
     */
    @FXML
    public void Drill(){
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " drill");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If you click the Mine button the player will mine
     */
    @FXML
    public void Mine(){
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " mine");
            gameUIController.SwitchToInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * If you click the Build button the player will build the base
     */
    @FXML
    public void Build(){
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " build_base");
            gameUIController.SwitchToInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  If you click the Craft button the Craft Panel will appear
     */
    @FXML
    public void Craft(){
        gameUIController.SwitchToCraftSidePanel();
    }

    /**
     * If you click the PutBack button the PutBack Panel will appear
     */
    @FXML
    public void PutBack(){ gameUIController.SwitchToPutBackSidePanel();}

    /**
     * If you click the PutDown button the PutDown Panel will appear
     */
    @FXML
    public void PutDown(){
        gameUIController.SwitchToPutDownSidePanel();
    }

    /**
     * If you click the Skip button the turn will be skiped
     */
    @FXML
    public void Skip(){
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " skip");
            gameUIController.SwitchToInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It's refresh the game if you click any of the buttons
     */
    @Override
    public void Refresh(){
        PlayerShip curr = gameUIController.getGameController().getCurrentPlayer();
        BillCreator bc = BillCreator.GetInstance();
        if(curr != null && curr.getAsteroid() != null){

            if(curr.getAsteroid().GetShell() == 0){
                DrillButton.setEffect(new ColorAdjust(0,1,0, 0));
            } else{
                DrillButton.setEffect(null);
            }

            if(curr.getAsteroid().GetCore() == null || curr.getAsteroid().GetShell() != 0){
                MineButton.setEffect(new ColorAdjust(0, 1, 0, 0));
            } else{
                MineButton.setEffect(null);
            }

            if(curr.getAsteroid().GetBase() == null){
                BuildButton.setEffect(new ColorAdjust(0, 1, 0, 0));
            } else{
                BuildButton.setEffect(null);
            }

            if(bc.CreateForRobot(curr.getMaterials()) == null && bc.CreateForTeleport(curr.getMaterials()) == null && bc.CreateForBaseFoundation(curr.getMaterials()) == null){
                CraftButton.setEffect(new ColorAdjust(0, 1, 0, 0));
            } else{
                CraftButton.setEffect(null);
            }

            if(curr.getMaterials().size() == 0 || curr.getAsteroid().GetShell() != 0 || curr.getAsteroid().GetCore() != null){
                PutBackButton.setEffect(new ColorAdjust(0, 1, 0, 0));
            } else{
                PutBackButton.setEffect(null);
            }

            if(curr.getTeleports().size() == 0){
                PutDownButton.setEffect(new ColorAdjust(0, 1, 0, 0));
            } else{
                PutDownButton.setEffect(null);
            }
        }
    }
}
