package step_definitions;

import Model.Asteroid;
import Model.TeleportGate;
import Model.UFO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static step_definitions.AsteroidStepDefinitions.*;
public class UfoStepDefinitions {

    UFO ufo;
    @And("I have an ufo")
    public void iHaveAnUfo() {
        ufo= new UFO(asteroid);
    }

    @And("Ufo stands on asteroid")
    public void ufoStandsOnAsteroid() {
        ufo.setAsteroid(asteroid);
    }

    @And("Ufo has place in backpack")
    public void ufoHasPlaceInBackpack() {
    }

    @When("Ufo mines")
    public void ufoMines() {
        ufo.Mine();
    }

    @Then("Ufo should have mined a mineral")
    public void ufoShouldHaveMinedAMineral() {
        Assert.assertNotEquals(0, ufo.getMaterials().size());
    }

    @Then("Ufo should have mined nothing")
    public void ufoShouldHaveMinedNothing() {
        Assert.assertEquals(0, ufo.getMaterials().size());
    }

    @When("Ufo moves")
    public void ufoMoves() {
        if (asteroid.getNeighbours().size()>0)
            ufo.Move(asteroid.getNeighbours().get(0));
    }

    @Then("Ufo should stand on the neighboring asteroid")
    public void ufoShouldStandOnTheNeighboringAsteroid() {
        Assert.assertEquals(asteroid.getNeighbours().get(0).GetUID(), ufo.getAsteroid().GetUID());
    }

    @Then("Ufo should not stand on the neighboring asteroid")
    public void ufoShouldNotStandOnTheNeighboringAsteroid() {
        if (asteroid.getNeighbours().size()>0)
            Assert.assertNotEquals(asteroid.getNeighbours().get(0).GetUID(), ufo.getAsteroid().GetUID());
        else
            Assert.assertNotEquals(null, ufo.getAsteroid().GetUID());
    }

    @Then("Ufo should stand on the neighboring teleports pair")
    public void ufoShouldStandOnTheNeighboringTeleportsPair() {
        Assert.assertEquals(((TeleportGate)asteroid.getNeighbours().get(0)).getPair().getNeighbours().get(0).GetUID(), ufo.getAsteroid().GetUID());
    }

    @Then("Ufo should have not moved")
    public void ufoShouldHaveNotMoved()  {
        Assert.assertEquals(asteroid.GetUID(), ufo.getAsteroid().GetUID());
    }
}
