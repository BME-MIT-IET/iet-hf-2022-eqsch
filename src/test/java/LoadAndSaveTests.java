import Controllers.FileController;
import Controllers.GameController;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class LoadAndSaveTests {

    @Test
    public void loadAndSave() throws Exception {
        GameController gc = new GameController();
        gc.InterpretCommand("load \\src\\test\\resources\\case_1_load.txt");
        gc.InterpretCommand("save \\src\\test\\resources\\case_1_save.txt");

        File e = new File("C:\\Users\\danib\\IdeaProjects\\iet-hf-2022-eqsch\\src\\test\\resources\\case_1_check.txt");
        File r = new File("C:\\Users\\danib\\IdeaProjects\\iet-hf-2022-eqsch\\src\\test\\resources\\case_1_save.txt");

        FileController fc = new FileController();

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
