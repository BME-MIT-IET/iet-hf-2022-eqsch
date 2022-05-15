package UI.Layout.GameOverMenu;

import UI.Components.MagicConstants;
import UI.Components.SelectHandler;
import UI.Layout.LoadMenu.LoadMenuController;
import UI.Layout.MainMenu.MainMenuController;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The Game Over Menu Controller class
 */
public class GameOverMenuController {
    /**
     * Stage for the controller
     */
    public Stage stage;
    /**
     * Anchor Pane
     */
    @FXML
    AnchorPane Anchor;
    /**
     * Button for restarting the game
     */
    @FXML
    Button Restart_button;
    /**
     * Button for exit the game
     */
    @FXML
    Button Exit_button;

    public GameOverMenuController(Stage s){
        stage = s;
    }

    /**
     * Shows the game over pane
     * @param a an Anchor Pane
     */
    public void setAnchor(AnchorPane a){
        Anchor = a;
        Anchor.getStylesheets().clear();
        Anchor.getStylesheets().add(this.getClass().getResource("gameover.css").toExternalForm());
        Anchor.getStyleClass().clear();
        Anchor.getStyleClass().add("anchor");
    }

    /**
     * Initialization for the Game Over Menu
     */
    public void Init(){
        new SelectHandler(Restart_button);
        new SelectHandler(Exit_button);
    }

    /**
     * Restarts the game with the same options
     */
    @FXML
    public void Restart(){
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
}
