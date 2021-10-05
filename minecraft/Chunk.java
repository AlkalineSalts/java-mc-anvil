package minecraft;
import minecraft.nbt.NBTData;
import minecraft.nbt.NBT_Tag;
import minecraft.nbt.Named_NBT_Tag;
import java.util.ArrayList;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.io.IOException;
/**
 * Write a description of class Chunk here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Chunk extends NBTData
{
    private class Section {
    private NBT_Tag sectionTag;
    public Section(byte sectionNumber, NBT_Tag addToSectionTag) {
        sectionTag = new NBT_Tag(false, (byte)0);
        byte[] blocks = new byte[4096];
        byte[] skylight = new byte[2048];
        byte[] blocklight = new byte[2048];
        for (int i = 0; i < skylight.length; i++) {
        skylight[i] = (byte)255;  
        }
        Named_NBT_Tag blockTag = new Named_NBT_Tag("Blocks",blocks);
        Named_NBT_Tag skyTag = new Named_NBT_Tag("SkyLight",skylight);
        Named_NBT_Tag yTag = new Named_NBT_Tag("Y",sectionNumber);
        Named_NBT_Tag blockLightTag = new Named_NBT_Tag("BlockLight",blocklight);
        Named_NBT_Tag dataTag = new Named_NBT_Tag("Data",new byte[2048]);
        sectionTag.addSubtag(blockTag);
        sectionTag.addSubtag(skyTag);
        sectionTag.addSubtag(yTag);
        sectionTag.addSubtag(blockLightTag);
        sectionTag.addSubtag(dataTag);
        sectionTag.addSubtag(new NBT_Tag());// nonetag
        

        addToSectionTag.addSubtag(sectionTag);

    }
    public Section(NBT_Tag section) {
          sectionTag = section;              
    }
    public Block getBlock(int x, int y, int z) {
        int id = getBlockId(x, y, z);
        int skylight = getBlockData("SkyLight", x, y, z);
        int blocklight = getBlockData("BlockLight" , x, y, z);
        int block_data = getBlockData("Data", x, y, z);
        return Block.createBlock(id, block_data, skylight, blocklight);
    }
    public void setBlock(int x, int y, int z, Block block) {
        setBlockId(x, y, z, block);
        setBlockData(x, y, z, block);
        setBlockSkyLight(x, y, z, block);
        setBlockLight(x, y, z, block);
    }
    public boolean isEmpty() {
        byte[] blocks = new byte[4096];
        byte[] skylight = new byte[2048];
        byte[] blocklight = new byte[2048];
        for (int i = 0; i < skylight.length; i++) {
        skylight[i] = (byte)255;  
        }
        boolean blocksEmpty = Arrays.equals(blocks, sectionTag.getSubtagByName("Blocks").getByteArray());
        boolean skyLightEmpty = Arrays.equals(skylight, sectionTag.getSubtagByName("SkyLight").getByteArray());
        boolean blockLightEmpty = Arrays.equals(blocklight, sectionTag.getSubtagByName("BlockLight").getByteArray());
        return blocksEmpty && skyLightEmpty && blockLightEmpty;
    }
    private void setBlockId(int x, int y, int z, Block block) {
        byte[] blockIdArr = sectionTag.getSubtagByName("Blocks").getByteArray();
        int position = 256 * y + 16 * z + x;
        blockIdArr[position] = (byte)block.id;
    }
    private void setBlockData(int x, int y, int z, Block block) {
        byte[] data = sectionTag.getSubtagByName("Data").getByteArray();
        if (data == null) {return;} // section not present
        int position = (256 * y + 16 * z + x)/2;
        byte[] singleByte = {data[position]};
        BitSet bits = BitSet.valueOf(singleByte);
        if (x%2 == 0) {position = 0;}
        else {position = 4;}
        int blockData = block.blockData;
        boolean[] toBits= new boolean[4];
        if (blockData / 8 == 0) {toBits[0] = true; blockData -=8;}
        if (blockData / 4 == 0) {toBits[1] = true; blockData -= 4;}
        if (blockData / 2 == 0) {toBits[2] = true; blockData -= 2;}
        if (blockData == 1) {toBits[3] = true;}
        for (int i = position; i < position + 4; i++) {
        bits.set(i, toBits[i-position]);
        }
        byte[] finalByte = bits.toByteArray();
        data[position] = finalByte[0];
    }
    private void setBlockSkyLight(int x, int y, int z, Block block) {
        byte[] data = sectionTag.getSubtagByName("SkyLight").getByteArray();
        if (data == null) {return;} // section not present
        int position = (256 * y + 16 * z + x)/2;
        byte[] singleByte = {data[position]};
        BitSet bits = BitSet.valueOf(singleByte);
        if (x%2 == 0) {position = 0;}
        else {position = 4;}
        int lightVal = block.blockLight;
        boolean[] toBits= new boolean[4];
        if (lightVal / 8 == 0) {toBits[0] = true; lightVal -=8;}
        if (lightVal / 4 == 0) {toBits[1] = true; lightVal -= 4;}
        if (lightVal / 2 == 0) {toBits[2] = true; lightVal -= 2;}
        if (lightVal == 1) {toBits[3] = true;}
        for (int i = position; i < position + 4; i++) {
        bits.set(i, toBits[i-position]);
        }
        byte[] finalByte = bits.toByteArray();
        byte b = finalByte[0];
        data[position] = b;
    }
    private void setBlockLight(int x, int y, int z, Block block) {
        byte[] data = sectionTag.getSubtagByName("BlockLight").getByteArray();
        if (data == null) {return;} // section not present
        int position = (256 * y + 16 * z + x)/2;
        byte[] singleByte = {data[position]};
        BitSet bits = BitSet.valueOf(singleByte);
        if (x%2 == 0) {position = 0;}
        else {position = 4;}
        int lightVal = block.blockLight;
        boolean[] toBits= new boolean[4];
        if (lightVal / 8 == 0) {toBits[0] = true; lightVal -=8;}
        if (lightVal / 4 == 0) {toBits[1] = true; lightVal -= 4;}
        if (lightVal / 2 == 0) {toBits[2] = true; lightVal -= 2;}
        if (lightVal == 1) {toBits[3] = true;}
        for (int i = position; i < position + 4; i++) {
        bits.set(i, toBits[i-position]);
        }
        byte[] finalByte = bits.toByteArray();
        byte b = finalByte[0];
        data[position] = b;
    }
    private int getBlockId(int x, int y, int z) {
        byte[] blockIdArr = sectionTag.getSubtagByName("Blocks").getByteArray();
        int position = 256 * y + 16 * z + x;
        int id = NBTData.unsignedByte(blockIdArr[position]);
        return id;
    }
    private int getBlockData(String tagSection, int x, int y, int z) {
        byte[] data = sectionTag.getSubtagByName(tagSection).getByteArray();
        if (data == null) {return 0;} // section not present, so must be 0
        int position = (256 * y + 16 * z + x)/2;
        byte b = data[position];
        int returnValue;
        if (x%2 == 0) {returnValue = (b >> 4) & 0xF; } //even block
        else {returnValue = b & 0xF;} //odd block
        return returnValue;
    }
    }

    // instance variables - replace the example below with your own
    public static byte []  emptyChunk = {120,-100,-19,-38,-17,106,-45,80,24,-64,-31,55,107,-22,-38,
        110,-54,-16,6,-4,-96,-9,33,-120,-126,-62,-124,-62,-90,-32,-57,-72,6,23,-42,53,101,
        -51,68,-67,72,-17,-59,43,-48,-98,-6,7,-12,2,86,-31,125,-98,111,39,9,-100,-28,124,8,
        -121,-28,55,-117,-104,-59,-8,-76,-3,-40,46,-85,120,112,-38,125,-72,28,-26,-3,-6,118,
        -39,12,-19,-94,26,69,-3,101,-34,111,98,-21,40,-90,47,-37,114,-10,117,-77,-114,-88,2,
        -56,107,26,-109,-77,-10,98,-24,-6,-43,102,-74,29,30,31,-58,-67,103,-53,-2,-30,106,-5,
        -78,56,-39,-9,-67,1,0,0,0,119,-32,48,38,103,87,-97,119,31,19,35,38,-15,29,0,72,-89,
        -118,-22,-35,118,79,48,-37,-3,32,-8,-77,43,0,-32,110,-19,-85,-35,-8,61,-17,-61,61,-49,
        -5,120,79,11,-80,-81,-25,-1,-41,-1,-46,-18,28,70,-3,-68,25,26,91,1,0,72,74,61,8,0,0,0,
        -55,-88,7,1,32,-67,82,15,86,-22,65,0,64,61,8,0,-55,-87,7,1,0,0,32,25,-11,32,0,-92,87,
        -22,-63,3,-11,32,0,-96,30,4,-128,-28,-44,-125,0,0,0,-112,-116,122,16,0,-46,43,-11,-32,72,
        61,8,0,-88,7,1,32,57,-11,32,0,0,0,36,-93,30,4,-128,-12,74,61,88,-85,7,1,0,-11,32,0,36,
        -89,30,4,0,0,-128,100,-44,-125,0,-112,94,-87,7,-57,-22,65,0,64,61,8,0,-55,-87,7,1,0,0,
        32,25,-11,32,0,-92,87,-22,-63,80,15,2,0,-22,65,0,72,78,61,8,0,0,0,-55,-88,7,1,32,-67,82,
        15,86,-22,65,0,64,61,8,0,-55,-87,7,1,0,0,32,25,-11,32,0,-92,87,-22,-63,3,-11,32,0,-96,
        30,4,-128,-28,-44,-125,0,0,0,-112,-116,122,16,0,-46,43,-11,-32,72,61,8,0,-88,7,1,32,57,
        -11,32,0,0,0,36,-93,30,4,-128,-12,74,61,88,-85,7,1,0,-11,32,0,36,-89,30,4,0,0,-128,100,
        -44,-125,0,-112,94,-87,7,-57,-22,65,0,64,61,8,0,-55,-43,49,59,109,54,-61,-101,-11,-94,
        25,-38,-97,-121,-66,62,41,73,97,-41,95,-73,-101,-120,42,-90,-55,-43,113,-1,-43,-22,-78,
        121,-33,13,-19,-30,-68,-69,-2,-75,72,-33,30,-115,-94,-2,52,-17,55,101,80,-59,-55,121,
        123,115,-45,116,-85,121,-65,-66,93,110,87,114,81,77,-29,-8,-68,91,-74,47,86,67,55,116,
        -19,-18,-78,-104,-58,-28,-81,113,-116,-30,-88,-20,-60,-34,-74,55,-101,-82,95,69,-116,
        -97,-58,15,-68,34,110,33};
    private Named_NBT_Tag levelTag;
    private int[] heightMap;
    private Section[] sections;
    private int secondsPastEpoch;
    static {
    try {
    emptyChunk = NBTData.decompressNBT(emptyChunk,(byte)2);
    } catch (DataFormatException dfe) {dfe.printStackTrace();}
    catch (IOException ioe) {ioe.printStackTrace();}
    }    
    
    /**
     * Constructor for objects of class Chunk
     */
    
    public Chunk(byte[] uncompressedNBT, int sPE) {
        super(uncompressedNBT);
        levelTag = (Named_NBT_Tag)topLevelNBT.get(0);
        levelTag = levelTag.getSubtagByName("Level");
        heightMap = levelTag.getSubtagByName("HeightMap").getIntArray();
        Named_NBT_Tag sectionTags = levelTag.getSubtagByName("Sections");
        //System.out.println(sectionTags);
        sections = new Section[16];
        NBT_Tag[] nbt_sections = sectionTags.getSubtags();
        for (int i = 0;i < nbt_sections.length;i++) {
        int ybyte = nbt_sections[i].getSubtagByName("Y").getByte();
        sections[ybyte] = new Section(nbt_sections[i]);    
        }
        for (byte i = 0; i < 16; i++) {
        if (sections[i] == null) {
        sections[i] = new Section(i, sectionTags); 
        }
        }
        secondsPastEpoch = sPE;
    }
    public Chunk() {
    this(emptyChunk,0);
    }
    public Block getBlock(int x, int y, int z) {
        if (!(x >= 0 && x < 16 && y >=0 && y <= 256 && z >= 0 && z < 16))
        {return null;}
        int section = y/16;
        int internalSection = y - 16 * section;    
        if (section > sections.length) {
            return Block.createBlock(0);
        }
        else {
        return sections[section].getBlock(x, internalSection, z);    
        }        
    }
    public void setBlock(int x, int y, int z, Block block) {
        Named_NBT_Tag sectionTags = levelTag.getSubtagByName("Sections");
        if (!(x >= 0 && x < 16 && y >=0 && y <= 256 && z >= 0 && z < 16)) {
        return;
        }
        updateTimeStamp();
        int section = y/16;
        int internalSection = y - 16 * section;    
        if (section > sections.length) {
        Section[] newArr = new Section[section+1];    
        for (byte i = 0; i < sections.length; i++) {
            newArr[i] = sections[i];
        }
        for (byte i = (byte)sections.length; i < newArr.length; i++) {
            newArr[i] = new Section(i, sectionTags);
        }
        sections = newArr;
        }
        sections[section].setBlock(x, internalSection, z, block);
        setHeightmap(x, y, z, block);
    }
    private void setHeightmap(int x, int globalY, int z, Block block) {
        //note, fix to make heightmap work with motionblocking
        if (block.id == 0 && globalY == getHeightMap(x,z)) {
            Block b = null;
            do {
                b = getBlock(x, globalY, z);
                if (b == null) {continue;}
            } while(b.id == 0 && globalY-- > 0);
            heightMap[16 * z + x] = globalY + 1;
        }
        else if (globalY > getHeightMap(x,z) && block.id!=0) {
            heightMap[16 * z + x] = globalY + 1;
        }
    }
    public int getLastModified() {
    return secondsPastEpoch;    
    }
    public int getHeightMap(int x, int z) {
        //MOTION_BLOCKING:The highest block that blocks motion or contains a fluid.
        return heightMap[16 * z + x] - 1;
    }
    private void updateTimeStamp() {
        secondsPastEpoch = (int)(System.currentTimeMillis()/1000);
    }
    public void setBiome(Biome biome, int x, int z) {
        if (x > 16 || x < 0 || z > 16 || z < 0) {return;}
        Named_NBT_Tag biomeArray = levelTag.getSubtagByName("Biomes");
        byte[] bArray = biomeArray.getByteArray();
        bArray[16 * z + x] = biome.getId();
    }
    public Biome getBiome(int x, int z) {
        if (x > 16 || x < 0 || z > 16 || z < 0) {return null;}
        Named_NBT_Tag biomeArray = levelTag.getSubtagByName("Biomes");
        byte[] bArray = biomeArray.getByteArray();
        byte id = bArray[16 * z + x];
        for (Biome biome:Biome.values()) {
        if (biome.getId() == id)
        {return biome;}
        }
        return null;
    }
}
