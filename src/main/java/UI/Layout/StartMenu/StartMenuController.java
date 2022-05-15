package UI.Layout.StartMenu;

import Controllers.GameController;
import UI.Components.MagicConstants;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.MainMenu.MainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller of the Start Menu
 */
public class StartMenuController {
    public Stage stage;
    /**
     * it represent how many players will be
     */
    private int playerNum = 1;

    /**
     * the base of the panel
     */
    @FXML
    AnchorPane Anchor;
    /**
     * it represent the Start button
     */
    @FXML
    Button Start_button;
    /**
     * it represent the Back button
     */
    @FXML
    Button Back_button;
    /**
     * it represent the Small button
     */
    @FXML
    Button Small_button;
    /**
     * it represent the Normal button
     */
    @FXML
    Button Normal_button;
    /**
     * it represent the Large button
     */
    @FXML
    Button Large_button;
    /**
     * it represent the Counter field
     */
    @FXML
    TextField Counter_field;
    /**
     * it represent the File name field
     */
    @FXML
    TextField File_name_field;

    public StartMenuController(Stage s){
        stage = s;
    }

    public void setAnchor(AnchorPane a){
        Anchor = a;
    }

    /**
     * It's initialize the panel
     */
    public void Init(){
        new SelectHandler(Start_button);
        new SelectHandler(Back_button);
        new SelectHandler(Small_button);
        new SelectHandler(Normal_button);
        new SelectHandler(Large_button);
        Small_button.setEffect(new Glow(0.5));
    }

    /**
     * It's resposible for the number of the players
     */
    @FXML
    public void Counter(){
        if(Counter_field.getText().length() == 0){
            return;
        }
        try {
            int p;
            p = Integer.parseInt(Counter_field.getText());
            if(p < 1 || p > 5){
                int uj = Character.getNumericValue(Counter_field.getText().charAt(0));
                if(uj < 1 || uj > 5){
                    throw new Exception();
                }
                p = uj;
            }
            playerNum = p;
        }
        catch (Exception e){
            //System.out.println("Invalid value! Please enter a number between 1 and 5.");
        }
        Counter_field.setText(Integer.toString(playerNum));
        MagicConstants.setShipNumber(playerNum);
    }

    /**
     * It start the game
     */
    @FXML
    public void Start(){

        GameController gc = new GameController();
        gc.SetCurrentWorkingDirectory("\\saves");
        gc.NewMap();
        GameUIController gameUIController = new GameUIController(gc, stage);
        if(File_name_field.getText() == null) gameUIController.setFileName("save");
        else gameUIController.setFileName(File_name_field.getText());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(gameUIController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/GameLayout.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Anchor.getChildren().clear();
        Anchor.getChildren().addAll(root.getChildren());
        gameUIController.setAnchor(Anchor);
        stage.setFullScreen(MagicConstants.FullScreen);
        CleanUp();
        gameUIController.Init();

    }

    /**
     * It will bring you back to the Main Menu
     */
    @FXML
    public void Back(){
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
        CleanUp();
        mainMenuController.Init();
    }

    /**
     * The map will be small
     */
    @FXML
    public void Small(){
        Small_button.setEffect(new Glow(0.5));
        Normal_button.setEffect(null);
        Large_button.setEffect(null);
        MagicConstants.setMapSize(0);
    }

    /**
     * The map will be normal
     */
    @FXML
    public void Normal(){
        Small_button.setEffect(null);
        Normal_button.setEffect(new Glow(0.5));
        Large_button.setEffect(null);
        MagicConstants.setMapSize(1);
    }

    /**
     * The map will be large
     */
    @FXML
    public void Large(){
        Small_button.setEffect(null);
        Normal_button.setEffect(null);
        Large_button.setEffect(new Glow(0.5));
        MagicConstants.setMapSize(2);
    }

    /**
     * It's reset the panel's attributes
     */
    public void CleanUp(){
        Anchor = null;
        Start_button = null;
        Back_button = null;
        Small_button = null;
        Normal_button = null;
        Large_button = null;
        Counter_field = null;
    }
}
