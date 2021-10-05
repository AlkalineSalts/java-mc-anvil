package minecraft;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Arrays;
import minecraft.*;
/**
 * The test class DimensionTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DimensionTest
{
    /**
     * Default constructor for test class DimensionTest
     */
    public DimensionTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    @Test
    public void testDimension() {
      File dim = new File("/Users/williamrantis/Library/Application Support/minecraft/saves/The Maw/region");
      Dimension d = new Dimension(dim);
      int[] minXZ = d.getLowestRegion();
      int[] maxXZ = d.getHighestRegion();
      Dimension sub = new Dimension(d, minXZ[0], minXZ[1], maxXZ[0], maxXZ[1]);
      int[] sminXZ = sub.getLowestRegion();
      int[] smaxXZ = sub.getHighestRegion();
      assertEquals(sminXZ[0], minXZ[0]);   
      assertEquals(sminXZ[1], minXZ[1]);   
      assertEquals(smaxXZ[0], maxXZ[0]);   
      assertEquals(smaxXZ[1], maxXZ[1]);   
      assertEquals(d.toString(), sub.toString());
      
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
