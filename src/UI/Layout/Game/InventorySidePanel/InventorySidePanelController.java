package UI.Layout.Game.InventorySidePanel;

import Model.Materials.*;
import UI.Layout.Game.GameUIController;
import Model.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * The Inventory Side Panel Controller class
 */
public class InventorySidePanelController {
    /**
     * The control of the game UI
     */
    GameUIController gameUIController;
    /**
     * Label for Coal Number
     */
    @FXML
    Label CoalNumb;
    /**
     *Label for Iron Number
     */
    @FXML
    Label IronNumb;
    /**
     * Label for Ice Number
     */
    @FXML
    Label IceNumb;
    /**
     * Label for Uranium Number
     */
    @FXML
    Label UranNumb;
    /**
     * Label for Teleportgate Number
     */
    @FXML
    Label PortalNumb;

    public InventorySidePanelController(GameUIController GUIC){
        gameUIController = GUIC;
    }

    /**
     * Initialization for the panel
     */
    public void Init(){
        Refresh();
    }

    /**
     *Refreshes the Inventory Side Panel if the player mined, or crafted something
     */
    public void Refresh() {
        ArrayList<Material> inv = gameUIController.getGameController().getCurrentPlayer().getMaterials();
        BillCreator bc = BillCreator.GetInstance();
        CoalNumb.setText(Integer.toString(bc.Count(inv, new Coal(new Map()))));
        IronNumb.setText(Integer.toString(bc.Count(inv, new Iron(new Map()))));
        IceNumb.setText(Integer.toString(bc.Count(inv, new Ice(new Map()))));
        UranNumb.setText(Integer.toString(bc.Count(inv, new Uranium(new Map()))));
        PortalNumb.setText(Integer.toString(gameUIController.getGameController().getCurrentPlayer().getTeleports().size()));
    }
}
