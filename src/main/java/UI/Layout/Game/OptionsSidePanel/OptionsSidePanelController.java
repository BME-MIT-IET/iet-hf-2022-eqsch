package UI.Layout.Game.OptionsSidePanel;

import Controllers.NotificationManager;
import UI.Components.MagicConstants;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.MainMenu.MainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import static java.lang.System.exit;

/**
 * The Options Side Panel Controller class
 */
public class OptionsSidePanelController {

    /**
     * The control of the game UI
     */
    GameUIController gameUIController;
    /**
     * Stage for the controller
     */
    Stage stage;
    /**
     * Anchor Pane
     */
    AnchorPane Anchor;
    /**
     * Represents the Save button on the panel
     */
    @FXML
    Button SaveButton;
    /**
     * Represents the Menu button on the panel
     */
    @FXML
    Button MenuButton;
    /**
     * Represents the Exit button on the panel
     */
    @FXML
    Button ExitButton;
    /**
     * Represents the Cancel button on the panel
     */
    @FXML
    Button CancelButton;

    public OptionsSidePanelController(GameUIController GUIC, Stage s){
        gameUIController = GUIC;
        stage = s;
    }

    public void setAnchor(AnchorPane a){
        Anchor = a;
    }

    /**
     *  Initialization for the Options Side Panel
     */
    public void Init(){
        new SelectHandler(SaveButton);
        new SelectHandler(MenuButton);
        new SelectHandler(ExitButton);
        new SelectHandler(CancelButton);
    }

    /**
     * The method for saving the game
     */
    @FXML
    public void Save(){
        try {
            NotificationManager.AddWarning("Game saved!");
            gameUIController.getGameController().InterpretCommand("save " + gameUIController.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method for the Main Menu
     */
    @FXML
    public void Menu(){
        gameUIController.CleanUp();
        MainMenuController mainMenuController = new MainMenuController(stage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(mainMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/MainMenu/MainMenuLayout.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Anchor.getChildren().clear();
        Anchor.getChildren().addAll(root.getChildren());
        mainMenuController.setAnchor(Anchor);
        stage.setFullScreen(MagicConstants.FullScreen);
        mainMenuController.Init();
    }

    /**
     * The method for exit the game, closes the window
     */
    @FXML
    public void Exit(){
        stage.close();
    }

    /**
     * The method for cancel the current panel, switch to the Action Side Panel
     */
    @FXML
    public void Cancel(){ gameUIController.SwitchToActionSidePanel(); }
}
