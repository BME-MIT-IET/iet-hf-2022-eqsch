import Controllers.FileController;
import Controllers.GameController;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class AITakesActionTests {
    String dir = System.getProperty("user.dir");

    @Test
    public void RobotTakesAction() throws Exception {
        GameController gc = new GameController();
        FileController fc = new FileController();

        gc.InterpretCommand("load /src/test/resources/robotTakesAction_load.txt");
        gc.InterpretCommand("p 2 skip");
        gc.InterpretCommand("p 2 skip");
        gc.InterpretCommand("save /src/test/resources/robotTakesAction_save.txt");

        File e = new File(dir + "/src/test/resources/robotTakesAction_check.txt");
        File r = new File(dir + "/src/test/resources/robotTakesAction_save.txt");

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

    @Test
    public void UFOTakesAction() throws Exception {
        GameController gc = new GameController();
        FileController fc = new FileController();

        gc.InterpretCommand("load /src/test/resources/ufoTakesAction_load.txt");
        gc.InterpretCommand("p 2 skip");
        gc.InterpretCommand("p 2 skip");
        gc.InterpretCommand("save /src/test/resources/ufoTakesAction_save.txt");

        File e = new File(dir + "/src/test/resources/ufoTakesAction_check.txt");
        File r = new File(dir + "/src/test/resources/ufoTakesAction_save.txt");

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
