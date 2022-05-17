package step_definitions;

import Model.Asteroid;
import Model.Map;
import Model.Materials.BillOfMaterial;
import Model.Materials.Coal;
import Model.Materials.Material;
import Model.Materials.Uranium;
import Model.PlayerShip;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static step_definitions.AsteroidStepDefinitions.asteroid;
import static step_definitions.AsteroidStepDefinitions.map;
import static step_definitions.AsteroidStepDefinitions.loweredShellNumbers;

public class PlayerStepDefinitions {

    static PlayerShip playerShip = null;
    boolean lastMineSuccessfull;


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

    @When("Player drills")
    public void playerDrills() {
        int previousShellNumbers = asteroid.GetShell();
        playerShip.Drill();
        loweredShellNumbers = previousShellNumbers != asteroid.GetShell();
    }

    @And("Player has {int} Iron in backpack")
    public void playerHasIronInBackpack(int arg0) {
    }

    @And("Player has {int} Coal in backpack")
    public void playerHasCoalInBackpack(int arg0) {
    }

    @And("Player has {int} Ice in backpack")
    public void playerHasIceInBackpack(int arg0) {
    }

    @And("Player has {int} Uranium in backpack")
    public void playerHasUraniumInBackpack(int arg0) {
    }

    @When("Player creates a teleport")
    public void playerCreatesATeleport() {
    }

    @Then("Player should have created {int} teleport")
    public void playerShouldHaveCreatedTeleport(int arg0) {
    }

    @Then("Player should not have created {int} teleport")
    public void playerShouldNotHaveCreatedTeleport(int arg0) {
    }

    @When("Player creates a robot")
    public void playerCreatesARobot() {
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
