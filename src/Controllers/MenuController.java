package Controllers;

import Model.Field;
import Model.Materials.Material;
import Model.PlayerShip;
import Model.Sun;
import Utils.BadFileFormat;
import Utils.InvalidCommand;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A menu controller class, it handle the incoming commands.
 */
public class MenuController {
    GameController gc = new GameController();

    /**
     * The pre-game menu handler.
     */
    public void Start(){
        //System.out.println("Asteroid Miner");
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            String line = in.nextLine();
            String parts[] = line.split(" ");
            if(parts[0].equals("run")){
                TestRunner tc = null;
                try {
                    tc = new TestRunner("teszt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(parts[1].equals("all")){
                    tc.RunAllTests(gc);
                }
                else{
                    tc.RunTest(parts[1],gc);
                }
            }
            else if(parts[0].equals("start")){
                try {
                    gc.NewMap();
                    Game();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if(parts[0].equals("load")){
                try {
                    gc.InterpretCommand(line);
                    Game();
                }
                catch (InvalidCommand e) {
                    System.out.println(e.getMessage());
                }
                catch (BadFileFormat e) {
                    System.out.println(e.getMessage());
                }
                catch (Exception e) {
                    System.out.println("Unknown error while loading file");
                    e.printStackTrace();
                }
            }
            else if(parts[0].equals("exit")){
                exit = true;
            }
            else if(parts[0].equals("help")){
                System.out.println("Usable commands:");
                System.out.println("start -> Creates a new map and starts game");
                System.out.println("load filename -> Loads a given map and starts game");
                System.out.println("run [all]/[test number] -> Runs all the tests, and gives results or runs the specified test");
                System.out.println("exit -> Exits from game");
            }
            else{
                System.out.println("Unknown command");
            }
        }
    }

    /**
     * The in-game menu handler.
     */
    public void Game(){
        boolean exit = false;
        Scanner in = new Scanner(System.in);
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        PlayerShip last = null;
        while (!exit){
            PrintStream out = new PrintStream(System.out);
            if(last != gc.getCurrentPlayer()) {
                System.out.println("Current Player:");
                gc.getCurrentPlayer().Save(out, false);
                System.out.println("Current Asteroid:");
                gc.getCurrentPlayer().getAsteroid().Save(out, false);
                if(gc.getCurrentPlayer().getAsteroid().GetCore() != null) {
                    System.out.println("With Material:");
                    gc.getCurrentPlayer().getAsteroid().GetCore().Save(out, false);
                }
                last = gc.getCurrentPlayer();
            }
            String line = in.nextLine();
            String[] parts = line.split(" ");
            if(parts[0].equals("exit")){
                System.exit(0);
            }
            else if(parts[0].equals("menu")){
                exit = true;
            }
            else if(parts[0].equals("neighbours")){
                System.out.println("Neighbouring fields:");
                for(Field f : gc.getCurrentPlayer().getAsteroid().getNeighbours()){
                    f.Save(out,false);
                }
            }
            else if(parts[0].equals("inventory")){
                System.out.println("Player Inventory:");
                for(Material f : gc.getCurrentPlayer().getMaterials()){
                    f.Save(out,false);
                }
            }
            else if(parts[0].equals("sun")){
                Sun s = Sun.GetInstance();
                System.out.println("Next sunstorm will hit Sector" + s.getTarget().GetUID() + " in " + s.getRoundsUntillStorm() + "turns.");
            }
            else if(parts[0].equals("help")){
                System.out.println("Usable commands:");
                System.out.println("move FieldID -> Moves current player to given asteroid");
                System.out.println("drill -> Current player drills once");
                System.out.println("mine -> Current player mines");
                System.out.println("craft robot/teleports/base -> Current player crafts the specified item/s");
                System.out.println("build -> Current player uses resources to build on the base that is on the same field.");
                System.out.println("put_back MaterialID -> Attempts to put back the given material inside the asteroid the player is on.");
                System.out.println("neighbours -> List every neighbouring field of the asteroid the player currently is on.");
                System.out.println("inventory -> List every material of the player.");
                System.out.println("sun -> List the details of the next sun storm.");
                System.out.println("save filename -> saves map to the given file");
                System.out.println("exit -> Exits from game");
            }
            else if(parts[0].equals("ls") || parts[0].equals("save")){
                try {
                    gc.InterpretCommand(line);
                }
                catch (InvalidCommand e) {
                    System.out.println(e.getMessage());
                }
                catch (Exception e) {
                    System.out.println("Invalid command");
                }
            }
            else{
                try {
                    gc.InterpretCommand("p " + gc.getCurrentPlayer().GetUID() + " " +line);
                    if(!NotificationManager.LastCommandSuccess){
                        String l = NotificationManager.getError();
                        while(l != null){
                            System.out.println(ANSI_RED + l + ANSI_RESET);
                            l = NotificationManager.getError();
                        }
                        System.out.println("Command failed");
                    }
                    else{
                        String l = NotificationManager.getMessage();
                        while(l != null){
                            System.out.println(l);
                            l = NotificationManager.getMessage();
                        }
                    }
                }
                catch (InvalidCommand e) {
                    System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
                }
                catch (Exception e) {
                    System.out.println(ANSI_RED + "Invalid command" + ANSI_RESET);
                }
            }
            if(NotificationManager.GameOver)
                exit = true;
        }
    }
}
