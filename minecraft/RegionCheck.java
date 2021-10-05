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
 * The test class RegionCheck.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class RegionCheck
{
    /**
     * Default constructor for test class RegionCheck
     */
    public RegionCheck()
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

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test
    public void testBlockData() throws IOException
    {
    File rFile = new File("r.-11.-4.mca");
    Region region = new Region(rFile);
    Chunk c = region.getChunk(4,31); 
    Block b = c.getBlock(0, 0, 0);
    assertEquals(0,b.skyLight);
    }
    @Test
    public void testBlockDataChange() throws IOException {
    File rFile = new File("r.-11.-4.mca");
    Region region = new Region(rFile);
    Chunk c = region.getChunk(4,31); 
    Block dataBlock = Block.createBlock(1,1,1,1);
    c.setBlock(0,0,0,dataBlock);
    Block retBlock = c.getBlock(0,0,0);
    assertEquals(dataBlock, retBlock);
    }
    @Test
    public void testHeightMap() throws IOException {
    //checks if heightmap returns heightest block
    File rFile = new File("r.-11.-4.mca");
    Region region = new Region(rFile);
    Chunk c = region.getChunk(4,31); 
    boolean isAir = false;
    boolean isNotAir = false;
    for (int z = 0; z < 16; z++) {
    for (int x = 0; x < 16; x++) {
    Block block = c.getBlock(x,c.getHeightMap(x, z),z);
    if (block.id == 0) {isAir = true;}
    Block air = c.getBlock(x, c.getHeightMap(x, z) + 1, z);
    if (air.id != 0) {isNotAir = true;}
    }
    }
    assertFalse(isAir);
    assertFalse(isNotAir);
    }
    @Test
    public void chunkSetBlockTest() throws IOException {
    File rFile = new File("r.-11.-4.mca");
    Region region = new Region(rFile);
    Chunk c = region.getChunk(4,31);     
    Block block = Block.createBlock(0);
    c.setBlock(0, 10, 0, block);
    Block inserted = c.getBlock(0,10,0);
    assertEquals(inserted, block);
    }
    @Test
    public void heightMapTest() throws IOException {
    File rFile = new File("r.-11.-4.mca");
    Region region = new Region(rFile);
    Chunk c = region.getChunk(4,31);     
    Block air = Block.createBlock(0);
    Block solidBlock = Block.createBlock(1);
    int original = c.getHeightMap(0,0);
    //checks that air addition does not change heightmap
    c.setBlock(0,255,0,air);
    assertEquals(original,c.getHeightMap(0,0));
    
    //checks if adding a solid block changes the heightmap
    c.setBlock(0,255,0,solidBlock);
    assertEquals(255, c.getHeightMap(0,0));
    
    //checks if removing block returns to original value
    c.setBlock(0,255,0,air);
    assertEquals(original, c.getHeightMap(0,0));
    }
    @Test
    public void emptyChunkWorks() {
    Chunk c = new Chunk();    
    assertNotNull(c);
    System.out.println(c);
    }
}
