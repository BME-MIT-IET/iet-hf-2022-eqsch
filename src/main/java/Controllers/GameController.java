package Controllers;

import Model.*;
import Model.Materials.Material;
import Model.Materials.Uranium;
import UI.Components.Notification;
import Utils.InvalidCommand;

import java.io.File;
import java.util.ArrayList;

/**
 * A controller class, which handles the in-game commands.
 */
public class GameController {
    // the players
    ArrayList<PlayerShip> ps = new ArrayList<>();
    // the robots
    ArrayList<RobotShip> rs = new ArrayList<>();
    // the ufos
    ArrayList<UFO> ufos = new ArrayList<>();
    // the teleport gates
    ArrayList<TeleportGate> tgs = new ArrayList<>();
    // the uraniums
    ArrayList<Uranium> urans = new ArrayList<>();
    // the map
    Map map=null;
    // controller on/off switch
    boolean controller = true;
    // the currently active working directory
    String CurrentWorkingDirectory = System.getProperty("user.dir");
    //the current player
    private PlayerShip CurrentPlayer;

    public void SetCurrentWorkingDirectory(String CWD){
        CurrentWorkingDirectory += CWD;
    }

    // getter for the current player
    public PlayerShip getCurrentPlayer(){
        if(CurrentPlayer==null){
            if(ps.size() == 0){
                NotificationManager.PlayersLost();
                return null;
            }
            CurrentPlayer = ps.get(0);
        }
        if(CurrentPlayer.getAsteroid() == null){
            ps.remove(CurrentPlayer);
            CurrentPlayer = null;
            return getCurrentPlayer();
        }
        return CurrentPlayer;
    }
    // setter for the current player
    void setCurrentPlayer(PlayerShip p){
        CurrentPlayer = p;
    }

    // setter for the controller switch
    void setController(boolean val){
        controller = val;
    }

    //getter of the map
    public Map getMap(){return map;}

