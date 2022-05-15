import Controllers.GameController;
import Controllers.TestRunner;
import UI.Components.MagicConstants;
import UI.Layout.MainMenu.MainMenuController;
import UI.Layout.GameOverMenu.GameOverMenuController;
import UI.Layout.WonMenu.WonMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import UI.Layout.Game.GameUIController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*GameController gc = new GameController();
        gc.NewMap();
        GameUIController gameUIController = new GameUIController(gc);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(gameUIController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/Game/GameLayout.fxml"));
        Parent root = null;
        root = fxmlLoader.load();
        primaryStage.setTitle("Projlab");
        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setScene(scene);
        gameUIController.Init();
        primaryStage.setFullScreen(MagicConstants.FullScreen);
        primaryStage.show();*/

        MainMenuController mainMenuController = new MainMenuController(primaryStage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(mainMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/MainMenu/MainMenuLayout.fxml"));
        Parent root = null;
        root = fxmlLoader.load();
        primaryStage.setTitle("Projlab");
        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setScene(scene);
        mainMenuController.Init();
        primaryStage.setFullScreen(MagicConstants.FullScreen);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();

        /*WonMenuController wonMenuController = new WonMenuController(primaryStage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(wonMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/WonMenu/WonMenuLayout.fxml"));
        Parent root = null;
        root = fxmlLoader.load();
        primaryStage.setTitle("Projlab");
        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setScene(scene);
        wonMenuController.Init();
        primarystage.setFullScreen(MagicConstants.FullScreen);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();*/

       /* GameOverMenuController gameOverMenuController = new GameOverMenuController(primaryStage);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(gameOverMenuController);
        fxmlLoader.setLocation(getClass().getResource("/UI/Layout/GameOverMenu/GameOverMenuLayout.fxml"));
        Parent root = null;
        root = fxmlLoader.load();
        primaryStage.setTitle("Projlab");
        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setScene(scene);
        gameOverMenuController.Init();
        primarystage.setFullScreen(MagicConstants.FullScreen);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        boolean commandLine = false;
        for(String s : args){
            if(s.equals("runtests")){
                TestRunner tc = null;
                try {
                    tc = new TestRunner("teszt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GameController gc = new GameController();
                tc.RunAllTests(gc);
            }
            else if(s.equals("cmdline")){
                commandLine = true;
            }
        }
        if(commandLine){
            Program.main(args);
        }
        else{
            launch(args);
        }
    }
}
