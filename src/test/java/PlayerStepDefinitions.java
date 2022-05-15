import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlayerStepDefinitions {
    @Given("I have a player")
    public void iHaveAPlayer(){

    }

    @And("I have an asteroid")
    public void iHaveAnAsteroid() {
    }

    @And("Asteroid has <shellnumber> shells")
    public void asteroidHasShellnumberShells(int shellnumber) {
    }

    @And("Asteroid has a core")
    public void asteroidHasACore() {
    }

    @And("Player stands on asteroid")
    public void playerStandsOnAsteroid() {
    }

    @When("Player mines")
    public void playerMines() {
    }

    @Then("Player should have mined a mineral")
    public void playerShouldHaveMinedAMineral() {
    }
}
