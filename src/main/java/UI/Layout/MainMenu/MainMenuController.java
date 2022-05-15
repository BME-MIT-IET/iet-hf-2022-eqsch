package UI.Layout.MainMenu;

import UI.Components.MagicConstants;
import UI.Layout.LoadMenu.LoadMenuController;
import UI.Layout.StartMenu.StartMenuController;
import UI.Components.SelectHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The Main Menu Controller class
 */
public class MainMenuController {

    /**
     *  Stage for the controller
     */
    public Stage stage;
    /**
     * Anchor Pane
     */
    @FXML
    AnchorPane Anchor;
    /**
     * Button for starting the game
     */
    @FXML
    Button Start_button;
    /**
     * Button for loading a game
     */
    @FXML
    Button Load_button;
    /**
     * Button for exit the game
     */
    @FXML
    Button Exit_button;

    public MainMenuController(Stage s){
        stage = s;
    }

    public void setAnchor(AnchorPane a){
        Anchor = a;
        Anchor.getStylesheets().clear();
        Anchor.getStylesheets().add(this.getClass().getResource("mainmenu.css").toExternalForm());
        Anchor.getStyleClass().clear();
        Anchor.getStyleClass().add("anchor");
    }

    /**
     * Initialization for the Main Menu
     */
    public void Init(){
        new SelectHandler(Start_button);
        new SelectHandler(Load_button);
        new SelectHandler(Exit_button);
    }

    /**
     * Starts the game
     */
    @FXML
    public void Start(){
        StartMenuController startMenuController = new StartMenuController(stage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(startMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/StartMenu/StartMenuLayout.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Anchor.getChildren().clear();
        Anchor.getChildren().addAll(root.getChildren());
        startMenuController.setAnchor(Anchor);
        stage.setFullScreen(MagicConstants.FullScreen);
        CleanUp();
        startMenuController.Init();
    }

    /**
     * Loads the game from saved txt
     */
    @FXML
    public void Load(){
        LoadMenuController loadMenuController = new LoadMenuController(stage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(loadMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/LoadMenu/LoadMenuLayout.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Anchor.getChildren().clear();
        Anchor.getChildren().addAll(root.getChildren());
        loadMenuController.setAnchor(Anchor);
        stage.setFullScreen(MagicConstants.FullScreen);
        CleanUp();
        loadMenuController.Init();
    }

    /**
     * The method for exit the game, closes the window
     */
    @FXML
    public void Exit(){
       stage.close();
    }

    /**
     * Cleans up the menu
     */
    public void CleanUp(){
        Anchor = null;
        Start_button = null;
        Load_button = null;
        Exit_button = null;
    }
}
