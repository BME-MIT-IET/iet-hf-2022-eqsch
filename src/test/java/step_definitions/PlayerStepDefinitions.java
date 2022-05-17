package step_definitions;

import Model.Asteroid;
import Model.Materials.*;
import Model.PlayerShip;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
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
        Assert.assertNotEquals(0,playerShip.getMaterials().size() );
    }


    @And("Player has place in backpack")
    public void playerHasPlaceInBackpack() {
        if (playerShip.getMaterials().size()>9){
            playerShip.getMaterials().remove(0);
        }
    }

    @And("Player has no place in backpack")
    public void playerHasNoPlaceInBackpack() {
        playerShip.getMaterials().add(new Coal(1));
        playerShip.getMaterials().add(new Coal(2));
        playerShip.getMaterials().add(new Coal(3));
        playerShip.getMaterials().add(new Coal(4));
        playerShip.getMaterials().add(new Coal(5));
        playerShip.getMaterials().add(new Coal(6));
        playerShip.getMaterials().add(new Coal(7));
        playerShip.getMaterials().add(new Coal(8));
        playerShip.getMaterials().add(new Coal(9));
        playerShip.getMaterials().add(new Coal(0));
    }

    @Then("Player should have mined nothing")
    public void playerShouldHaveMinedNothing() {
        Assert.assertTrue(!lastMineSuccessful);
    }

    @When("Player drills")
    public void playerDrills() {
        int previousShellNumbers = asteroid.GetShell();
        playerShip.Drill();
        loweredShellNumbers = previousShellNumbers != asteroid.GetShell();
    }

    @And("Player has {int} Iron in backpack")
    public void playerHasIronInBackpack(int arg0) {
        for (int i = 0; i < arg0; ++i) {
            Iron iron=new Iron(i);;
            playerShip.getMaterials().add(iron);
        }
    }

    @And("Player has {int} Coal in backpack")
    public void playerHasCoalInBackpack(int arg0) {
        for (int i = 0; i < arg0; ++i) {
            Coal coal=new Coal(i);;
            playerShip.getMaterials().add(coal);
        }
    }

    @And("Player has {int} Ice in backpack")
    public void playerHasIceInBackpack(int arg0) {
        for (int i = 0; i < arg0; ++i) {
            Ice ice=new Ice(i);;
            playerShip.getMaterials().add(ice);
        }
    }

    @And("Player has {int} Uranium in backpack")
    public void playerHasUraniumInBackpack(int arg0) {
        for (int i = 0; i < arg0; ++i) {
            Uranium uranium=new Uranium(i);;
            playerShip.getMaterials().add(uranium);
        }
    }

    @When("Player creates a teleport")
    public void playerCreatesATeleport() {
        numberOfTeleportsBeforeCrafting = playerShip.getTeleports().size();
        playerShip.CraftTeleportGates();
    }

    @Then("Player should have created {int} teleport")
    public void playerShouldHaveCreatedTeleport(int arg0) {
        Assert.assertEquals(arg0, playerShip.getTeleports().size());
    }

    @Then("Player should not have created {int} teleport")
    public void playerShouldNotHaveCreatedTeleport(int arg0) {
        assertEquals(numberOfTeleportsBeforeCrafting, playerShip.getTeleports().size());
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
