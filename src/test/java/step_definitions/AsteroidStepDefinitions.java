package step_definitions;

import Model.*;
import Model.Materials.Coal;
import Model.Materials.Ice;
import Model.Materials.Material;
import Model.Materials.Uranium;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.Random;

import static step_definitions.TeleportStepDefinitions.teleportGate;

public class AsteroidStepDefinitions {

    static Asteroid asteroid;
    static Map map = new Map();
    static Sector sector = new Sector(map.GetNewUID(), map);
    static boolean loweredShellNumbers;

    @And("Asteroid has not got a core")
    public void asteroidHasNotGotACore() {
        asteroid.SetCore(null);
    }

    @And("Asteroid has an ice Core")
    public void asteroidHasAnIceCore() {
        Ice ice =new Ice(map.GetNewUID());
        asteroid.SetCore(ice);
    }

    @And("Ice should have evaporated")
    public void iceShouldHaveEvaporated() {
        Assert.assertTrue(asteroid.GetCore()==null);
    }

    @Then("Uranium has been exposed one more time")
    public void uraniumHasBeenExposedOneMoreTime() {
        ((Uranium)asteroid.GetCore()).TurnOver();
        Assert.assertNotEquals(0, ((Uranium) asteroid.GetCore()).getExposedFor());
    }

    @And("Asteroid has an Uranium Core")
    public void asteroidHasAnUraniumCore() {
        Uranium uranium=new Uranium(1);
        asteroid.SetCore(uranium);
    }

    @And("Asteroid has {int} shells")
    public void asteroidHasShells(int arg0) {
        asteroid.SetShell(arg0);
    }

    @And("Asteroid has a core")
    public void asteroidHasACore() {
        Coal coal =new Coal(map.GetNewUID());
        asteroid.SetCore(coal);
    }

    @Then("Asteroid should have less shells")
    public void asteroidShouldHaveLessShells() {
        Assert.assertTrue(loweredShellNumbers);
    }

    @Then("Asteroid should have {int} shell")
    public void asteroidShouldHaveShell(int arg0) {
        Assert.assertEquals(arg0, asteroid.GetShell());
    }

    @And("Asteroid is close to sun")
    public void asteroidIsCloseToSun() {
        asteroid.setX(0);
        asteroid.setY(0);
    }

    @And("I have an asteroid")
    public void iHaveAnAsteroid() {
        asteroid = new Asteroid(sector, null, new Random().nextInt(6)+4);
    }

    @Then("Asteroid should not have neighboring teleport")
    public void asteroidShouldNotHaveNeighboringTeleport() {
        boolean teleport=false;
        for (Field field:asteroid.getNeighbours()) {
            if (field.getClass()== TeleportGate.class){
                teleport=true;
            }
        }
        Assert.assertTrue(!teleport);
    }

    @Then("Asteroid should have neighboring teleport")
    public void asteroidShouldHaveNeighboringTeleport() {
        boolean teleport=false;
        for (Field field:asteroid.getNeighbours()) {
            if (field.getClass()== TeleportGate.class){
                teleport=true;
            }
        }
        Assert.assertTrue(teleport);
    }

    @And("Asteroid has a neighboring asteroid")
    public void asteroidHasANeighboringAsteroid() {
        asteroid.getNeighbours().add(new Asteroid(map.GetNewUID()));
    }

    @And("Asteroid doesnt have neighboring asteroid")
    public void asteroidDoesntHaveNeighboringAsteroid() {
        asteroid.getNeighbours().clear();
    }

    @And("Asteroid has a neighboring teleport")
    public void asteroidHasANeighboringTeleport() {
        asteroid.getNeighbours().add(new TeleportGate(map.GetNewUID()));
        teleportGate.getNeighbours().add(asteroid);
    }
}
