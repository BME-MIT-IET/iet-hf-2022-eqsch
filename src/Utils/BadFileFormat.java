package Utils;

/**
 * An exception class for bad file formats.
 */
public class BadFileFormat extends Exception{
    /**
     * A string representing the reason of the problem.
     */
    String Why;
    /**
     * A string representing the place of the problem.
     */
    String Line;

    public BadFileFormat(String line, String why){Line=line;Why=why;}

    /**
     * The getter of the exception.
     * @return With the combined message of the problem's place and reason.
     */
    @Override
    public String getMessage(){
        return "Save File format is not acceptable: " + Line + " -> " + Why;
    }
}
