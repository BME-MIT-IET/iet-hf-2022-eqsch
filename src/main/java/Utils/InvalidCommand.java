package Utils;

import java.util.ArrayList;

/**
 * An exception class for invalid commands.
 */
public class InvalidCommand extends Exception{
    String line;
    public  InvalidCommand(String Line){line = Line;}

    public  InvalidCommand(String Line, ArrayList<String> Args,String ErrorMsg){
        line = Line;
        for(String it : Args){
            line += " " + it;
        }
        line += ErrorMsg;
    }

    /**
     * The getter of the error message.
     * It contains the place where the mistake happened and possible reasons.
     * @return
     */
    @Override
    public String getMessage(){
        return "An invalid command was entered: " + line;
    }
}
