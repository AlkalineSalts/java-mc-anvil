package minecraft;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;
/**
 * Write a description of class Dimension here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Dimension
{
    // instance variables - replace the example below with your own
    
    private File[][] regions;
    private int shiftX;
    private int shiftZ;
    private File dimensionFile;
    /**
     * 
     */
    public Dimension(File dimFile) 
    {
        dimensionFile = dimFile;
        int minX = 0;
        int minZ = 0;
        int maxX = 0;
        int maxZ = 0;
        for (File regionFile: dimFile.listFiles()) {
            if (regionFile.isFile() && !regionFile.isHidden()) {
            String name = regionFile.getName();
            if (!Region.isRegionFile(name)) {continue;}
            String[] split = name.split("\\.");
            int x = Integer.valueOf(split[1]);
            int z = Integer.valueOf(split[2]);
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minZ = Math.min(minZ, z);
            maxZ = Math.max(maxZ, z);
            }
        }
        int diffX = -minX; shiftX = diffX;
        int diffZ = -minZ; shiftZ = diffZ;
        regions = new File[maxX+diffX+1][maxZ+diffZ+1];
        for (File regionFile: dimFile.listFiles()) {
            if (regionFile.isFile() && !regionFile.isHidden()) {
            String name = regionFile.getName();
            String[] split = name.split("\\.");
            int x = Integer.valueOf(split[1]);
            int z = Integer.valueOf(split[2]);
            //System.out.println(x+","+z+";"+(x+diffX)+","+(z+diffZ));
            regions[x+diffX][z+diffZ] = regionFile;
        }
       }
    }
     /**
     * Gets a subdimension of the given dimension
     * Throws a DimensionException if the dimensions given do not fit within 
     * the given dimension
     * 
     * @param d the dimension that a subdimension will be a subset of
     * @param minX the minimum x coord
     */
    
    public Dimension(Dimension d, int minX, int minZ, int maxX, int maxZ) {
        int[] minXZ = d.getLowestRegion();
        int[] maxXZ = d.getHighestRegion();
        //makes sure parameters are in range
        if (minX < minXZ[0] || minZ < minXZ[1] || maxX > maxXZ[0] ||
        maxZ > maxXZ[1]) {
            throw new DimensionException(
            String.format("Cannot make subdimension: Original Dimension mins X: %d Z: %d, Attempted mins X: %d Z: %d\n",minXZ[0], minXZ[1], minX, minZ)+
            String.format("Original Dimension max X: %d Z: %d, Attempted max X: %d Z: %d",maxXZ[0], maxXZ[1], maxX, maxZ));}
        this.dimensionFile = d.dimensionFile;
        this.shiftX = -minX;
        this.shiftZ = -minZ;
        this.regions = new File[maxX+this.shiftX][maxZ+this.shiftZ];
        for (int i = minX; i < maxX; i++) {
        for (int j = minZ; j < maxZ; j++) {
        this.regions[i+this.shiftX][j+this.shiftZ] = d.regions[i+d.shiftX][j+d.shiftZ];
        }
        }
    }
    /*
    public Block getBlock(int globalX, int globalY, int globalZ) {
        int regionX = globalX / 512;
        int regionZ = globalZ / 512;
        Region r = null;
        try {
        r = regions[regionX+shiftX][regionZ+shiftZ];
        } catch (ArrayIndexOutOfBoundsException aoe) {} //r stays null
        if (r == null) {return null;}
        int chunkX = globalX % 32;
        int chunkZ = globalZ % 32;
        int internalChunkX = globalX % 16;
        int internalChunkZ = globalZ % 16;
        Chunk c = r.getChunk(chunkX, chunkZ);
        if (c == null) {return null;}
        return c.getBlock(internalChunkX, globalY, internalChunkZ);
    }
    public void setBLock(int globalX, int globalY, int globalZ, Block block) {
        int regionX = globalX / 512;
        int regionZ = globalZ / 512;
        Region r = null;
        try {
        r = regions[regionX+shiftX][regionZ+shiftZ];
        } catch (ArrayIndexOutOfBoundsException aoe) {} //r stays null
        if (r == null) {return;}
        int chunkX = globalX % 32;
        int chunkZ = globalZ % 32;
        int internalChunkX = globalX % 16;
        int internalChunkZ = globalZ % 16;
        Chunk c = r.getChunk(chunkX, chunkZ);
        if (c == null) {return;}
        c.setBlock(internalChunkX, globalY, internalChunkZ, block);
    }
    
    */
    /**
     * Determines if the region exists in this dimension
     * 
     * @param x the x coordinate of the region
     * @param z the z coordinate of the region
     * @return if the region exists
     */
    public boolean regionExists(int x, int z) {
    x += shiftX;
    z += shiftZ;
    if (x > regions.length || z > regions[0].length ||
    x < 0 || z < 0) {return false;}
    if (regions[x][z] == null) {return false;}
    else {return true;}
    }
    public Region getRegion(int x, int z) {
        x += shiftX;
        z += shiftZ;
        File rFile = null;
        if (!regionExists(x -shiftX, z - shiftZ)) {
        return null;    
        } 
        rFile = regions[x][z];
        try {
        return new Region(rFile);
        } catch (IOException e) {return null;}
    }
    public int[] getLowestRegion() {
        int[] xz = {0-shiftX, 0-shiftZ};
        return xz;
    }
    public int[] getHighestRegion() {
        int[] high = getLowestRegion();
        int[] xz = {high[0] + regions.length, high[1] + regions[0].length};
        return xz;
    }
    
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int x = 0; x < regions.length; x++) {
        for (int z = 0; z < regions[0].length; z++) {
            if (regions[x][z] == null) {sb.append('0');}
            else {sb.append('x');}
        }
        sb.append('\n');
    }
    return sb.toString();
    }
}
