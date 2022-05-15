package Model;

import Controllers.FileController;
import Controllers.MapBuilder;
import Controllers.NotificationManager;
import Model.Materials.BillCreator;
import Model.Materials.BillOfMaterial;
import Model.Materials.Material;
import UI.Components.MagicConstants;
import Utils.LinkerException;
import Utils.StringPair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * It bases from the Ship class. Represents the player's ship in the game.
 */
public class PlayerShip extends Ship {
    /**
     * The player's materials.
     */
    private ArrayList<Material> materials = new ArrayList<>();
    /**
     * The player's teleport gates.
     */
    private ArrayList<TeleportGate> teleports = new ArrayList<>();


    public PlayerShip(int uid){
        super(uid);
        materials = new ArrayList<>();
        teleports = new ArrayList<>();
    }

    public PlayerShip(Asteroid start){
        super(start);
        materials = new ArrayList<>();
        teleports = new ArrayList<>();
    }

    public PlayerShip(Map map) {
        super(map);
    }

    /**
     * The getter of the materials.
     * @return With the player's materials.
     */
    public ArrayList<Material> getMaterials(){return materials;}

    /**
     * The getter of the teleport gates.
     * @return With the player's teleport gates.
     */
    public ArrayList<TeleportGate> getTeleports(){return teleports;}

    /**
     * Mines asteroid's core material.
     */
    public void Mine(){
        NotificationManager.setLastCommandSuccess(true);
        // Only mines if player ship has 9 material or less.
        if(materials.size() < 10) {
            Material core;
            core = asteroid.GetMined();
            // Only adds if asteroid is not empty.
            if(core != null){
                materials.add(core);
                NotificationManager.AddMessage("Player" + GetUID() + " mined successfully");
            }
        }
        else{
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Player inventory full, can't mine");
        }
    }

    /**
     * Removes a bill of material from the inventory.
     * @param b The materials we want to be removed from the player's inventory.
     */
    public void Remove(BillOfMaterial b){
        ArrayList<Material> removable;
        removable = b.GetMaterials();
        for(Material m : removable){
            materials.remove(m);
        }
    }

    /**
     * Crafts teleport gate pair.
     */
    public void CraftTeleportGates(){
        NotificationManager.setLastCommandSuccess(true);
        if(teleports.size() <= 1) {
            BillCreator bc = BillCreator.GetInstance();
            BillOfMaterial bill = bc.CreateForTeleport(materials);
            // checks whether player ship has enough material to craft
            if(bill != null)  {
                Remove(bill);
                TeleportGate t1 = new TeleportGate(asteroid.sector.map.GetNewUID());
                TeleportGate t2 = new TeleportGate(asteroid.sector.map.GetNewUID());
                t1.pair(t2);
                teleports.add(t1);
                teleports.add(t2);
                NotificationManager.AddMessage("Player" + GetUID() + " built 2 teleports.");
            }
            else{
                NotificationManager.setLastCommandSuccess(false);
                NotificationManager.AddError("Player doesn't have enough materials, can't craft");
            }
        }
        else{
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Player inventory full, can't craft");
        }
    }

    /**
     * Crafts a robot.
     */
    public void CraftRobot(){
        NotificationManager.setLastCommandSuccess(true);
        BillCreator bc = BillCreator.GetInstance();
        BillOfMaterial bill = bc.CreateForRobot(materials);
        // checks whether player ship has enough material to craft
        if(bill != null) {
            Remove(bill);
            RobotShip rs = new RobotShip(asteroid);
            NotificationManager.AddNewRobot(rs);
            NotificationManager.AddMessage("Player" + GetUID() + " built a robot.");
        }
        else{
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Player doesn't have enough materials, can't craft");
        }
    }

    /**
     * Crafts a base foundation.
     */
    public void CraftBase(){
        NotificationManager.setLastCommandSuccess(true);
        if(asteroid.GetBase() == null){
            BillCreator bc = BillCreator.GetInstance();
            BillOfMaterial bill = bc.CreateForBaseFoundation(materials);
            if(bill != null) {
                Remove(bill);
                Base newbase = new Base(asteroid.sector.map.GetNewUID());
                for(Material m : bill.GetMaterials()){
                    newbase.Accept(m);
                }
                asteroid.SetBase(newbase);
                NotificationManager.AddMessage("Player" + GetUID() + " built a base.");
            }
            else{
                NotificationManager.setLastCommandSuccess(false);
                NotificationManager.AddError("Player doesn't have enough materials, can't craft");
            }
        }
        else{
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Base is already placed on asteroid, can't craft");
        }
    }

    /**
     * Puts back a material to the core.
     * @param m The material we want to put back to the core.
     */
    public void PutBack(Material m){
        NotificationManager.setLastCommandSuccess(true);
        if(asteroid.SetCore(m)){
            materials.remove(m);
            NotificationManager.AddMessage("Player" + GetUID() + " successfully put: Material" + m.GetUID() + " back.");
        }
    }

