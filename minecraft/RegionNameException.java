package minecraft;


/**
 * Represents a problem with region names.
 *
 * @author Will Rantis
 * @version 10/5/21
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
