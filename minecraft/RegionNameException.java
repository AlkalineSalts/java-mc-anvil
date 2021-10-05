package minecraft;


/**
 * Write a description of class TagDataFormatException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RegionNameException extends RuntimeException
{
    // instance variables - replace the example below with your own
    /**
     * Constructor for objects of class TagDataFormatException
     */
    public RegionNameException(String msg) {
    super(msg);    
    }
    public RegionNameException()
    {
        this("Region File is not in the minceraft format \"r.(int).(int).mca\"");
    }
    

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
}
