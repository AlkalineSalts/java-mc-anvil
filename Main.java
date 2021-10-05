import minecraft.Region;
import minecraft.Chunk;
import minecraft.nbt.NBTData;
import minecraft.Dimension;
import minecraft.Block;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
    public static void levelTest() throws IOException {
        RandomAccessFile dimFile = new RandomAccessFile("level.dat","r");
        byte[] b = new byte[(int)dimFile.length()];
        dimFile.read(b);
        try {
        byte[] un = NBTData.decompressNBT(b,(byte)1);
        NBTData r = new NBTData(un);
        FileOutputStream fos = new FileOutputStream("l.dat");
        String s = r.toString();
        for (int i = 0; i < s.length(); i++) {
        fos.write(s.charAt(i));    
        }
        fos.close();
    } catch (Exception e) {e.printStackTrace();}
        
    }

    public static void main(String[] args) {
        File dim = new File("The Maw/region");
        Dimension d = new Dimension(dim);
        int[] hxz = d.getHighestRegion();
        int[] lxz = d.getLowestRegion();
        int x = hxz[0] - lxz[0];
        int z = hxz[1] - lxz[1];
        int shiftX = -lxz[0];
        int shiftZ = -lxz[1];
        
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < z; ++j) {
                if (d.regionExists(i-shiftX, j-shiftZ)) {
                    System.out.print("x");
                } else {System.out.print("0");}
         }
            System.out.println("");
        }
        System.out.println("-----------------------");
        System.out.println(d);
        Block[][] world = new Block[x * 512][z * 512];
        System.out.println(x + " " + z);
        /*
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < z; j++) {
                System.out.println(i + " " + j);
                if (d.regionExists(i - shiftX, j - shiftZ)) {
                    
                    Region r = d.getRegion(i - shiftX, j - shiftZ);
                    
                }
            }
        }
        */
        Dimension sub = new Dimension(d, lxz[0]+1, lxz[1]+1, hxz[0]-1 , hxz[1]-1);
        System.out.println(sub);
        System.out.println("-----------------------");
    }
    
    public static void getChunkString() throws IOException {
        File rFile = new File("r.-11.-4.mca");
        Region region = new Region(rFile);
        Chunk c = region.getChunk(4,31);
        String s = c.toString();
        FileOutputStream fos = new FileOutputStream("chunk.txt");
        for (int i = 0; i < s.length(); i++) {
        fos.write((byte)s.charAt(i));    
        }
    }
    public static void getEmptyChunkFile() throws IOException {
    File rFile = new File("r.0.0.mca");
    Region region = new Region(rFile);
    Chunk c = region.getChunk(0,0);
    FileOutputStream fos = new  FileOutputStream("emptyChunk.dat");
    byte[] compressed = NBTData.compressNBT(c.getAsBytes(),(byte)2);
    System.out.print("{");
    for (int i = 0; i < compressed.length; i++) {
    System.out.print(compressed[i]+",");
    }
    System.out.println("}");
    }
}
