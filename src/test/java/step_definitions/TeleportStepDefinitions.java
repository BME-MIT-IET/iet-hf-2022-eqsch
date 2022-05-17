package step_definitions;

import Model.Asteroid;
import Model.TeleportGate;
import io.cucumber.java.en.And;

public class TeleportStepDefinitions {
    TeleportGate teleportGate=new TeleportGate(0);
    @And("Teleport is active")
    public void teleportIsActive() {
        TeleportGate pair =new TeleportGate(1);
        pair.getNeighbours().add(new Asteroid(3));
        teleportGate.setPair(pair);
    }

    @And("Teleport is not active")
    public void teleportIsNotActive() {
        if (teleportGate.getPair()!=null){
            teleportGate.getPair().setPair(null);
        }
    }
}
