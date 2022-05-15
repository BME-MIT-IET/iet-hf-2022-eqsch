package UI.Components;

import Model.*;
import Model.Materials.*;
/**
 * visitor class for setting the right side panels
 */
public class InfoPanelVisitor implements IVisitor {
    /**
     * true if the given object is an asteroid, false if not
     */
    public boolean isAsteroid;
    /**
     * the shell of the given asteroid
     */
    public int Shell;
    /**
     * the core of the given asteroid
     */
    public Material Core;
    /**
     * true if the given teleport is active, false if not
     */
    public boolean isActive;
    /**
     * true if the given teleport is crazy, false if not
     */
    public boolean isCrazy;
    /**
     * the base of the given asteroid
     */
    public Base base;

    public InfoPanelVisitor(IVisitable v){
        isAsteroid = false;
        v.accept(this);
    }

    /**
     * visit method for the teleport's side panel
     * @param tg the teleport
     */
    @Override
    public void visit(TeleportGate tg) {
        isAsteroid = false;
        isActive = tg.isActive();
        isCrazy = tg.getWasHitByStorm();
    }

    /**
     * visit method fot the asteroid's side panel
     * @param a the asteroid
     */
    @Override
    public void visit(Asteroid a) {
        isAsteroid = true;
        Shell = a.GetShell();
        Core = a.GetCore();
        base = a.GetBase();
    }

    @Override
    public void visit(PlayerShip p) {

    }

    @Override
    public void visit(RobotShip r) {

    }

    @Override
    public void visit(UFO u) {

    }

    @Override
    public void visit(Uranium u) {

    }

    @Override
    public void visit(Iron i) {

    }

    @Override
    public void visit(Ice i) {

    }

    @Override
    public void visit(Coal tg) {

    }

    @Override
    public void visit(Base b) {

    }
}
