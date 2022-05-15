package Controllers;

import Utils.AssertException;
import Utils.BadFileFormat;
import Utils.InvalidCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is responsible for running tests
 */
public class TestRunner {
    File TestDir;
    String Path;
    int DirNum;

    public TestRunner(String TestRoot) throws IOException {
        TestDir = new File(TestRoot);
        Path = TestDir.getCanonicalPath();
        if(!TestDir.isDirectory())
            throw(new FileNotFoundException());
        File[] files = TestDir.listFiles();
        DirNum = 0;
        for(File f : files){
            if(f.isDirectory())
                DirNum++;
        }
        System.out.println("Found " + files.length + " files of which " + DirNum + " are test directories.");
    }

    /**
     * @param Test the file where the commands are
     * @param gc the GameController
     * @throws Exception
     */
    void Run(File Test,GameController gc) throws Exception {
        File cmd = new File(Test.getCanonicalPath() + "\\cmd.txt");
        Scanner FScanner = new Scanner(cmd);
        while (FScanner.hasNextLine()) {
            String data = FScanner.nextLine();
            System.out.println(data);
            gc.InterpretCommand(data);
        }
    }

    /**
     * This method run all the tests we have
     * @param gc
     */
    public void RunAllTests(GameController gc){
        File[] files = TestDir.listFiles();
        String oldCurr = gc.CurrentWorkingDirectory;
        int SuccessfulTests = 0;
        for(File f : files){
            if(f.isDirectory()) {
                try {
                    System.out.println();
                    System.out.println("Test[" + f.getName() + "]");
                    try {
                        gc.CurrentWorkingDirectory = f.getPath();
                        Run(f,gc);
                        System.out.println("\u001B[32m" + "Test Successful" + "\u001B[0m");
                        SuccessfulTests++;
                    }
                    catch (InvalidCommand invalidCommand) {
                        System.out.println("\u001B[31m" + invalidCommand.getMessage() + "\u001B[0m");
                    }
                    catch (BadFileFormat badFileFormat) {
                        System.out.println("\u001B[31m" + badFileFormat.getMessage() + "\u001B[0m");
                    }
                    catch (AssertException e){
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    System.out.println("Test[" + f.getName() + "] Done");
                }
                catch (IOException e) {
                    System.out.println("Invalid Test Directory in Test Root. Invalid Dir: " + f.getName());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println(SuccessfulTests + " tests were successful out of " + DirNum);
        gc.CurrentWorkingDirectory = oldCurr;
    }

    public void RunTest(String Test,GameController gc){
        String oldCurr = gc.CurrentWorkingDirectory;
        File f = new File(TestDir + "//" + Test);
        try {
            System.out.println();
            System.out.println("Test[" + f.getName() + "]");
            try {
                gc.CurrentWorkingDirectory = f.getPath();
                Run(f,gc);
                System.out.println("\u001B[32m" + "Test Successful" + "\u001B[0m");
            }
            catch (InvalidCommand invalidCommand) {
                System.out.println("\u001B[31m" + invalidCommand.getMessage() + "\u001B[0m");
            }
            catch (BadFileFormat badFileFormat) {
                System.out.println("\u001B[31m" + badFileFormat.getMessage() + "\u001B[0m");
            }
            catch (AssertException e){
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        }
        catch (IOException e) {
            System.out.println("Invalid Test Directory in Test Root. Invalid Dir: " + f.getName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        gc.CurrentWorkingDirectory = oldCurr;
    }

}
