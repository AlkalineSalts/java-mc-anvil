package minecraft;
import minecraft.nbt.NBTData;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.nio.ByteBuffer;
/**
 * Write a description of class Region here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Region
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Region
     */
    private static Pattern rPattern = Pattern.compile("r\\.-?[0-9]+\\.-?[0-9]+\\.mca\\z");
    private Chunk[][] chunks = new Chunk[32][32];
    private int regionX;
    private int regionZ;
    private File regionFile;
    public Region(File file, boolean createEmpty) throws IOException {
        regionFile = file;
        String file_name = file.getName();
        Matcher m = rPattern.matcher(file_name);
        if (!m.lookingAt()) {throw new RegionNameException();}
        String[] name_split = file_name.split("\\.");
        regionX = Integer.parseInt(name_split[1]);
        regionZ = Integer.parseInt(name_split[2]);
        if (createEmpty) {
        for (int x = 0; x < 32; x++) {
        for (int z = 0; z < 32; z++) {
        chunks[x][z] = new Chunk();    
        }
        }
        }
        else {
        RandomAccessFile rma = new RandomAccessFile(file,"r");
        byte[] data = new byte[(int)rma.length()];
        rma.read(data);
        for (int x = 0; x < 32;x++) {
            for (int z = 0; z < 32;z++) {
            int[] cLoc = chunkLocation(x, z, data);
            if (cLoc[0] != 0 && cLoc[1] != 0) {
                //System.out.println(cLoc[0] + "," + cLoc[1]);
                chunks[x][z] = new Chunk(getChunkData(x, z, data), getTimeStamp(x, z, data));
            }
            }
        }
        rma.close();
    }
    }
    public Region(File file) throws IOException{
    this(file, false);    
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    private static int headerOffset(int chunkX, int chunkZ)
    {
        //says where chunk location information is
        return  4 * ((chunkX & 31) + (chunkZ & 31) * 32);
    }
    private static int timeStampOffset(int chunkX, int chunkZ) {
    return headerOffset(chunkX,chunkZ) + 4096;    
    }
    public static boolean isRegionFile(String potential) {
        Matcher m = rPattern.matcher(potential);
        return m.lookingAt();
    }
    private int getTimeStamp(int chunkX, int chunkZ, byte[] data) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        int offset = timeStampOffset(chunkX, chunkZ);
        bb.put(data[offset++]);
        bb.put(data[offset++]);
        bb.put(data[offset++]);
        bb.put(data[offset]);
        bb.rewind();
        return bb.getInt();
    }
    private int[] chunkLocation(int chunkX, int chunkZ, byte[] data) {
    //returns offset integer and number of sections
    int[] ret = new int[2];
    int offset = headerOffset(chunkX, chunkZ);
    int biggest = NBTData.unsignedByte(data[offset++]) * 256 * 256;
    int middle = NBTData.unsignedByte(data[offset++]) * 256;
    int last = NBTData.unsignedByte(data[offset++]);
    ret[0] = biggest + middle + last;
    ret[1] = NBTData.unsignedByte(data[offset]);
    return ret;
    }
    private byte[] getChunkData(int chunkX, int chunkZ, byte[] data)  {
    int[] chunkInfo = chunkLocation(chunkX, chunkZ, data);
    if (chunkInfo[0] == 0 && chunkInfo[0] == 0) {
    return null;    
    }
    int chunk_offset = chunkInfo[0] * 4096;
    int byteOne = NBTData.unsignedByte(data[chunk_offset++]) * 256 * 256 * 256;
    int byteTwo = NBTData.unsignedByte(data[chunk_offset++]) * 256 * 256;
    int byteThree = NBTData.unsignedByte(data[chunk_offset++]) * 256;
    int byteFour = NBTData.unsignedByte(data[chunk_offset++]);
    byte compressionType = data[chunk_offset++];
    //System.out.println(compressionType);
    int chunk_length = byteOne + byteTwo +byteThree + byteFour - 1; //length - 1
    //is what the chunk length in bytes, so i'll do the - 1 here now
    byte[] compressedData = Arrays.copyOfRange(data,chunk_offset,chunk_offset + chunk_length);
    byte [] uncompressed = null;
    try {
        uncompressed = NBTData.decompressNBT(compressedData,compressionType);
    } catch(Exception e) {e.printStackTrace();}
    return (uncompressed);
    }
    public Chunk getChunk(int x, int z) {
    if (x > 32 || z > 32 || x < 0 || z < 0) {return null;}
    return chunks[x][z];    
    }
    public void setChunk(int x, int z, Chunk c) {
    if (x > 32 || z > 32 || x < 0 || z < 0) {return;}
    chunks[x][z] = c;
    }
    public void saveToFile() throws IOException {
    saveToFile(regionFile);    
    }
    public void saveToFile(File alternate) throws IOException{
        ArrayList<byte[]> holder = new ArrayList(17);
        int nextFreeSection = 2; //in kb section
        byte[] header = new byte[8192];
        holder.add(header);
        for(int x = 0; x < 32; x++) {
        for(int z = 0; z < 32; z++) {
        Chunk c = getChunk(x, z);
        if (c == null) {continue;}
        //sets up header
        byte[] compressed = NBTData.compressNBT(c.getAsBytes(),(byte)2);
        
        int numberSec = (int)Math.ceil((double)(5 + compressed.length)/4096);
        
        ByteBuffer plusComp = ByteBuffer.allocate(numberSec*4096);
        //
        int whereInHeader = headerOffset(x, z);
        byte[] cPos = ByteBuffer.allocate(4).putInt(nextFreeSection).array();

        header[whereInHeader++] = cPos[1];
        header[whereInHeader++] = cPos[2];
        header[whereInHeader++] = cPos[3];
        header[whereInHeader] = (byte)numberSec;
        
        int tsOffset = timeStampOffset(x, z);
        int timestamp = c.getLastModified();
        byte[] intArr = ByteBuffer.allocate(4).putInt(timestamp).array();
        for (int i = tsOffset; i < tsOffset + 4; i++) {
        header[i] = intArr[i - tsOffset];    
        }
        //compress data in 4096, or 4kiB section, padded w/ 0
        // + 1 because mca reads length - 1 bytes
        
        plusComp.putInt(compressed.length+1);
        plusComp.put((byte)2); // compression type 2
        plusComp.put(compressed);
        
        nextFreeSection += numberSec;
        
        holder.add(plusComp.array());
        //System.out.println(System.currentTimeMillis());
        }
        }
      FileOutputStream fos = new FileOutputStream(alternate);
      for (int i = 0; i < holder.size(); i++) {
      fos.write(holder.get(i));  
      }
      fos.close();
    }
    @Override
    public boolean equals(Object o)
    {
    if (o instanceof Region) {
    Region other = (Region)o;
    
    for (int x = 0; x < 32;x++) {
            for (int z = 0; z < 32;z++) {
                Chunk thisChunk = this.getChunk(x,z);
                Chunk otherChunk = other.getChunk(x,z);
                if (thisChunk == null && otherChunk!= null || thisChunk !=null && otherChunk == null) 
                {return false;}
                if (thisChunk != null) {
                boolean areEqual = thisChunk.equals(otherChunk);
                if (!areEqual) {return false;}
                }
            }
        }
    return true;
    }
    else {return false;}
}

}
