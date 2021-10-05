package minecraft;
import java.util.ArrayList;
/**
 * Write a description of class Block here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public final class Block
{
    //block must be immutable 
    private static ArrayList<Block> blockPool = new ArrayList();
    public final int id;
    public final int blockData;
    public final int skyLight;
    public final int blockLight;
    /**
     * Constructor for objects of class Block
     */
    private Block(int blockId)
    {
        this(blockId, 0, 0, 0);
    }
    private Block(int blockId, int bd) {
        this(blockId, bd, 0, 0);
    }
    private Block(int blockId, int bd, int sky, int bl) {
        if (bd > 15) {bd = 15;}
        else if (bd < 0) {bd = 0;}
        if (sky > 15) {sky = 15;}
        else if (sky < 0) {sky = 0;}
        if (bl > 15) {bl = 15;}
        else if (bl < 0) {bl = 0;}
        id = blockId;
        blockData = bd;
        skyLight = sky;
        blockLight = bl;
    }
    public static Block createBlock(int blockId, int bd, int sky, int bl, boolean fromPool) {
        Block newBlock = new Block(blockId, bd, sky, bl);
        if (fromPool) {
        for (Block inArr: blockPool) {
        if (inArr.equals(newBlock)) {return inArr;}  
        }
        //if not in array
        blockPool.add(newBlock);
        return newBlock;
    }
    else {return newBlock;}
    }
    public static Block createBlock(int blockId, boolean fromPool) {
    return createBlock(blockId, 0, 0, 0, fromPool);    
    }
    public static Block createBlock(int blockId) {
    return createBlock(blockId, true);
    }
    public static Block createBlock(int blockId, int blockData, boolean fromPool) {
    return createBlock(blockId, blockData, 0, 0, fromPool);
    }
    public static Block createBlock(int blockId, int blockData) {
        return createBlock(blockId, blockData, true);
    }
    public static Block createBlock(int blockId, int bd, int sky, int bl) {
    return createBlock(blockId, bd, sky, bl, true);    
    }
    @Override
    public boolean equals(Object blockObject) {
        try {
            Block otherBlock = (Block)blockObject;
            if (otherBlock.id == this.id) {return true;} 
            else {return false;}
    } catch (ClassCastException cce) {return false;}
    }
    @Override public int hashCode() {
    return id * 1000 + blockData;    
    }
    @Override
    public String toString() {
    return String.format("Block Id: %d, Block Data: %d, SkyLight: %d, Block Light: %d",id,blockData,skyLight,blockLight);
    }
}

