package UI.Layout.Game;
import Controllers.GameController;
import Model.*;
import Controllers.NotificationManager;
import Model.Field;
import Model.Map;
import UI.Components.*;
import UI.Layout.Game.ActionSidePanel.ActionSidePanelController;
import UI.Layout.Game.BaseInfoSidePanel.BaseInfoPanelController;
import UI.Layout.Game.CraftSidePanel.CraftSidePanelController;
import UI.Layout.Game.CurrentTeleportSidePanel.CurrentTeleportSidePanelController;
import UI.Layout.Game.OptionsSidePanel.OptionsSidePanelController;
import UI.Layout.Game.PutBackSidePanel.PutBackSidePanelController;
import UI.Layout.Game.PutDownSidePanel.PutDownSidePanelController;
import UI.Layout.Game.CurrentAsteroidSidePanel.CurrentAsteroidSidePanelController;
import UI.Layout.Game.InventorySidePanel.InventorySidePanelController;
import UI.Layout.GameOverMenu.GameOverMenuController;
import UI.Layout.WonMenu.WonMenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The controller of the GameUI
 */
public class GameUIController implements EventHandler<KeyEvent> {
    /**
     * it contains the camerashifts
     */
    ArrayList<String> CameraShift = new ArrayList<>();
    /**
     * the controller of the side panels
     */
    ISidePanelController sidePanelController;

    /**
     * the controller of the game
     */
    GameController gameController;
    /**
     * the stage of the game
     */
    Stage stage;
    /**
     * the name of the current gameplay
     */
    String fileName;
    /**
     * it is the state of the invalidation
     */
    boolean invalidState = true;
    /**
     * the half of the pane
     */
    double PaneHalf = 1920.0/2.0;
    /**
     * the camera of the game
     */
    Camera camera = new Camera(0,0,2);
    /**
     * Sun's image
     */
    static ImageView SunImage = null;
    /**
     * it shows for the players the selected field
     */
    FieldImage selected = null;
    /**
     * Selected circle
     */
    Circle selectedCircle = null;
    /**
     * it shows which player's turn is active
     */
    Circle selectedPlayer = null;
    /**
     * it shows where the players can go
     */
    ArrayList<Circle> MovableCircle = new ArrayList<>();
    /**
     * It shows where will be sun storm
     */
    ArrayList<Circle> SunStormCircle = new ArrayList<>();
    /**
     * it contains the fieldImages
     */
    ArrayList<FieldImage> fieldImages = new ArrayList<>();
    /**
     * it contains the connections of the fields
     */
    ArrayList<Connection> connections = new ArrayList<>();
    /**
     * it contains the explosions
     */
    ArrayList<Explosion> explosions = new ArrayList<>();
    /**
     * the base of the panel
     */
    @FXML
    AnchorPane Anchor;
    /**
     * the options button
     */
    @FXML
    Button OptionsButton;
    /**
     * The side panels will be there
     */
    @FXML
    Pane SidePanelWrapper;
    /**
     * where the game is
     */
    @FXML
    Pane GameContent;
    /**
     * the inventory panel will be there
     */
    @FXML
    Pane InventoryWrapper;
    /**
     * the infos will be there
     */
    @FXML
    Pane InfoWrapper;
    /**
     * the base info side panel will be there
     */
    @FXML
    Pane BaseInfoSidePanelWrapper;
    /**
     * the controller of the base info panel
     */
    BaseInfoPanelController baseInfoPanelController;
    /**
     * it shows that the base info panel is active or not
     */
    boolean BaseInfoActive = false;
    /**
     * it contains the infos
     */
    VBox InfoPanel;
    /**
     * it shows the notifications
     */
    @FXML
    VBox NotificationVBox;
    /**
     * the timeline of the game
     */
    public Timeline timeline;
    /**
     * the controller of the current asteroid side panel
     */
    CurrentAsteroidSidePanelController currentAsteroidSidePanelController;
    /**
     * the controller of the current teleport side panel
     */
    CurrentTeleportSidePanelController currentTeleportSidePanelController;
    boolean SelectStuck = false;

