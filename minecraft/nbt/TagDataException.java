package minecraft.nbt;


/**
 * Write a description of class TagDataFormatException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TagDataException extends RuntimeException
{
    // instance variables - replace the example below with your own
    /**
     * Constructor for objects of class TagDataFormatException
     */
    public TagDataException(String msg) {
    super(msg);    
    }
    public TagDataException()
    {
        this("Requested data is not in available from this tag type");
    }
    

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
}