    /**
     * It handles the in-game command.
     * @param CommandLine the given command line
     * @throws Exception
     */
    public void InterpretCommand(String CommandLine) throws Exception {
        String[] parts = CommandLine.split(" ");

        if (parts[0].equals("@Fail")){
            boolean Failed = false;
            try {
                String LineWithOutFlag = parts[1] + " ";
                for(int i=2;i<parts.length;i++) {
                    LineWithOutFlag += parts[i];
                    if(i < parts.length-1)
                        LineWithOutFlag += " ";
                }
                InterpretCommand(LineWithOutFlag);
            } catch (InvalidCommand e){
                Failed = true;
            }
            if(!Failed) {
                throw (new InvalidCommand(CommandLine + "-> Line marked as @Fail didn't fail"));
            }
        }
        else if (parts[0].equals("sunStorm")){
            SunStorm(Integer.parseInt(parts[1]));
        }
        else if (parts[0].equals("save")){
            FileController fc = new FileController();
            fc.Save(new File(CurrentWorkingDirectory + "\\" + parts[1]),map,this);
        }
        else if(parts[0].equals("load")){
            FileController fc = new FileController();
            map = fc.Load(new File(CurrentWorkingDirectory + "\\" + parts[1]),this);
        }
        else if(parts[0].equals("controller")){
            controller = Boolean.parseBoolean(parts[1]);
        }
        else if(parts[0].equals("endturn")){
            EndTurn();
        }
        else if(parts[0].equals("ls")){
            boolean all_attrib = false;
            if(parts.length >= 3){
                if(parts[2].equals("att")){
                    all_attrib = true;
                }
            }
            switch (parts[1]){
                case "all":
                    map.Save(System.out);
                    break;
                case "p":
                    for(PlayerShip it : ps){
                        if(all_attrib)
                            it.Save(System.out, false);
                        else
                            System.out.println("Player: " + it.GetUID());
                    }
                    break;
                case "s":
                    for(PlayerShip it : ps){
                        if(all_attrib)
                            it.Save(System.out, false);
                        else
                            System.out.println("Player: " + it.GetUID());
                    }
                    for(UFO it : ufos){
                        if(all_attrib)
                            it.Save(System.out, false);
                        else
                            System.out.println("Player: " + it.GetUID());
                    }
                case "r":
                    for(RobotShip it : rs){
                        if(all_attrib)
                            it.Save(System.out, false);
                        else
                            System.out.println("Robot: " + it.GetUID());
                    }
                    break;
                case "u":
                    for(UFO it : ufos){
                        if(all_attrib)
                            it.Save(System.out, false);
                        else
                            System.out.println("Player: " + it.GetUID());
                    }
                    break;
                case "a":
                    for(Sector s : map.getSectors()){
                        for(Field f : s.getFields()){
                            if(f.toString().equals("Asteroid")){
                                if(all_attrib)
                                    f.Save(System.out, false);
                                else
                                    System.out.println("Player: " + f.GetUID());
                            }
                        }
                    }
                    break;
                case "t":
                    for(TeleportGate it : tgs){
                        if(all_attrib)
                            it.Save(System.out, false);
                        else
                            System.out.println("Player: " + it.GetUID());
                    }
                    break;
            }
        }
        else if(parts[0].equals("compare")){
            FileController fc = new FileController();
            fc.Compare(new File(CurrentWorkingDirectory + "\\" + parts[1]),new File(CurrentWorkingDirectory + "\\" + parts[2]));
        }
        else{
            if(parts.length < 3){
                throw(new InvalidCommand(CommandLine + "-> Too few parameter for UID search or unknown command"));
            }
            char TypeFlag = parts[0].charAt(0);
            int UID = Integer.parseInt(parts[1]);
            ArrayList<String> Args = new ArrayList<>();
            for(int i=3;i< parts.length;i++){
                Args.add(parts[i]);
            }
            switch (TypeFlag){
                case 'p':
                    PlayerDoes(UID,parts[2], Args);
                    break;
                case 'r':
                    RobotDoes(UID,parts[2], Args);
                    break;
                case 'u':
                    UFODoes(UID,parts[2], Args);
                    break;
                case 't':
                    TeleportDoes(UID,parts[2], Args);
                    break;
                default:
                    throw(new InvalidCommand(CommandLine + "-> Unknown typeflag"));
            }
        }
    }

