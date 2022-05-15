import Controllers.GameController;
import Controllers.TestRunner;
import org.junit.Test;
import org.junit.Assert;
import java.io.IOException;

public class OldTests {

    @Test
    public void runAllOldTests() throws IOException {
        TestRunner tc = new TestRunner("teszt");
        GameController gc = new GameController();
        tc.RunAllTests(gc);
        Assert.assertTrue(false);
    }
}
