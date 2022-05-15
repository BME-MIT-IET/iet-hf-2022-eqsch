import Controllers.FileController;
import Controllers.GameController;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class PlayerTests {
    String dir = System.getProperty("user.dir");

    @Test
    public void playerDrillsAndMines() throws Exception {
        GameController gc = new GameController();
        FileController fc = new FileController();

        gc.InterpretCommand("load /src/test/resources/playerDrillsAndMines_load.txt");



        for(int i=0; i<3; i++){
            gc.InterpretCommand("p 2 drill");
        }
        gc.InterpretCommand("p 2 mine");
        gc.InterpretCommand("save /src/test/resources/playerDrillsAndMines_save.txt");

        File e = new File(dir + "/src/test/resources/playerDrillsAndMines_check.txt");
        File r = new File(dir + "/src/test/resources/playerDrillsAndMines_save.txt");

        boolean res;
        try{
            fc.Compare(e, r);
            res = true;
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            res = false;
        }

        Assert.assertTrue(res);
    }
}
