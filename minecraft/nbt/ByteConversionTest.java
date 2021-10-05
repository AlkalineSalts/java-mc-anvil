package minecraft.nbt;
import minecraft.Chunk;
import minecraft.Region;
import org.junit.*;
import minecraft.nbt.Named_NBT_Tag;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
/**
 * The test class ByteConversionTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ByteConversionTest
{
    /**
     * Default constructor for test class ByteConversionTest
     */
    public ByteConversionTest()
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
    
    public void compoundTagNamedCheck()
    {
        String name = "name";
        Named_NBT_Tag byteTag = new Named_NBT_Tag(name,(byte)1);
        Named_NBT_Tag shortTag = new Named_NBT_Tag(name,(short)1);
        Named_NBT_Tag intTag = new Named_NBT_Tag(name,1);
        Named_NBT_Tag floatTag = new Named_NBT_Tag(name,1.0f);
        Named_NBT_Tag doubleTag = new Named_NBT_Tag(name,1.0);
        Named_NBT_Tag comp = new Named_NBT_Tag(name,false, NBT_Tag.TYPE_BYTE);
        comp.addSubtag(byteTag);
        comp.addSubtag(shortTag);
        comp.addSubtag(intTag);
        comp.addSubtag(floatTag);
        comp.addSubtag(doubleTag);
        comp.addSubtag(new NBT_Tag());
    }
    @Test
    public void chunkCompressionTest() throws IOException, DataFormatException{
    File dimFile = new File("r.-11.-4.mca");
    Region r = new Region(dimFile);
    Chunk c = r.getChunk(4, 31);    
    byte[] un = c.getAsBytes();
    byte[] comp = NBTData.compressNBT(un,(byte)2);
    byte[] uncomp = NBTData.decompressNBT(comp,(byte)2);
    Assert.assertTrue(Arrays.equals(un,uncomp));
    RandomAccessFile rma = new RandomAccessFile("compressedChunk.txt", "rw");
    rma.write(comp);
    }
    }

