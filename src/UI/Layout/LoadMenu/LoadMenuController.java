package UI.Layout.LoadMenu;

import Controllers.GameController;
import UI.Components.MagicConstants;
import UI.Components.SelectHandler;
import UI.Layout.Game.GameUIController;
import UI.Layout.MainMenu.MainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The controller of the Load Menu
 */
public class LoadMenuController {
    /**
     * The stage of the game
     */
    public Stage stage;
    /**
     * the saved files' name
     */
    private ArrayList<String> saves = new ArrayList<>();
    /**
     * the saves files' path
     */
    private String path = new File("").getAbsolutePath() + "\\saves";
    /**
     * the current save's index
     */
    private int currentIndex = 0;
    /**
     * the base of the panel
     */
    @FXML
    AnchorPane Anchor;
    /**
     * Saved file name
     */
    @FXML
    Label Save_label;
    /**
     * Back button
     */
    @FXML
    Button Back_button;
    /**
     * Load button
     */
    @FXML
    Button Load_button;
    /**
     * Left arrow button
     */
    @FXML
    Button Left_button;
    /**
     * Right arrow button
     */
    @FXML
    Button Right_button;

    public LoadMenuController(Stage s){
        stage = s;
    }

    public void setAnchor(AnchorPane a){
        Anchor = a;
    }

    /**
     * It's initialize the panel
     */
    public void Init() {
        new SelectHandler(Back_button);
        new SelectHandler(Load_button);
        new SelectHandler(Left_button);
        new SelectHandler(Right_button);

        File dir = new File(path);
        //System.out.println(path);

        if(!dir.exists()) {
            dir.mkdir();
            //System.out.println("Invalid directory.");
        }

        File [] files = dir.listFiles();
        for(File f : files){
            saves.add(f.getName());
        }
        Save_label.setText(saves.get(currentIndex));
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
        Anchor = null;
        mainMenuController.Init();
    }

    /**
     * It will load the selected game
     */
    @FXML
    public void Load() {
        GameController gc = new GameController();
        GameUIController gameUIController = new GameUIController(gc, stage);
        gc.SetCurrentWorkingDirectory("\\saves");
        try {
            gameUIController.getGameController().InterpretCommand("load " + saves.get(currentIndex));
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
            gameUIController.Init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * you can select which saved game will be played next
     */
    @FXML
    public void Left(){
        if(currentIndex > 0) currentIndex --;
        else currentIndex = saves.size()-1;
        Save_label.setText(saves.get(currentIndex));
    }
    /**
     * you can select which saved game will be played next
     */
    @FXML
    public void Right(){
        if(currentIndex < saves.size()-1) currentIndex ++;
        else currentIndex = 0;
        Save_label.setText(saves.get(currentIndex));
    }
}
