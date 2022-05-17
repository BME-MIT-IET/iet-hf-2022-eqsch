package step_definitions;

import Model.Asteroid;
import Model.Map;
import Model.Materials.Coal;
import Model.Materials.Ice;
import Model.Materials.Material;
import Model.Materials.Uranium;
import Model.Sector;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.util.Random;

import static step_definitions.PlayerStepDefinitions.playerShip;

public class AsteroidStepDefinitions {

    static Asteroid asteroid;
    static Map map = new Map();
    static Sector sector = new Sector(map.GetNewUID(), map);
    static boolean loweredShellNumbers;

    public void MaterialTurnOver(Uranium uranium) {
        uranium.TurnOver();
        System.out.println("uranium");
    }

    public void MaterialTurnOver(Material material) {
        /* No operation */
        System.out.println("material");
    }

    @And("Asteroid has not got a core")
    public void asteroidHasNotGotACore() {
        asteroid.Evaporate(); // Wasn't written for this, but works!
    }

    @And("Asteroid has more than {int} shells")
    public void asteroidHasMoreThanShells(int arg0) {
        asteroid.SetShell(arg0 + 1);
    }


    @And("Asteroid has an ice Core")
    public void asteroidHasAnIceCore() {
        int originalShells = asteroid.GetShell();
        boolean playerStandsOnAsteroid = playerShip.getAsteroid() != null;

        asteroid = new Asteroid(sector, new Ice(map), originalShells);
        if (playerStandsOnAsteroid) {
            playerShip.setAsteroid(asteroid);
        }
    }

    @And("Ice should have evaporated")
    public void iceShouldHaveEvaporated() {
        if (asteroid.GetCore() == null) {
            System.out.println("Asteroid has not got a core");
        } else {
            System.out.println("Asteroid has a core");
        }
    }

    @Then("Uranium has been exposed one more time")
    public void uraniumHasBeenExposedOneMoreTime() {
        MaterialTurnOver(asteroid.GetCore());
        System.out.println(asteroid.GetCore());
    }

    @And("Asteroid has an Uranium Core")
    public void asteroidHasAnUraniumCore() {
        int originalShells = asteroid.GetShell();
        boolean playerStandsOnAsteroid = playerShip.getAsteroid() != null;

        asteroid = new Asteroid(sector, new Uranium(map), originalShells);
        if (playerStandsOnAsteroid) {
            playerShip.setAsteroid(asteroid);
        }
    }

    @And("Asteroid has {int} shell")
    public void asteroidHasShell(int arg0) {
        asteroidHasShells(arg0);
    }

    @And("Asteroid has {int} shells")
    public void asteroidHasShells(int arg0) {
        asteroid.SetShell(arg0);
    }

    @And("Asteroid has a core")
    public void asteroidHasACore() {
        /*
        Asteroid has a shell by default
         */
    }

    @Then("Asteroid should have less shells")
    public void asteroidShouldHaveLessShells() {
        if (loweredShellNumbers) {
            System.out.println("Asteroid has got fewer shells");
        } else {
            System.out.println("Asteroid has the same number of shells");
        }
    }

    @Then("Asteroid should have {int} shell")
    public void asteroidShouldHaveShell(int arg0) {
        if (asteroid.GetShell() == arg0) {
            System.out.println("Asteroid has the right number of shells");
        } else {
            System.out.println("Asteroid does not have the right number of shells");
        }
    }

    @And("Asteroid is close to sun")
    public void asteroidIsCloseToSun() {
        asteroid.setX(0);
        asteroid.setY(0);
    }

    @And("I have an asteroid")
    public void iHaveAnAsteroid() {
        asteroid = new Asteroid(sector, new Coal(map), new Random().nextInt(6)+4);
    }

    @Then("Asteroid should not have neighboring teleport")
    public void asteroidShouldNotHaveNeighboringTeleport() {
    }

    @Then("Asteroid should have neighboring teleport")
    public void asteroidShouldHaveNeighboringTeleport() {
        
    }

    @And("Asteroid has a neighboring asteroid")
    public void asteroidHasANeighboringAsteroid() {
        
    }

    @And("Asteroid doesnt have neighboring asteroid")
    public void asteroidDoesntHaveNeighboringAsteroid() {
        
    }

    @And("Asteroid has a neighboring teleport")
    public void asteroidHasANeighboringTeleport() {
    }
}
