package UI.Layout.Game.CurrentAsteroidSidePanel;

import Model.Asteroid;
import Model.Field;
import UI.Components.FieldImage;
import UI.Components.ImageVisitor;
import UI.Components.InfoPanelVisitor;
import UI.Components.MagicConstants;
import UI.Layout.Game.GameUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * The Current Asteroid Side Panel Controller class
 */
public class CurrentAsteroidSidePanelController {
    /**
     * The control of the game UI
     */
    GameUIController gameUIController;
    /**
     * Label for the Panel's name
     */
    @FXML
    Label NameLabel;
    /**
     * VBox for the buttons
     */
    @FXML
    VBox CurrentAsteroidPanel;
    /**
     * TextField for the Shell Field
     */
    @FXML
    TextField ShellField;
    /**
     * Anchor Pane for the Core Field
     */
    @FXML
    AnchorPane CoreField;
    /**
     * TextField for the Sector Field
     */
    @FXML
    TextField SectorField;
    /**
     * Anchor Pane for the Close Field
     */
    @FXML
    AnchorPane CloseField;
    static Image X = null;
    static Image checkmark = null;

    public CurrentAsteroidSidePanelController(GameUIController GUIC){
        gameUIController = GUIC;
    }

    public void Init(){

    }

    /**
     * Shows the Current Asteroid panel with the datas of the asteroid
     * @param f a FieldImage
     */
    public void Show(FieldImage f){
        InfoPanelVisitor ipv = new InfoPanelVisitor(f.getField());
        if (ipv.isAsteroid) {
            CoreField.getChildren().clear();
            CloseField.getChildren().clear();
            ShellField.setText(Integer.toString(ipv.Shell));
            NameLabel.setText("Asteroid" + f.getField().GetUID());
            SectorField.setText(Integer.toString(f.getField().getSector().GetUID()));

            if(ipv.Core != null) {
                ImageVisitor iv = new ImageVisitor(ipv.Core);
                ImageView MaterialImage = new ImageView(iv.getImage());
                MaterialImage.setPreserveRatio(true);
                MaterialImage.setFitHeight(MagicConstants.CoreInfoImageSize);
                AnchorPane.setRightAnchor(MaterialImage, 15.0); // + MaterialImage.getFitWidth());
                AnchorPane.setTopAnchor(MaterialImage,  9.0);
                CoreField.getChildren().add(MaterialImage);
            }
            if(((Asteroid) f.getField()).getSunClose()){
                try {
                    if(checkmark == null){

                        checkmark = new Image(new FileInputStream(new File("").getAbsolutePath()+"\\img\\checkmark.png"));
                    }
                    ImageView img = new ImageView(checkmark);
                    img.setPreserveRatio(true);
                    img.setFitHeight(MagicConstants.CoreInfoImageSize);
                    AnchorPane.setRightAnchor(img, 15.0);
                    AnchorPane.setTopAnchor(img, 9.0);
                    CloseField.getChildren().add(img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    if(X == null){
                        X = new Image(new FileInputStream(new File("").getAbsolutePath()+"\\img\\x.png"));
                    }
                    ImageView img = new ImageView(X);
                    img.setPreserveRatio(true);
                    img.setFitHeight(MagicConstants.CoreInfoImageSize);
                    AnchorPane.setRightAnchor(img, 15.0);
                    AnchorPane.setTopAnchor(img, 9.0);
                    CloseField.getChildren().add(img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(ipv.base != null){
                gameUIController.SwitchToBasePanel(ipv.base);
            }
        }
        else
            CurrentAsteroidPanel.setVisible(false);
    }

    /**
     * Cleans up the panel
     */
    public void CleanUp(){
        gameUIController = null;
        NameLabel = null;
        CurrentAsteroidPanel = null;
        ShellField = null;
        CoreField = null;
        SectorField = null;
        CloseField = null;
    }
}