    /**
     * It handles the player commands.
     * @param UID The UID of the player.
     * @param command The command what he want to do.
     * @param Args The argument of the command:
     * @throws InvalidCommand
     */
    public void PlayerDoes(int UID, String command, ArrayList<String> Args) throws InvalidCommand {
        PlayerShip current = null;
        for(PlayerShip it : ps){
            if(it.GetUID() == UID){
                current = it;
            }
        }
        if(controller && UID != getCurrentPlayer().GetUID()){
            throw (new InvalidCommand("p " + UID + " " + command + " -> " + "The object with this identifier is not the one taking its turn."));
        }
        if(current == null)
            throw(new InvalidCommand( "p "+ UID + " " + command, Args ,"-> player UID not found"));
        if(command.equals("move")){
            ArrayList<Field> fields = current.getAsteroid().getNeighbours();
            Field target = null;
            for(Field it : fields){
                if(it.GetUID() == Integer.parseInt(Args.get(0))){
                    target = it;
                }
            }
            if(target == null)
                throw(new InvalidCommand("p "+ UID + " " + command, Args , "-> Field UID not found on move"));
            current.Move(target);
        }
        else if(command.equals("craft")){
            switch (Args.get(0)) {
                case "robot":
                    current.CraftRobot();
                    break;
                case "teleports":
                    current.CraftTeleportGates();
                    break;
                case "base":
                    current.CraftBase();
                    break;
            }
        }
        else if(command.equals("put_back")){
            ArrayList<Material> materials = current.getMaterials();
            Material target = null;
            for(Material it : materials){
                if(it.GetUID() == Integer.parseInt(Args.get(0))){
                    target = it;
                }
            }
            if(target == null)
                throw(new InvalidCommand("p "+ UID + " " + command, Args , "-> Material UID not found on put_back"));
            current.PutBack(target);
        }
        else if(command.equals("put_down")){
            ArrayList<TeleportGate> materials = current.getTeleports();
            TeleportGate target = null;
            for(TeleportGate it : materials){
                if(it.GetUID() == Integer.parseInt(Args.get(0))){
                    target = it;
                }
            }
            if(target == null)
                throw(new InvalidCommand("p "+ UID + " " + command, Args , "-> Teleport UID not found on put_down"));
            current.PutDown(target);
        }
        else if(command.equals("drill")){
            current.Drill();
        }
        else if(command.equals("mine")){
            current.Mine();
        }
        else if(command.equals("build_base")){
            current.BuildBase();
        }
        else if(command.equals("skip")){
            NotificationManager.setLastCommandSuccess(true);
        }
        else{
            throw(new InvalidCommand("p "+ UID + " " + command, Args , "-> Unkown command"));
        }
        if(controller) {
            if(ps.size()==0){
                NotificationManager.PlayersLost();
            }
            else if (NotificationManager.LastCommandSuccess) {
                for (int i = 0; i < ps.size(); i++) {
                    if (ps.get(i) == CurrentPlayer) {
                        if (i == ps.size() - 1) {
                            CurrentPlayer = ps.get(0);
                            EndTurn();
                        } else {
                            CurrentPlayer = ps.get(i + 1);
                        }
                        break;
                    }
                }
            }
        }
        TeleportGate gate = NotificationManager.getNewTeleport();
        if(gate != null){
            tgs.add(gate);
            NotificationManager.AddNewTeleport(gate); //Pushing it back so the UI can see it as well.
        }
        RobotShip r = NotificationManager.getNewRobot();
        if(r != null){
            rs.add(r);
        }
        if(current.getAsteroid()==null)
            ps.remove(current);
    }

    /**
     * It handles the robot commands.
     * @param UID The UID of the robot.
     * @param command The command what he want to do.
     * @param Args The argument of the command:
     * @throws InvalidCommand
     */
    public void RobotDoes(int UID, String command, ArrayList<String> Args) throws InvalidCommand {
        RobotShip current = null;
        for(RobotShip it : rs){
            if(it.GetUID() == UID){
                current = it;
            }
        }
        if(current == null)
            throw(new InvalidCommand("r "+ UID + " " + command, Args , "-> Robot UID not found"));
        if(command.equals("move")){
            ArrayList<Field> fields = current.getAsteroid().getNeighbours();
            Field target = null;
            for(Field it : fields){
                if(it.GetUID() == Integer.parseInt(Args.get(0))){
                    target = it;
                }
            }
            if(target == null)
                throw(new InvalidCommand("r "+ UID + " " + command, Args , "-> Field UID not found on move"));
            current.Move(target);
        }
        else if(command.equals("drill")){
            current.Drill();
        }
        if(current.getAsteroid()==null)
            rs.remove(current);
    }

    /**
     * It handles the UFO commands.
     * @param UID The UID of the UFO.
     * @param command The command what he want to do.
     * @param Args The argument of the command:
     * @throws InvalidCommand
     */
    public void UFODoes(int UID, String command, ArrayList<String> Args) throws InvalidCommand {
        UFO current = null;
        for(UFO it : ufos){
            if(it.GetUID() == UID){
                current = it;
            }
        }
        if(current == null)
            throw(new InvalidCommand("u "+ UID + " " + command, Args , "-> UFO UID not found"));
        if(command.equals("move")){
            ArrayList<Field> fields = current.getAsteroid().getNeighbours();
            Field target = null;
            for(Field it : fields){
                if(it.GetUID() == Integer.parseInt(Args.get(0))){
                    target = it;
                }
            }
            if(target == null)
                throw(new InvalidCommand("u "+ UID + " " + command, Args , "-> Field UID not found on move"));
            current.Move(target);
        }
        else if(command.equals("mine")){
            current.Mine();
        }
        if(current.getAsteroid()==null)
            ufos.remove(current);
    }

