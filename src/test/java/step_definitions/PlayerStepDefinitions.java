package step_definitions;

import Model.Asteroid;
import Model.Map;
import Model.Materials.*;
import Model.PlayerShip;
import Model.Sector;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Random;

public class PlayerStepDefinitions {
    Map map = new Map();
    PlayerShip playerShip = null;
    Asteroid asteroid = null;
    Sector sector = new Sector(map.GetNewUID(), map);
    boolean lastMineSuccessfull;
    boolean loweredShellNumbers;

    @Given("I have a player")
    public void iHaveAPlayer(){
        playerShip = new PlayerShip(map);
    }

    @And("I have an asteroid")
    public void iHaveAnAsteroid() {
        /*
        asteroid = new Asteroid(map.GetNewUID());
        int originalShells = asteroid.GetShell();
        asteroid.SetShell(0);
        asteroid.SetCore(new Coal(map));
        asteroid.SetShell(originalShells);
         */

        asteroid = new Asteroid(sector, new Coal(map), new Random().nextInt(6)+4);
    }

    @And("Asteroid has a core")
    public void asteroidHasACore() {
        /*
        Asteroid has a shell by default
         */
    }

    @And("Player stands on asteroid")
    public void playerStandsOnAsteroid() {
        playerShip.setAsteroid(asteroid);
    }

    @When("Player mines")
    public void playerMines() {
        int inventorySize = playerShip.getMaterials().size();
        playerShip.Mine();
        if (inventorySize < playerShip.getMaterials().size()) {
            lastMineSuccessfull = true;
        } else {
            lastMineSuccessfull = false;
        }
    }

    @Then("Player should have mined a mineral")
    public void playerShouldHaveMinedAMineral() {
        if (playerShip.getMaterials().size() > 0) {
            System.out.println("Player mines successfully");
        } else {
            System.out.println("Player could not mine successfully");
        }
    }

    @And("Asteroid has {int} shells")
    public void asteroidHasShells(int arg0) {
        asteroid.SetShell(arg0);
    }

    @And("Player has place in backpack")
    public void playerHasPlaceInBackpack() {
        ArrayList<Material> materials = playerShip.getMaterials();
        BillOfMaterial bom = new BillOfMaterial();
        for (Material material : materials) {
            bom.Add(material);
        }
        playerShip.Remove(bom);
    }

    @And("Player has no place in backpack")
    public void playerHasNoPlaceInBackpack() {
        Asteroid originalAsteroid = playerShip.getAsteroid();
        for (int i = 0; i < 10; ++i) {
            Asteroid tempAsteroid = new Asteroid(map.GetNewUID());
            tempAsteroid.SetCore(new Coal(map));
            playerShip.setAsteroid(tempAsteroid);
            playerShip.Mine();
        }
        playerShip.setAsteroid(originalAsteroid);
    }

    @Then("Player should have mined nothing")
    public void playerShouldHaveMinedNothing() {
        if (lastMineSuccessfull) {
            System.out.println("Player mine successfully");
        } else {
            System.out.println("Player could not mine successfully");
        }
    }

    @And("Asteroid has not got a core")
    public void asteroidHasNotGotACore() {
        asteroid.Evaporate(); // Wasn't written for this, but works!
    }

    @And("Asteroid has more than {int} shells")
    public void asteroidHasMoreThanShells(int arg0) {
        asteroid.SetShell(arg0 + 1);
    }

    @When("Player drills")
    public void playerDrills() {
        int previousShellNumbers = asteroid.GetShell();
        playerShip.Drill();
        loweredShellNumbers = previousShellNumbers != asteroid.GetShell();
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

    @And("Asteroid has {int} shell")
    public void asteroidHasShell(int arg0) {
        asteroidHasShells(arg0);
    }

    @And("Asteroid is close to sun")
    public void asteroidIsCloseToSun() {
        asteroid.setX(0);
        asteroid.setY(0);
    }

    @And("Asteroid has an Uranium Core")
    public void asteroidHasAnUraniumCore() {
        int originalShells = asteroid.GetShell();
        asteroid.SetShell(0);
        asteroid.SetCore(new Uranium(map));
        asteroid.SetShell(originalShells);
    }

    public void MaterialTurnOver(Uranium uranium) {
        uranium.TurnOver();
        System.out.println("uranium");
    }

    public void MaterialTurnOver(Material material) {
        /* No operation */
        System.out.println("material");
    }

    @Then("Uranium has been exposed one more time")
    public void uraniumHasBeenExposedOneMoreTime() {
        MaterialTurnOver(asteroid.GetCore());
        System.out.println(asteroid.GetCore());
        /* Fixit */
    }

    @And("Asteroid has an ice Core")
    public void asteroidHasAnIceCore() {
        int originalShells = asteroid.GetShell();
        boolean playerStandsOnAsteroid = false;
        if (playerShip.getAsteroid() != null) {
            playerStandsOnAsteroid = true;
        }

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
}
