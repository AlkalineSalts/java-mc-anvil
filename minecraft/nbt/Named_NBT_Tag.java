package minecraft.nbt;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.util.Arrays;
/**
 * Write a description of classNamed_NBT_Tag here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Named_NBT_Tag extends NBT_Tag
{
    // instance variables - replace the example below with your own
    
    public final String Tag_Name;
    /**
     * Constructor for objects of classNamed_NBT_Tag
     */
    //constructor for tag NBT_Tag.TYPE 1
    public Named_NBT_Tag(String name, byte data) {
        super(data);
        if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 2
    public Named_NBT_Tag(String name, short data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 3
    public Named_NBT_Tag(String name, int data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 4
    public Named_NBT_Tag(String name, long data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 5
    public Named_NBT_Tag(String name, float data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 6
    public Named_NBT_Tag(String name, double data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 7
    public Named_NBT_Tag(String name, byte[] data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 8
    public Named_NBT_Tag(String name, String data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 9 and 10
    public Named_NBT_Tag(String name, boolean isList, byte lType) {
    super(isList, lType);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 11
    public Named_NBT_Tag(String name, int[] data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    //type 12
    public Named_NBT_Tag(String name, long[] data) {
    super(data);
    if (name != null) {Tag_Name = name;} else {Tag_Name = "";}
    }
    @Override
    public int getSize() { //size in bytes
        if (Tag_Type == TYPE_NONE) {
            return 1;
        }
        else {
            return 3 + Tag_Name.length() + super.getSize();
            //3 bytes
            //1 byte is for the tag id 2 are for the length of string
        }        
    }
    @Override
    public void getBytes(ByteBuffer bb) {
        //System.out.println(bb.remaining());
        bb.put(Tag_Type);
        byte firstByte = (byte)(Tag_Name.length() / 256);
        byte secondByte = (byte)(Tag_Name.length() % 256);
        bb.put(firstByte);
        bb.put(secondByte);
        for (int i = 0; i < Tag_Name.length(); i++) {
            byte character = (byte)Tag_Name.charAt(i);
            bb.put(character);
        }
        super.getBytes(bb);
    }
    @Override 
    public boolean isNamedTag() {
    return true;    
    }
    @Override
    public String toString() {
    return "<" + Tag_Name + "> " + super.toString();    
    }
}
