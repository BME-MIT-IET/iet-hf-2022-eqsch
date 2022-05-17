package step_definitions;

import Model.Asteroid;
import Model.Materials.*;
import Model.PlayerShip;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Random;

import static step_definitions.AsteroidStepDefinitions.*;

public class PlayerStepDefinitions {
    static PlayerShip playerShip = null;
    boolean lastMineSuccessful;
    int numberOfTeleportsBeforeCrafting;

    @Given("I have a player")
    public void iHaveAPlayer(){
        playerShip = new PlayerShip(map);
    }

    @And("Player stands on asteroid")
    public void playerStandsOnAsteroid() {
        playerShip.setAsteroid(asteroid);
    }

    @When("Player mines")
    public void playerMines() {
        int inventorySize = playerShip.getMaterials().size();
        playerShip.Mine();
        lastMineSuccessful = inventorySize < playerShip.getMaterials().size();
    }

    @Then("Player should have mined a mineral")
    public void playerShouldHaveMinedAMineral() {
        if (playerShip.getMaterials().size() > 0) {
            System.out.println("Player mines successfully");
        } else {
            System.out.println("Player could not mine successfully");
        }
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
        if (lastMineSuccessful) {
            System.out.println("Player mine successfully");
        } else {
            System.out.println("Player could not mine successfully");
        }
    }

    @When("Player drills")
    public void playerDrills() {
        int previousShellNumbers = asteroid.GetShell();
        playerShip.Drill();
        loweredShellNumbers = previousShellNumbers != asteroid.GetShell();
    }

    @And("Player has {int} Iron in backpack")
    public void playerHasIronInBackpack(int arg0) {
        Asteroid originalAsteroid = playerShip.getAsteroid();
        for (int i = 0; i < arg0; ++i) {
            Asteroid tempAsteroid = new Asteroid(sector, new Iron(map), 0);
            playerShip.setAsteroid(tempAsteroid);
            playerShip.Drill();
        }
        playerShip.setAsteroid(originalAsteroid);
    }

    @And("Player has {int} Coal in backpack")
    public void playerHasCoalInBackpack(int arg0) {
        Asteroid originalAsteroid = playerShip.getAsteroid();
        for (int i = 0; i < arg0; ++i) {
            Asteroid tempAsteroid = new Asteroid(sector, new Coal(map), 0);
            playerShip.setAsteroid(tempAsteroid);
            playerShip.Drill();
        }
        playerShip.setAsteroid(originalAsteroid);
    }

    @And("Player has {int} Ice in backpack")
    public void playerHasIceInBackpack(int arg0) {
        Asteroid originalAsteroid = playerShip.getAsteroid();
        for (int i = 0; i < arg0; ++i) {
            Asteroid tempAsteroid = new Asteroid(sector, new Ice(map), 0);
            playerShip.setAsteroid(tempAsteroid);
            playerShip.Drill();
        }
        playerShip.setAsteroid(originalAsteroid);
    }

    @And("Player has {int} Uranium in backpack")
    public void playerHasUraniumInBackpack(int arg0) {
        Asteroid originalAsteroid = playerShip.getAsteroid();
        for (int i = 0; i < arg0; ++i) {
            Asteroid tempAsteroid = new Asteroid(sector, new Uranium(map), 0);
            playerShip.setAsteroid(tempAsteroid);
            playerShip.Drill();
        }
        playerShip.setAsteroid(originalAsteroid);
    }

    @When("Player creates a teleport")
    public void playerCreatesATeleport() {
        numberOfTeleportsBeforeCrafting = playerShip.getTeleports().size();
        playerShip.CraftTeleportGates();
    }

    @Then("Player should have created {int} teleport")
    public void playerShouldHaveCreatedTeleport(int arg0) {
        if (numberOfTeleportsBeforeCrafting+arg0 == playerShip.getTeleports().size()) {
            System.out.println("Player has created " + arg0 + " teleports");
        } else {
            System.out.println("Player has not created " + arg0 + " teleports");
        }
    }

    @Then("Player should not have created {int} teleport")
    public void playerShouldNotHaveCreatedTeleport(int arg0) {
        if (playerShip.getTeleports().size() - numberOfTeleportsBeforeCrafting != arg0) {
            System.out.println("Player has not created " + arg0 + " teleports");
        } else {
            System.out.println("Player has created " + arg0 + " teleports, but it shouldn't have been able to");
        }
    }

    @When("Player creates a robot")
    public void playerCreatesARobot() {
        playerShip.CraftRobot();
    }

    @Then("I should have a robot")
    public void iShouldHaveARobot() {
    }

    @Then("I should not have a robot")
    public void iShouldNotHaveARobot() {
    }

    @And("Player has {int} teleport")
    public void playerHasTeleport(int arg0) {
    }

    @When("Player puts down teleport")
    public void playerPutsDownTeleport() {
    }

    @When("Player moves")
    public void playerMoves() {
    }

    @Then("Player should stand on the neighboring asteroid")
    public void playerShouldStandOnTheNeighboringAsteroid() {
    }

    @Then("Player should not stand on the neighboring asteroid")
    public void playerShouldNotStandOnTheNeighboringAsteroid() {
    }

    @Then("Player should stand on the neighboring teleports pair")
    public void playerShouldStandOnTheNeighboringTeleportsPair() {
    }

    @Then("Player should have not moved")
    public void playerShouldHaveNotMoved() {
    }
}
