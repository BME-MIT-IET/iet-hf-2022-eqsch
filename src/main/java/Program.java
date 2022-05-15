import Controllers.FileController;
import Controllers.GameController;
import Controllers.MenuController;
import Controllers.TestRunner;
import Model.Map;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args){
        try {
            MenuController m = new MenuController();
            m.Start();
        } catch (Exception e){
            System.out.println(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }
}
