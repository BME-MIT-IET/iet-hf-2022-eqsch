import Controllers.GameController;
import Controllers.MapBuilder;
import Model.Map;
import Model.Sector;
import UI.Components.MagicConstants;
import org.junit.Assert;
import org.junit.Test;
public class MapGeneratingTests {

    @Test
    public void ObjectsOnGeneratedMap(){
        MapBuilder mb = new MapBuilder();
        GameController gc = new GameController();
        Map map = mb.BuildMap(gc);
        int asteroidCount = 0;
        for(Sector s : map.getSectors()){
            asteroidCount += s.getFields().size();
        }

        Assert.assertEquals(gc.getPs().size(), MagicConstants.shipNumber);
        Assert.assertEquals(gc.getUfo().size(), gc.getPs().size());
        Assert.assertEquals(MagicConstants.asteroidNumber, asteroidCount);
    }
}
