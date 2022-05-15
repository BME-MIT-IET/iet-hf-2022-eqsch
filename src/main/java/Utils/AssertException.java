package Utils;

/**
 * An exception class for asserts.
 */
public class AssertException extends Exception{
    /**
     * The message we want to write out if the exception occurs.
     */
    String Message;

    // Extends the message with "Assert: "
    public AssertException(String msg){
        Message = "Assert: " + msg;
    }

    /**
     * The getter of the message.
     * @return The message.
     */
    @Override
    public String getMessage(){
        return Message;
    }
}
