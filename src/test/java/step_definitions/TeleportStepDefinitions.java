package step_definitions;

import Model.Asteroid;
import Model.TeleportGate;
import io.cucumber.java.en.And;

import static step_definitions.AsteroidStepDefinitions.map;

public class TeleportStepDefinitions {
    static TeleportGate teleportGate=new TeleportGate(map.GetNewUID());
    @And("Teleport is active")
    public void teleportIsActive() {
        TeleportGate pair =new TeleportGate(map.GetNewUID());
        pair.getNeighbours().add(new Asteroid(map.GetNewUID()));
        teleportGate.setPair(pair);
    }

    @And("Teleport is not active")
    public void teleportIsNotActive() {
        if (teleportGate.getPair()!=null){
            teleportGate.getPair().setPair(null);
        }
    }
}
