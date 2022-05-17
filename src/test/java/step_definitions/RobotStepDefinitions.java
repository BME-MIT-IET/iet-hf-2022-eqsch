package step_definitions;

import Model.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static step_definitions.AsteroidStepDefinitions.*;

public class RobotStepDefinitions {

    RobotShip robotShip;

    @Given("I have a Robot")
    public void iHaveARobot() {
        robotShip=new RobotShip(asteroid);
    }

    @And("Robot stands on asteroid")
    public void robotStandsOnAsteroid() {
        robotShip.setAsteroid(asteroid);
    }

    @When("Robot drills")
    public void robotDrills() {
        robotShip.Drill();
    }

    @When("Robot moves")
    public void robotMoves() {
        robotShip.Move(asteroid.getNeighbours().get(0));
    }

    @Then("Robot should stand on the neighboring asteroid")
    public void robotShouldStandOnTheNeighboringAsteroid() {
        Assert.assertEquals(asteroid.getNeighbours().get(0).GetUID(), robotShip.getAsteroid().GetUID());
    }

    @Then("Robot should not stand on the neighboring asteroid")
    public void robotShouldNotStandOnTheNeighboringAsteroid() {
        Assert.assertNotEquals(asteroid.getNeighbours().get(0).GetUID(), robotShip.getAsteroid().GetUID());
    }

    @Then("Robot should stand on the neighboring teleports pair")
    public void robotShouldStandOnTheNeighboringTeleportsPair() {
        Assert.assertEquals(((TeleportGate)asteroid.getNeighbours().get(0)).getPair().getNeighbours().get(0).GetUID(), robotShip.getAsteroid().GetUID());
    }

    @Then("Robot should have not moved")
    public void robotShouldHaveNotMoved() {
        Assert.assertEquals(asteroid.GetUID(), robotShip.getAsteroid().GetUID());
    }
}
