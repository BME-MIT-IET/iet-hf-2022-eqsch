package UI.Layout.Game.PutBackSidePanel;
import Model.Map;
import Model.Materials.*;
import Model.PlayerShip;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.Game.ISidePanelController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;

public class PutBackSidePanelController implements ISidePanelController {
    /**
     * the controller of the GameUI
     */
    GameUIController gameUIController;
    /**
     * it represent the Coal button on the panel
     */
    @FXML
    Button CoalButton;
    /**
     * it represent the Iron button on the panel
     */
    @FXML
    Button IronButton;
    /**
     * it represent the Ice button on the panel
     */
    @FXML
    Button IceButton;
    /**
     * it represent the Uran button on the panel
     */
    @FXML
    Button UranButton;
    /**
     * it represent the Cancel button on the panel
     */
    @FXML
    Button CancelButton;

    public PutBackSidePanelController(GameUIController GUIC){
        gameUIController = GUIC;
    }

    /**
     * It's initialize the panel
     */
    public void Init(){
        new SelectHandler(CoalButton);
        new SelectHandler(IronButton);
        new SelectHandler(IceButton);
        new SelectHandler(UranButton);
        new SelectHandler(CancelButton);
        Refresh();
    }

    /**
     * The coal will be put back in the current asteroid
     */
    @FXML
    public void Coal(){
        BillCreator bc=BillCreator.GetInstance();
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_back " + bc.SearchCoal(gameUIController.getGameController().getCurrentPlayer().getMaterials()));
            gameUIController.SwitchToInventory();
            gameUIController.SwitchToActionSidePanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * The iron will be put back in the current asteroid
     */
    @FXML
    public void Iron(){
        BillCreator bc=BillCreator.GetInstance();
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_back " + bc.SearchIron(gameUIController.getGameController().getCurrentPlayer().getMaterials()));
            gameUIController.SwitchToInventory();
            gameUIController.SwitchToActionSidePanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * The ice will be put back in the current asteroid
     */
    @FXML
    public void Ice(){
        BillCreator bc=BillCreator.GetInstance();
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_back " + bc.SearchIce(gameUIController.getGameController().getCurrentPlayer().getMaterials()));
            gameUIController.SwitchToInventory();
            gameUIController.SwitchToActionSidePanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * The uranium will be put back in the current asteroid
     */
    @FXML
    public void Uran(){  BillCreator bc=BillCreator.GetInstance();
        try {
            gameUIController.getGameController().InterpretCommand("p " + gameUIController.getGameController().getCurrentPlayer().GetUID() + " put_back " + bc.SearchUranium(gameUIController.getGameController().getCurrentPlayer().getMaterials()));
            gameUIController.SwitchToInventory();
            gameUIController.SwitchToActionSidePanel();
        } catch (Exception e) {
            e.printStackTrace();
        } }

    /**
     * It cancel the putting back method and goes back to the Action Panel
     */
    @FXML
    public void Cancel(){
        gameUIController.SwitchToActionSidePanel();
    }

    /**
     * It's refresh the game if you click any of the buttons
     */
    @Override
    public void Refresh() {
        PlayerShip curr = gameUIController.getGameController().getCurrentPlayer();
        BillCreator bc = BillCreator.GetInstance();

        if(bc.Count(curr.getMaterials(), new Coal(new Map())) == 0){
            CoalButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            CoalButton.setEffect(null);
        }

        if(bc.Count(curr.getMaterials(), new Iron(new Map())) == 0){
            IronButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            IronButton.setEffect(null);
        }

        if(bc.Count(curr.getMaterials(), new Ice(new Map())) == 0){
            IceButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            IceButton.setEffect(null);
        }

        if(bc.Count(curr.getMaterials(), new Uranium(new Map())) == 0){
            UranButton.setEffect(new ColorAdjust(0, 1, 0, 0));
        } else{
            UranButton.setEffect(null);
        }
    }
}