    /**
     * The player drills
     */
    public void Drill(){
        NotificationManager.setLastCommandSuccess(true);
        if(asteroid.GetDrilled()){
            NotificationManager.AddMessage("Player" + GetUID() + " drilled Asteroid" + asteroid.GetUID());
        }
        else{
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Asteroid is already drilled through, can't drill");
        }
    }

    /**
     * Builds the base.
     */
    public void BuildBase(){
        // checks whether is a base already on the asteroid
        if(asteroid.GetBase() != null){
            ArrayList<Material> removables = new ArrayList<>();
            for(Material it : materials) {
                if (asteroid.GetBase().Accept(it))
                    removables.add(it);
            }
            if(removables.size() != 0)
                NotificationManager.AddMessage("Player" + GetUID() + " successfully built " + removables.size() + " into the base.");
            else{
                NotificationManager.setLastCommandSuccess(false);
                NotificationManager.AddError("Player doesn't have any materials the base needs, can't build");
            }
            for(Material it : removables)
                materials.remove(it);
            asteroid.GetBase().CheckComplete();
        }
        else{
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("No base on asteroid, can't build");
        }
    }

    /**
     * Puts down the teleport gate.
     * @param t The teleport gate we want to put down next to the current asteroid.
     */
    public void PutDown(TeleportGate t){
        // Checks whether is player ship has teleport.
        Random r = new Random();
        if(teleports.size()>0){
            Remove(t);
            t.AddNeighbour(asteroid);
            t.SetSector(asteroid.sector);
            asteroid.sector.Add(t);
            asteroid.AddNeighbour(t);
            t.Reposition();
            NotificationManager.AddMessage("Player" + GetUID() + " successfully put Teleport" + t.GetUID() + " down.");
            NotificationManager.AddNewTeleport(t);
        }
    }

    /**
     * Player ship dies.
     */
    public void Die(){
        // Removes all materials.
        for(Material m : materials){
            materials.remove(m);
        }
        // Removes all teleports.
        for(TeleportGate t : teleports){
            Remove(t);
        }
        asteroid.Remove(this);
        NotificationManager.AddMessage("Player" + GetUID() + " died");
        asteroid = null;
    }

    /**
     * In case of asteroid exploding player ship dies.
     */
    public void AsteroidExploding(){
        Die();
    }

    @Override
    public void Move(Field f){
        NotificationManager.setLastCommandSuccess(true);
        Asteroid a = asteroid;
        super.Move(f);
        if(a == asteroid){
            NotificationManager.setLastCommandSuccess(false);
            NotificationManager.AddError("Teleport is not active, couldn't move.");
        }
        else{
            NotificationManager.AddMessage("Player" + GetUID() + " successfully moved to Asteroid" + asteroid.GetUID());
        }
    }


    /**
     * Removes a teleport gate.
     * @param t The teleport gate which will be removed from the player ship's inventory.
     */
    public void Remove(TeleportGate t){
        teleports.remove(t);
    }


    @Override
    public String toString(){
        return "PlayerShip";
    }

    /**
     * Links the objects attributes with their "value"
     * @param args The pairs we want to match.
     * @param fc The file controller.
     * @throws LinkerException
     */
    @Override
    public void Link(ArrayList<StringPair> args, FileController fc) throws LinkerException {
        super.Link(args,fc);
        for(StringPair it : args) {
            if(it.first.equals("Materials")){
                String[] ids = it.second.split(",");
                for (String idIt : ids) {
                    materials.add((Material) fc.GetWithUID(Integer.parseInt(idIt)));
                }
            }
            else if(it.first.equals("Teleports")){
                String[] ids = it.second.split(",");
                for (String idIt : ids) {
                    teleports.add((TeleportGate) fc.GetWithUID(Integer.parseInt(idIt)));
                }
            }
        }
    }

    /**
     * The save method for the PlayerShip class.
     * @param os The stream, where the class will be written.
     * @param CallChildren
     */
    @Override
    public void Save(PrintStream os,boolean CallChildren) {
        os.println("PlayerShip{");
        super.Save(os,CallChildren);
        if(materials.size()>0) {
            os.print("Materials: ");
            materials.sort(new Comparator<Material>() {
                @Override
                public int compare(Material o1, Material o2) {
                    return o1.GetUID()-o2.GetUID();
                }
            });
            for (Material it : materials) {
                os.print(it.GetUID());
                if (it != materials.get(materials.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
        }
        if(teleports.size()>0) {
            os.print("Teleports: ");
            teleports.sort(new Comparator<TeleportGate>() {
                @Override
                public int compare(TeleportGate o1, TeleportGate o2) {
                    return o1.GetUID()-o2.GetUID();
                }
            });
            for (TeleportGate it : teleports) {
                os.print(it.GetUID());
                if (it != teleports.get(teleports.size() - 1)) {
                    os.print(",");
                } else {
                    os.println();
                }
            }
        }
        os.println("}");
        if(CallChildren) {
            for (TeleportGate s : teleports) {
                s.Save(os, CallChildren);
            }
            for (Material s : materials) {
                s.Save(os, CallChildren);
            }
        }
    }

    @Override
    public void accept(IVisitor v) {
        v.visit(this);
    }
}