    /**
     * It handles the teleport commands.
     * @param UID The UID of the teleport.
     * @param command The command what he want to do.
     * @param Args The argument of the command:
     * @throws InvalidCommand
     */
    public void TeleportDoes(int UID, String command, ArrayList<String> Args) throws InvalidCommand {
        TeleportGate current = null;
        for(TeleportGate it : tgs){
            if(it.GetUID() == UID){
                current = it;
            }
        }
        if(current == null)
            throw(new InvalidCommand("t "+ UID + " " + command, Args , "-> Teleport UID not found"));
        if(command.equals("move")){
            ArrayList<Field> fields = current.getNeighbours().get(0).getNeighbours();
            Field target = null;
            for(Field it : fields){
                if(it.GetUID() == Integer.parseInt(Args.get(0))){
                    target = it;
                }
            }
            if(target == null)
                throw(new InvalidCommand("t "+ UID + " " + command, Args , "-> Field UID not found on move"));
            current.Move(target);
        }
        else if(command.equals("turnover")){
            current.TurnOver();
        }
    }

    /**
     * It handles the uranium commands.
     * @param UID The UID of the uranium.
     * @param command The command what he want to do.
     * @param Args The argument of the command:
     * @throws InvalidCommand
     */
    public void UraniumDoes(int UID, String command, ArrayList<String> Args) throws InvalidCommand {
        Uranium current = null;
        for(Uranium it : urans){
            if(it.GetUID() == UID){
                current = it;
            }
        }
        if(current == null)
            throw(new InvalidCommand("U "+ UID + " " + command, Args , "-> Uranium UID not found"));
        if(command.equals("turnover")){
            current.TurnOver();
        }
    }

    /**
     * It handles if the command was calling a sun storm.
     * @param UID The UID of the sector where the sun storm will happen.
     * @throws InvalidCommand
     */
    public void SunStorm(int UID) throws InvalidCommand {
        ArrayList<Sector> sectors = map.getSectors();
        Sector target = null;
        for(Sector it : sectors){
            if(it.GetUID() == UID){
                target = it;
            }
        }
        if(target == null)
            throw(new InvalidCommand("sunstorm "+ UID +  "-> Sector UID not found"));
        Sun.GetInstance().SunStorm(target);
    }

    /**
     * It handles if a turn ends (called).
     */
    public void EndTurn(){
        Sun.GetInstance().TurnOver();
        AIController ai = new AIController();
        if(controller) {
            ArrayList<RobotShip> rDel = new ArrayList<>();
            for (RobotShip r : rs) {
                if (r.getAsteroid() == null)
                    rDel.add(r);
                else
                    ai.TakeTurn(r);
                if (r.getAsteroid() == null)
                    rDel.add(r);
            }
            rs.removeAll(rDel);
            ArrayList<UFO> uDel = new ArrayList<>();
            for (UFO u : ufos) {
                if (u.getAsteroid() == null)
                    uDel.add(u);
                else
                    ai.TakeTurn(u);
                if (u.getAsteroid() == null)
                    uDel.add(u);
            }
            ufos.removeAll(uDel);
            for (TeleportGate t : tgs) {
                t.TurnOver();
            }
        }
        for(Uranium uranium : urans){
            uranium.TurnOver();
        }
        ArrayList<PlayerShip> deletable = new ArrayList<>();
        for(PlayerShip current : ps){
            if(current.getAsteroid()==null)
                deletable.add(current);
        }
        ps.removeAll(deletable);
        if(ps.size()==0){
            NotificationManager.PlayersLost();
        }

    }

    /**
     * Creates a new map.
     */
    public void NewMap(){
        MapBuilder mb = new MapBuilder();
        map = mb.BuildMap(this);
        Sun.GetInstance().SetMap(map);
    }
}
