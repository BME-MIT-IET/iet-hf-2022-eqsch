package UI.Layout.Game.BaseInfoSidePanel;

import Model.Base;
import Model.Map;
import Model.Materials.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
/**
 * The Base Info Panel Controller class
 */
public class BaseInfoPanelController {
    /**
     * Coal label
     */
    @FXML
    Label Coal;
    /**
     * Iron label
     */
    @FXML
    Label Iron;
    /**
     * Ice label
     */
    @FXML
    Label Ice;
    /**
     * Uranium label
     */
    @FXML
    Label Uranium;

    /**
     * Shows the Base panel with the Materials
     * @param base we just built
     */
    public void Show(Base base){
        ArrayList<Material> inv = base.getMaterials();
        BillCreator bc = BillCreator.GetInstance();
        Coal.setText(Integer.toString(bc.Count(inv, new Coal(new Map()))));
        Iron.setText(Integer.toString(bc.Count(inv, new Iron(new Map()))));
        Ice.setText(Integer.toString(bc.Count(inv, new Ice(new Map()))));
        Uranium.setText(Integer.toString(bc.Count(inv, new Uranium(new Map()))));
    }

}