    public GameUIController(GameController gc, Stage s){
        gameController = gc;
        stage = s;
    }

    public void setFileName(String fn){
        fileName = fn;
    }

    public String getFileName(){
        return fileName;
    }

    /**
     * it shows the base panel if it's exist
     * @param b
     */
    public void SwitchToBasePanel(Base b){
        BaseInfoSidePanelWrapper.setVisible(true);
        baseInfoPanelController.Show(b);
    }

    /**
     * it closes the base panel
     */
    public void CloseBasePanel(){
        BaseInfoSidePanelWrapper.setVisible(false);
    }

    /**
     * it shows the action side panel
     */
    public void SwitchToActionSidePanel(){
        SidePanelWrapper.getChildren().clear();
        ActionSidePanelController actionSidePanelController = new ActionSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(actionSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/ActionSidePanel/ActionSidePanel.fxml"));
        try {
            VBox actionPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(actionPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        actionSidePanelController.Init();
        sidePanelController = actionSidePanelController;
        OptionsButton.setVisible(true);
    }

    /**
     * it shows the craft side panel
     */
    public void SwitchToCraftSidePanel(){
        SidePanelWrapper.getChildren().clear();
        CraftSidePanelController craftSidePanelController = new CraftSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(craftSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/CraftSidePanel/CraftSidePanel.fxml"));
        try {
            VBox craftPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(craftPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        craftSidePanelController.Init();
        OptionsButton.setVisible(true);
    }

    /**
     * it shows the option side panel
     */
    public void SwitchToOptionsSidePanel(){
        SidePanelWrapper.getChildren().clear();
        OptionsSidePanelController optionsSidePanelController = new OptionsSidePanelController(this, stage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(optionsSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/OptionsSidePanel/OptionsSidePanelLayout.fxml"));
        try {
            VBox optionsPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(optionsPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        optionsSidePanelController.setAnchor(Anchor);
        optionsSidePanelController.Init();
        OptionsButton.setVisible(false);
    }

    /**
     * it shows the current asteroid side panel
     */
    public void SwitchToCurrentAsteroidSidePanel(){
        SidePanelWrapper.getChildren().clear();
        CurrentAsteroidSidePanelController currentAsteroidSidePanelController = new CurrentAsteroidSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(currentAsteroidSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/CurrentAsteroidSidePanel/CurrentAsteroidSidePanel.fxml"));
        try {
            VBox CurrentAsteroidPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(CurrentAsteroidPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentAsteroidSidePanelController.Init();
    }

    /**
     * it shows the current teleport side panel
     */
    public void SwitchToCurrentTeleportSidePanel(){
        SidePanelWrapper.getChildren().clear();
        CurrentTeleportSidePanelController currentTeleportSidePanelController = new CurrentTeleportSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(currentTeleportSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/CurrentAsteroidSidePanel/CurrentTeleportSidePanel.fxml"));
        try {
            VBox CurrentTeleportPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(CurrentTeleportPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTeleportSidePanelController.Init();
    }

    /**
     * it shows the put down side panel
     */
    public void SwitchToPutDownSidePanel(){
        SidePanelWrapper.getChildren().clear();
        PutDownSidePanelController putDownSidePanelController = new PutDownSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(putDownSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/PutDownSidePanel/PutDownSidePanel.fxml"));
        try {
            VBox putdownPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(putdownPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        putDownSidePanelController.Init();
    }

    /**
     * it shows the put back side panel
     */
    public void SwitchToPutBackSidePanel(){
        SidePanelWrapper.getChildren().clear();
        PutBackSidePanelController putBackSidePanelController = new PutBackSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(putBackSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/PutBackSidePanel/PutBackSidePanel.fxml"));
        try {
            VBox putbackPanel = fxmlLoader.load();
            SidePanelWrapper.getChildren().add(putbackPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        putBackSidePanelController.Init();
    }

    /**
     * it shows the inventory side panel
     */
    public void SwitchToInventory(){
        InventoryWrapper.getChildren().clear();
        InventorySidePanelController inventorySidePanelController = new InventorySidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(inventorySidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/InventorySidePanel/InventorySidePanel.fxml"));
        try {
            GridPane inventoryPanel = fxmlLoader.load();
            InventoryWrapper.getChildren().add(inventoryPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventorySidePanelController.Init();
    }

    /**
     * it shows the asteroid info panel
     */
    public void SwitchToAsteroidInfo(){
        if(currentTeleportSidePanelController != null){
            currentTeleportSidePanelController.CleanUp();
            currentTeleportSidePanelController = null;
        }
        InfoWrapper.getChildren().clear();
        currentAsteroidSidePanelController = new CurrentAsteroidSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(currentAsteroidSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/CurrentAsteroidSidePanel/CurrentAsteroidSidePanel.fxml"));
        try {
            InfoPanel = fxmlLoader.load();
            currentAsteroidSidePanelController.Init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InfoWrapper.getChildren().add(InfoPanel);
        //TeleportInfoPanel.setVisible(false);
        InfoWrapper.setVisible(false);
    }

    /**
     * it shows the Game over stage
     */
    public void SwitchToGameOver(){
        Anchor.getChildren().clear();
        GameOverMenuController gameOverMenuController=new GameOverMenuController(stage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(gameOverMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/GameOverMenu/GameOverMenuLayout.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Anchor.getChildren().addAll(root.getChildren());
        gameOverMenuController.setAnchor(Anchor);
        CleanUp();
        gameOverMenuController.Init();
    }

    /**
     * it shows the Won stage
     */
    public void SwitchToWon(){
        Anchor.getChildren().clear();
        WonMenuController wonMenuController=new WonMenuController(stage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(wonMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/WonMenu/WonMenuLayout.fxml"));
        AnchorPane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Anchor.getChildren().addAll(root.getChildren());
        wonMenuController.setAnchor(Anchor);
        CleanUp();
        wonMenuController.Init();
    }

    /**
     * it shows the teleport info panel
     */
    public void SwitchToTeleportInfo(){
        if(currentAsteroidSidePanelController != null){
            currentAsteroidSidePanelController.CleanUp();
            currentAsteroidSidePanelController = null;
        }
        InfoWrapper.getChildren().clear();
        currentTeleportSidePanelController = new CurrentTeleportSidePanelController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(currentTeleportSidePanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/CurrentTeleportSidePanel/CurrentTeleportSidePanel.fxml"));
        try {
            InfoPanel = fxmlLoader.load();
            currentTeleportSidePanelController.Init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InfoWrapper.getChildren().add(InfoPanel);
        //TeleportInfoPanel.setVisible(false);
        InfoWrapper.setVisible(false);
    }


    public GameController getGameController(){
        return gameController;
    }

    public void setAnchor(AnchorPane a){
        Anchor = a;
        Anchor.getStylesheets().clear();
        Anchor.getStylesheets().add(this.getClass().getResource("game.css").toExternalForm());
        Anchor.getStyleClass().clear();
        Anchor.getStyleClass().add("anchor");
    }

    /**
     * It's initializing the game
     */
    public void Init(){
        NotificationManager.setGameOver(false);
        NotificationManager.setPlayersWon(false);
        NotificationManager.ClearAll();
        SwitchToActionSidePanel();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(17),
                ae -> Refresh()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        new SelectHandler(OptionsButton);
        Anchor.addEventHandler(KeyEvent.KEY_PRESSED,this);
        Anchor.addEventHandler(KeyEvent.KEY_RELEASED,this);
        SwitchToInventory();

        baseInfoPanelController = new BaseInfoPanelController();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(baseInfoPanelController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/BaseInfoSidePanel/BaseInfoSidePanel.fxml"));
        try {
            VBox baseinfostuff = fxmlLoader.load();
            BaseInfoSidePanelWrapper.getChildren().addAll(baseinfostuff);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseInfoSidePanelWrapper.setVisible(false);
    }

    /**
     * the Field's x coordinate
     * @param x
     * @return
     */
    private double FieldX(double x){
        return GameContent.getWidth()/2.0 + camera.TransformX(x) * PaneHalf;
    }

    /**
     * the Field's y coordinate
     * @param y
     * @return
     */
    private double FieldY(double y){
        return GameContent.getHeight()/2.0 + camera.TransformY(y) * PaneHalf;
    }

    /**
     * It places the current ship in the middle of the screen
     * @param image
     */
    private void PositionField(FieldImage image){
        image.setFitWidth(camera.TransformWidth(image.size));
        image.setFitHeight(camera.TransformHeight(image.size));
        double posx = FieldX(image.x) - (camera.TransformWidth(image.size)/2);
        double posy = FieldY(image.y) - (camera.TransformWidth(image.size)/2);
        ////System.out.println(image.getFitWidth());
        image.relocate(posx,posy);
        GridPane ships = image.getShips();
        if(ships != null) {
            double ShipPosX = FieldX(image.x) + (camera.TransformWidth(image.size) / 2);
            double ShipPosY = FieldY(image.y) - (camera.TransformHeight(image.size) / 2);
            ships.relocate(ShipPosX, ShipPosY);
            boolean selectedAdd = false;
            int x = 0;
            ShipImage selected = null;
            for (Node n : ships.getChildren()) {
                if(n.toString().equals("ShipImage")) {
                    ShipImage s = (ShipImage) n;
                    s.setFitWidth(camera.TransformWidth(s.size));
                    s.setFitHeight(camera.TransformHeight(s.size));
                    if(gameController.getCurrentPlayer() != null && gameController.getCurrentPlayer().getAsteroid() != null) {
                        PlayerShip curr = gameController.getCurrentPlayer();
                        if (s.getShip().GetUID() == curr.GetUID()) {
                            selected = s;
                            x = GridPane.getColumnIndex(s);
                            selectedPlayer = new Circle();
                            selectedPlayer.setRadius(s.getFitWidth() / 2.0);
                            selectedPlayer.setFill(Color.WHITE);
                            selectedAdd = true;
                        }
                    }
                }
            }
            if(selectedAdd) {
                ArrayList<Node> ToBeDeleted = new ArrayList<>();
                for(Node n : ships.getChildren()){
                    if(GridPane.getColumnIndex(n) == x)
                        ToBeDeleted.add(n);
                }
                for(Node n : ToBeDeleted)
                    ships.getChildren().remove(n);
                ships.add(selectedPlayer, x, 0);
                ships.add(selected,x,0);
            }
        }
        ImageView base = image.getBase();
        if(base != null){
            base.setFitWidth(camera.TransformWidth(MagicConstants.BaseImageSize));
            base.setFitHeight(camera.TransformHeight(MagicConstants.BaseImageSize));
            double basePosX = FieldX(image.x) - (camera.TransformWidth(MagicConstants.BaseImageSize)/2);
            double basePosY = FieldY(image.y) - (camera.TransformHeight(image.size) / 2) - (camera.TransformHeight(MagicConstants.BaseImageSize)) + 5.0;
            base.relocate(basePosX, basePosY);
        }
    }

    /**
     * Places the field images
     * @param image
     */
    private void PlaceField(FieldImage image){
        ////System.out.println(posx + " " + posy);
        GameContent.getChildren().add(image);
        image.addEventHandler(MouseEvent.ANY,new FieldImageMouseHandler(image,this));
        GridPane ships = image.getShips();
        if(ships != null){
            GameContent.getChildren().add(ships);
            for (Node n : ships.getChildren()) {
                if(n.toString().equals("ShipImage")) {
                    ShipImage s = (ShipImage) n;
                    s.setFitWidth(camera.TransformWidth(s.size));
                    s.setFitHeight(camera.TransformHeight(s.size));
                }
            }
        }
        ImageView base = image.getBase();
        if(base != null){
            GameContent.getChildren().add(base);
        }
        PositionField(image);
    }

    /**
     * places the sun image
     */
    private void PlaceSun(){
        if(SunImage == null){
            String filePath = new File("").getAbsolutePath();
            filePath+="\\img\\";
            filePath += "Sun.png";
            try {
                SunImage = new ImageView(new Image(new FileInputStream(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(!GameContent.getChildren().contains(SunImage))
            GameContent.getChildren().add(SunImage);
        SunImage.setFitWidth(MagicConstants.SunDiameter);
        SunImage.setFitHeight(MagicConstants.SunDiameter);
        SunImage.setPreserveRatio(true);
        SunImage.setFitWidth(camera.TransformWidth(SunImage.getFitWidth()));
        SunImage.setFitHeight(camera.TransformHeight(SunImage.getFitHeight()));
        double posx = FieldX(0) - (SunImage.getFitWidth()/2);
        double posy = FieldY(0) - (SunImage.getFitHeight()/2);
        ////System.out.println(image.getFitWidth());
        SunImage.relocate(posx,posy);
        ////System.out.println(posx + " " + posy);
    }


    /**
     * it calculates the connections between the fields
     * @param connection
     */
    private void PositionConnection(Connection connection){
        FieldImage f1 = new FieldImage(connection.getF1());
        double f1posx = FieldX(f1.x);
        double f1posy = FieldY(f1.y);
        FieldImage f2 = new FieldImage(connection.getF2());
        double f2posx = FieldX(f2.x);
        double f2posy = FieldY(f2.y);
        connection.line.setStartX(f1posx);
        connection.line.setStartY(f1posy);
        connection.line.setEndX(f2posx);
        connection.line.setEndY(f2posy);
    }

    /**
     * the selected field's background will be white
     */
    public void PositionSelectedCircle(){
        if(selected != null) {
            GameContent.getChildren().remove(selectedCircle);
            selectedCircle = new Circle();
            selectedCircle.setCenterX(FieldX(selected.x));
            selectedCircle.setCenterY(FieldY(selected.y));
            selectedCircle.setRadius(selected.getFitWidth() / 2.0 * 1.2);
            selectedCircle.setFill(Color.WHITE);
            GameContent.getChildren().add(0, selectedCircle);
        }
    }

    /**
     * the selected field's neighbours' background will be blue
     */
    public void PositionMovableCircles(){
        GameContent.getChildren().removeAll(MovableCircle);
        MovableCircle.clear();
        if(gameController.getCurrentPlayer() != null && gameController.getCurrentPlayer().getAsteroid() != null){
            for (Field f : gameController.getCurrentPlayer().getAsteroid().getNeighbours()) {
                Circle Add = new Circle();
                FieldImage field = new FieldImage(f);
                Add.setCenterX(FieldX(field.x));
                Add.setCenterY(FieldY(field.y));
                Add.setRadius(camera.TransformWidth(field.size) / 2.0 * 1.2);
                Add.setFill(Color.AQUA);
                ////System.out.println("Movable circle x" + Add.getCenterX() + " y" + Add.getCenterY() + " r" + Add.getRadius());
                GameContent.getChildren().add(0, Add);
                MovableCircle.add(Add);
            }
        }
    }

    /**
     * the sector's background where the sunstorm will be will be red
     */
    public void PositionSunStormCircles(){
        GameContent.getChildren().removeAll(SunStormCircle);
        SunStormCircle.clear();
        for (Field f : Sun.GetInstance().getTarget().getFields()) {
            Circle Add = new Circle();
            FieldImage field = new FieldImage(f);
            Add.setCenterX(FieldX(field.x));
            Add.setCenterY(FieldY(field.y));
            Add.setRadius(camera.TransformWidth(field.size) / 2.0 * 1.4);
            Add.setFill(Color.RED);
            ////System.out.println("Movable circle x" + Add.getCenterX() + " y" + Add.getCenterY() + " r" + Add.getRadius());
            GameContent.getChildren().add(0, Add);
            SunStormCircle.add(Add);
        }
    }

    /**
     * it's responsible the boom effect
     * @param explosion
     */
    private void PositionExplosion(Explosion explosion){
        ImageView image = explosion.getImage();
        image.setFitHeight(camera.TransformHeight(explosion.getHeight()));
        image.setFitWidth(camera.TransformWidth(explosion.getWidth()));
        double posx = FieldX(explosion.getX()) - (camera.TransformWidth(explosion.getWidth())/2);
        double posy = FieldY(explosion.getY()) - (camera.TransformHeight(explosion.getHeight())/2);
        if(!GameContent.getChildren().contains(image)){
            GameContent.getChildren().add(image);
        }
        image.relocate(posx,posy);
        //System.out.println("Explosion at: x" + posx + " y" + posy);
    }

    /**
     * it places the connections
     * @param connection
     */
    private void PlaceConnection(Connection connection){
        Line line = new Line();
        if(gameController.getCurrentPlayer() != null && gameController.getCurrentPlayer().getAsteroid() != null) {
            if (connection.getF1() == gameController.getCurrentPlayer().getAsteroid() || connection.getF2() == gameController.getCurrentPlayer().getAsteroid())
                line.setStyle("-fx-stroke: aqua;");
            else if (connection.teleports)
                line.setStyle("-fx-stroke: green;");
            else
                line.setStyle("-fx-stroke: yellow;");
            connection.line = line;
            PositionConnection(connection);
            GameContent.getChildren().add(0, line);
        }
    }

    /**
     * Player ship moves to the clicked asteroid
     * @param f
     */
    public void Move(FieldImage f){
        //System.out.println("Move to " + f.getField().GetUID());
        try {
            gameController.InterpretCommand("p " + gameController.getCurrentPlayer().GetUID() + " move " + f.getField().GetUID());
            SwitchToInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * it shows the current field's infos
     * @param image
     */
    public void Select(FieldImage image){
        selected = image;
        PositionSelectedCircle();
        InfoPanelVisitor ipv = new InfoPanelVisitor(image.getField());
        if(ipv.isAsteroid) {
            SwitchToAsteroidInfo();
            currentAsteroidSidePanelController.Show(image);
            InfoPanel.setVisible(true);
        }
        else{
            SwitchToTeleportInfo();
            currentTeleportSidePanelController.Show(image);
            InfoPanel.setVisible(true);
        }
        //TeleportInfoPanel.setVisible(true);
        InfoWrapper.setVisible(true);
        if(InFrontOfField(image)){
            SelectStuck = true;
        }
    }

    /**
     * It returns if we are inside of the info wrapper
     * @param x
     * @param y
     * @return
     */
    private boolean Inside(double x,double y){
        return (InfoWrapper.getLayoutX() <= x && x <= InfoWrapper.getLayoutX() + InfoWrapper.getWidth())
                && (InfoWrapper.getLayoutY() <= y && y <= InfoWrapper.getLayoutY() + InfoWrapper.getHeight());
    }

    /**
     * @param f
     * @return
     */
    private boolean InFrontOfField(FieldImage f){
        return Inside(f.getLayoutX(),f.getLayoutY())
                || Inside(f.getLayoutX() + f.getFitWidth(),f.getLayoutY())
                || Inside(f.getLayoutX() + f.getFitWidth(),f.getLayoutY() + f.getFitHeight())
                || Inside(f.getLayoutX(),f.getLayoutY() + f.getFitHeight());
    }

    /**
     * Changes the field's background if the mouse is on it
     * @param f
     * @param Force
     * @param x
     * @param y
     */
    public void Deselect(FieldImage f, boolean Force, double x ,double y){
        if(f == selected) {
            if((!InFrontOfField(f)) || Force){
                selected = null;
                if(!invalidState)
                    GameContent.getChildren().remove(selectedCircle);
                InfoPanel.setVisible(false);
                InfoWrapper.setVisible(false);
                CloseBasePanel();
                SelectStuck = false;
            }
            else{
                SelectStuck = true;
                if(!Inside(x,y))
                    Deselect(f,true,0,0);
            }
        }
    }

    /**
     * Refreshes the notification pane with the new messages
     */
    private void NotificationRefresh(){
        String l = NotificationManager.getError();
        while(l != null){
            Label error = new Label(l);
            error.setPrefWidth(12 * l.length());
            error.setMinWidth(error.getPrefWidth());
            error.setMaxWidth(error.getPrefWidth());
            error.setMinHeight(error.getPrefHeight());
            error.setMaxHeight(error.getPrefHeight());;
            error.getStylesheets().add(this.getClass().getResource("game.css").toExternalForm());
            error.getStyleClass().add("error");
            new Notification(error,NotificationVBox,MagicConstants.NotificationFadeStart,MagicConstants.NotificationDuration,Color.RED);
            l = NotificationManager.getError();
        }

        l = NotificationManager.getMessage();
        while(l != null){
            Label msg = new Label(l);
            msg.setPrefWidth(12 * l.length());
            msg.setMinWidth(msg.getPrefWidth());
            msg.setMaxWidth(msg.getPrefWidth());
            msg.setMinHeight(msg.getPrefHeight());
            msg.setMaxHeight(msg.getPrefHeight());;
            msg.getStylesheets().add(this.getClass().getResource("game.css").toExternalForm());
            msg.getStyleClass().add("msg");
            new Notification(msg,NotificationVBox,MagicConstants.NotificationFadeStart,MagicConstants.NotificationDuration,Color.AQUA);
            l = NotificationManager.getMessage();
        }

        l = NotificationManager.getWarning();
        while(l != null){
            Label error = new Label(l);
            error.setPrefWidth(12 * l.length());
            error.setMinWidth(error.getPrefWidth());
            error.setMaxWidth(error.getPrefWidth());
            error.setMinHeight(error.getPrefHeight());
            error.setMaxHeight(error.getPrefHeight());;
            error.getStylesheets().add(this.getClass().getResource("game.css").toExternalForm());
            error.getStyleClass().add("warning");
            new Notification(error,NotificationVBox,MagicConstants.NotificationFadeStart,MagicConstants.NotificationDuration,Color.YELLOW);
            l = NotificationManager.getWarning();
            }

        if(NotificationManager.getGameOver()){
            SwitchToGameOver();
        }

        if(NotificationManager.getPlayersWon()){
            SwitchToWon();
        }

        TeleportGate t = NotificationManager.getNewTeleport();
        if(t != null){
            FieldImage newT = new FieldImage(t);
            fieldImages.add(newT);
            ArrayList<Connection> newConnections = new ArrayList<>();
            for (Field n : t.getNeighbours()) {
                Connection c = new Connection(t, n);
                boolean found = false;
                for (Connection connection : connections) {
                    if (connection.isSame(c)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    connections.add(c);
                    newConnections.add(c);
                }
            }
            if(t.getPair() != null && t.isActive()){
                Connection c = new Connection(t, t.getPair());
                c.teleports = true;
                connections.add(c);
                newConnections.add(c);
            }
            for(Connection c : newConnections){
                PlaceConnection(c);
            }
            PlaceField(newT);
        }
        Asteroid a = NotificationManager.getExplodedAsteroid();
        if(a != null){
            Explosion explosion = new Explosion(a,MagicConstants.ExplosionDuration,MagicConstants.ExplosionGrowth);
            explosions.add(explosion);
            GameContent.getChildren().add(explosion.getImage());
            PositionExplosion(explosion);
        }

    }

    /**
     * handles the explosions, deletes them if they are done.
     */
    public void HandleExplosions(){
        ArrayList<Explosion> Delete = new ArrayList<>();
        for(Explosion e : explosions){
            if(e.isDone()){
                Delete.add(e);
                GameContent.getChildren().remove(e.getImage());
            }
            else{
                PositionExplosion(e);
            }
        }
        explosions.removeAll(Delete);
    }

    /**
     * refreshes the games current state, moves the camera, draws the elements, calls handles
     */
    public void Refresh(){
        NotificationRefresh();
        if(NotificationManager.getLastCommandSuccess()){
            Invalidate();
            NotificationManager.setLastCommandSuccess(false);
        }
        final double CamShift = 0.015;
        final double CamZoom = 0.98;
        if (GameContent.getWidth() >= GameContent.getHeight())
            PaneHalf = GameContent.getWidth() / 2.0;
        else
            PaneHalf = GameContent.getHeight() / 2.0;

        // camera moves / zooms
        for (String s : CameraShift) {
            if (s.equals("Left"))
                camera.setX(camera.getX() - (CamShift / camera.getZoom()));
            else if (s.equals("Right"))
                camera.setX(camera.getX() + (CamShift / camera.getZoom()));
            else if (s.equals("Up"))
                camera.setY(camera.getY() - (CamShift / camera.getZoom()));
            else if (s.equals("Down"))
                camera.setY(camera.getY() + (CamShift / camera.getZoom()));
            else if (s.equals("Zoom"))
                camera.setZoom(camera.getZoom() * CamZoom);
            else if (s.equals("Unzoom"))
                camera.setZoom(camera.getZoom() / CamZoom);
        }

        // if something called for invalidate, refreshes the elemnts
        if(invalidState) {
            sidePanelController.Refresh();
            //System.out.println("Invalidated");
            selectedPlayer = null;
            Map map = gameController.getMap();
            GameContent.getChildren().clear();
            fieldImages = new ArrayList<>();
            connections = new ArrayList<>();
            for (Sector s : map.getSectors()) {
                for (Field f : s.getFields()) {
                    FieldImage FI = new FieldImage(f);
                    fieldImages.add(FI);
                    for (Field n : f.getNeighbours()) {
                        Connection c = new Connection(f, n);
                        boolean found = false;
                        for (Connection connection : connections) {
                            if (connection.isSame(c)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found)
                            connections.add(c);
                    }
                    if(FI.getPair() != null){
                        TeleportGate t = (TeleportGate) FI.getField();
                        if(t.isActive()) {
                            Connection c = new Connection(t, t.getPair());
                            connections.add(c);
                            c.teleports = true;
                        }
                    }
                }
            }
            PositionSelectedCircle();
            for (Connection c : connections) {
                PlaceConnection(c);
            }
            for (FieldImage f : fieldImages) {
                PlaceField(f);
            }
            PlaceSun();
            invalidState = false;
            if(gameController.getCurrentPlayer() != null && gameController.getCurrentPlayer().getAsteroid() != null){
                camera.setX(gameController.getCurrentPlayer().getAsteroid().getX());
                camera.setY(gameController.getCurrentPlayer().getAsteroid().getY());
            }
        }
        // else, handles the changes
        else {
            HandleExplosions();
            PositionSelectedCircle();
            PositionMovableCircles();
            PositionSunStormCircles();
            for (Connection c : connections) {
                PositionConnection(c);
            }
            for (FieldImage f : fieldImages) {
                PositionField(f);
            }
            PlaceSun();
        }
    }

    /**
     * sets the invalidState's value to true, it invalidates
     */
    public void Invalidate(){
        invalidState = true;
    }

    /**
     * handles the camera moving and zooming
     * @param keyEvent the key event
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
            switch ((keyEvent.getCode())){
                case A:
                    if(!CameraShift.contains("Left"))
                        CameraShift.add("Left");
                    break;
                case D:
                    if(!CameraShift.contains("Right"))
                        CameraShift.add("Right");
                    break;
                case W:
                    if(!CameraShift.contains("Up"))
                        CameraShift.add("Up");
                    break;
                case S:
                    if(!CameraShift.contains("Down"))
                        CameraShift.add("Down");
                    break;
                case Q:
                    if(!CameraShift.contains("Zoom"))
                        CameraShift.add("Zoom");
                    break;
                case E:
                    if(!CameraShift.contains("Unzoom"))
                        CameraShift.add("Unzoom");
                    break;
            }
        if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
            switch ((keyEvent.getCode())){
                case A:
                    CameraShift.remove("Left");
                    break;
                case D:
                    CameraShift.remove("Right");
                    break;
                case W:
                    CameraShift.remove("Up");
                    break;
                case S:
                    CameraShift.remove("Down");
                    break;
                case Q:
                    CameraShift.remove("Zoom");
                    break;
                case E:
                    CameraShift.remove("Unzoom");
                    break;
            }
    }

    /**
     * cleans up the timeline and the eventhandler
     */
    public void CleanUp(){
        Anchor.removeEventHandler(KeyEvent.KEY_PRESSED,this);
        Anchor.removeEventHandler(KeyEvent.KEY_RELEASED,this);
        timeline.stop();
        timeline = null;
        Anchor = null;
    }

    /**
     * deselects a panel, preventing glitches
     */
    @FXML
    public void UnstuckSelect(){
        if(SelectStuck) {
            Deselect(selected,true,0,0);
        }
    }
}
