package minecraft.nbt;
import java.util.ArrayList;
import java.nio.ByteBuffer;
/**
 * Write a description of class NBT_Tag here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NBT_Tag
{
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_BYTE  = 1;
    public static final byte TYPE_SHORT = 2;
    public static final byte TYPE_INT = 3;
    public static final byte TYPE_LONG = 4;
    public static final byte TYPE_FLOAT = 5;
    public static final byte TYPE_DOUBLE = 6;
    public static final byte TYPE_BYTE_ARRAY = 7;
    public static final byte TYPE_STRING = 8;
    public static final byte TYPE_LIST = 9;
    public static final byte TYPE_COMPOUND = 10;
    public static final byte TYPE_INTEGER_ARRAY = 11;
    public static final byte TYPE_LONG_ARRAY = 12;
    
    private static final byte[] emptyArr = new byte[0];
    private static final int[] emptyIntArr = new int[0];
    private static final long[] emptyLongArr = new long[0];
    public final byte Tag_Type;
        
    private ArrayList<NBT_Tag> subTags = null; 
    private byte List_Type; // for list type tags only
    
    private long integer;
    private double floating;
    private byte[] byteArr = emptyArr;
    private int[] intArr = emptyIntArr;
    private long[] longArr = emptyLongArr;
    private String tag_data;
    public NBT_Tag() {
        Tag_Type = NBT_Tag.TYPE_NONE;
    }
    //constructor for tag NBT_Tag.TYPE 1
    public NBT_Tag(byte data) {
        Tag_Type = NBT_Tag.TYPE_BYTE;
        integer = data;
    }
    //type 2
    public NBT_Tag(short data) {
    Tag_Type = NBT_Tag.TYPE_SHORT;
    integer = data;
    }
    //type 3
    public NBT_Tag(int data) {
    Tag_Type = NBT_Tag.TYPE_INT;
    integer = data;
    }
    //type 4
    public NBT_Tag(long data) {
    Tag_Type = NBT_Tag.TYPE_LONG;
    integer = data;
    }
    //type 5
    public NBT_Tag(float data) {
    Tag_Type = NBT_Tag.TYPE_FLOAT;
    floating = data;
    }
    //type 6
    public NBT_Tag(double data) {
    Tag_Type = NBT_Tag.TYPE_DOUBLE;
    floating = data;
    }
    //type 7
    public NBT_Tag(byte[] data) {
    Tag_Type = NBT_Tag.TYPE_BYTE_ARRAY;
    byteArr = data;
    }
    //type 8
    public NBT_Tag(String data) {
    Tag_Type = NBT_Tag.TYPE_STRING;
    tag_data = data;
    }
    //type 9 and 10
    public NBT_Tag(boolean isList, byte listType) {
    subTags = new ArrayList();
    if (isList) {Tag_Type = NBT_Tag.TYPE_LIST; List_Type = listType;}
    else {Tag_Type = NBT_Tag.TYPE_COMPOUND;
    subTags.add(new NBT_Tag());
    }
    }
    
    //type 11
    public NBT_Tag(int[] data) {
    Tag_Type = NBT_Tag.TYPE_INTEGER_ARRAY;
    intArr = data;
    }
    //type 12
    public NBT_Tag(long[] data) {
    Tag_Type = NBT_Tag.TYPE_BYTE_ARRAY;
    longArr = data;
    }
    public byte getByte() {
    if (Tag_Type == NBT_Tag.TYPE_BYTE)    {
        return (byte)integer;
    }
    else {throw new TagDataException();}
    }
    public void setByte(byte b) {
        if (Tag_Type == NBT_Tag.TYPE_BYTE)    {
        integer = b;
    }
    else {throw new TagDataException();}
    }
    public short getShort() {
    if (Tag_Type == NBT_Tag.TYPE_SHORT)    {
        return (short)integer;
    }
    else {throw new TagDataException();}
    }
    public void setShort(short s) {
        if (Tag_Type == NBT_Tag.TYPE_SHORT)    {
        integer = s;
    }
    else {throw new TagDataException();}
    }
    public int getInt() {
    if (Tag_Type == NBT_Tag.TYPE_INT)    {
        return (int)integer;
    }
    else {throw new TagDataException();}
    }
    public void setInt(int i) {
    if (Tag_Type == NBT_Tag.TYPE_INT)    {
        integer = i;
    }
    else {throw new TagDataException();}    
    }
    public long getLong() {
    if (Tag_Type == NBT_Tag.TYPE_LONG)    {
        return integer;
    }
    else {throw new TagDataException();}
    }
    public void setLong(long l) {
    if (Tag_Type == NBT_Tag.TYPE_LONG)    {
        integer = l;
    }
    else {throw new TagDataException();}    
    }
    public float getFloat() {
    if (Tag_Type == NBT_Tag.TYPE_FLOAT)    {
        return (float)floating;
    }
    else {throw new TagDataException();}
    }
    public void setFloat(float f) {
    if (Tag_Type == NBT_Tag.TYPE_FLOAT)    {
        floating = f;
    }
    else {throw new TagDataException();}    
    }
    public double getDouble() {
    if (Tag_Type == NBT_Tag.TYPE_DOUBLE)    {
        return floating;
    }
    else {throw new TagDataException();}
    }
    public void setDouble(double d) {
    if (Tag_Type == NBT_Tag.TYPE_DOUBLE)    {
        floating = d;
    }
    else {throw new TagDataException();}    
    }
    public byte[] getByteArray() {
    if (Tag_Type == NBT_Tag.TYPE_BYTE_ARRAY)  {
        return byteArr;
    }
    else {throw new TagDataException();}
    }
    public String getString() {
        if (Tag_Type == NBT_Tag.TYPE_STRING) {
        return tag_data;    
        }
        else {throw new TagDataException();}
    }
    public int[] getIntArray() {
        if (Tag_Type == NBT_Tag.TYPE_INTEGER_ARRAY) {
            return intArr;   
        }
        else {throw new TagDataException();}
    }
    public long[] getLongArray() {
        if (Tag_Type == NBT_Tag.TYPE_LONG_ARRAY) {
            return longArr;   
        }
        else {throw new TagDataException();}
    }
    public void addSubtag(NBT_Tag nbt_tag) {
        if (this.Tag_Type == NBT_Tag.TYPE_COMPOUND)
        {
            if (nbt_tag.isNamedTag() || nbt_tag.Tag_Type == TYPE_NONE) 
            {
            if (nbt_tag.Tag_Type == TYPE_NONE) {}
            else {
            subTags.add(subTags.size()-1, nbt_tag);
             }
            }
        else {throw new TagDataException("Only named tags or TYPE_NONE can be added to Compound tag");}  
        }
        else if (this.Tag_Type == NBT_Tag.TYPE_LIST) {
            if (!nbt_tag.isNamedTag()) {
            if (nbt_tag.Tag_Type == this.List_Type) {
            subTags.add(nbt_tag);    
            } else {throw new TagDataException("Lists only accept one type of tag: "+List_Type);}
            }
            else {throw new TagDataException("Only unnamed tags can be added to List tag");}
    }
    else {throw new TagDataException();}
    }
    public boolean isNamedTag() {
    return false;    
    }
    public int getSize() { //in number of bytes
       switch (Tag_Type) {
       case TYPE_NONE:
       return 1;
       case TYPE_BYTE:
       return 1;
       case TYPE_SHORT:
       return 2;
       case TYPE_INT:
       return 4;
       case TYPE_LONG:
       return 8;
       case TYPE_FLOAT:
       return 4;
       case TYPE_DOUBLE:
       return 8;
       case TYPE_BYTE_ARRAY:
       return 4 + byteArr.length;
       case TYPE_STRING:
       return 2 + tag_data.length(); //two bytes which hold string length
       case TYPE_LIST:
       if (subTags.size() > 0) {
       int list_Psize = 5; //5 for byte tagID(not the lists tag_type) and 4 for number
       //of elements
       for (int i = 0; i < subTags.size(); i++)
       {
       list_Psize += subTags.get(i).getSize();    
       }
       return list_Psize;
       }
       else {return 5;}
       case TYPE_COMPOUND:
       int compound_Psize = 0;
       for (int i = 0; i < subTags.size(); i++) {
           compound_Psize += subTags.get(i).getSize();
       }
       return compound_Psize;
       case TYPE_INTEGER_ARRAY:
       return 4 + intArr.length * 4; //4 + is for the size declaration
       case TYPE_LONG_ARRAY:
       return 4 + longArr.length * 8;
       }
      //should never reach here, -1 for bad return
       return -1;
    }
    public void getBytes(ByteBuffer bb) {
    if (Tag_Type == TYPE_NONE) {
        byte none = 0;
        bb.put(none);
    }
    else if (Tag_Type == TYPE_BYTE) {
        bb.put(getByte());
    }
    else if (Tag_Type == TYPE_SHORT) {
        bb.putShort(getShort());
    }
    else if (Tag_Type == TYPE_INT) {
        bb.putInt(getInt());
    }
    else if (Tag_Type == TYPE_LONG) {
        bb.putLong(getLong());
    }
    else if (Tag_Type == TYPE_FLOAT) {
        bb.putFloat(getFloat());
    }
    else if (Tag_Type == TYPE_DOUBLE) {
        bb.putDouble(getDouble());
    }
    else if (Tag_Type == TYPE_BYTE_ARRAY) {
        bb.putInt(byteArr.length);
        bb.put(byteArr);
    }
    else if (Tag_Type == TYPE_STRING) {
        byte firstByte = (byte)(tag_data.length() / 256);
        byte secondByte = (byte)(tag_data.length() % 256);
        bb.put(firstByte);
        bb.put(secondByte);
        for (int i = 0; i < tag_data.length(); i++) {
        byte character = (byte)tag_data.charAt(i);
        bb.put(character);
        }
    }
    else if (Tag_Type == TYPE_LIST) {
        bb.put(List_Type);
        int numTags = subTags.size(); 
        bb.putInt(numTags);
        for (int i = 0; i < numTags; i++) {
            subTags.get(i).getBytes(bb);    
        }
    }
    else if (Tag_Type == TYPE_COMPOUND) {
       for (int i = 0; i < subTags.size(); i++) {
           subTags.get(i).getBytes(bb);    
        } 
    }
    else if (Tag_Type == TYPE_INTEGER_ARRAY) {
        bb.putInt(intArr.length);
        for (int i = 0; i < intArr.length; i++) {
            bb.putInt(intArr[i]);
        }
    }
    else if (Tag_Type == TYPE_LONG_ARRAY) {
        bb.putInt(longArr.length);
        for (int i = 0; i < longArr.length; i++) {
            bb.putLong(longArr[i]);    
        }
    }
    }
    public NBT_Tag[] getSubtags() {
        if (Tag_Type == TYPE_LIST || Tag_Type == TYPE_COMPOUND) {
        NBT_Tag[] nbtArr = new NBT_Tag[subTags.size()];
        return subTags.toArray(nbtArr);    
        } else {
            throw new TagDataException();
        }
    }
    public int numberOfSubtags() {
    return subTags.size();    
    }
    public Named_NBT_Tag getSubtagByName(String name) {
    if (Tag_Type == TYPE_LIST || Tag_Type == TYPE_COMPOUND) {
          
        
    for (int i = 0;i < subTags.size(); i++) {
        NBT_Tag tag = subTags.get(i);
        if (tag.isNamedTag()) {
        Named_NBT_Tag named_tag = (Named_NBT_Tag)tag;
        if (name.equals(named_tag.Tag_Name)) {
            return named_tag;}
        }
    }
    //if tag is not in here
    } else {
            throw new TagDataException();
        }
    return null;
    }
    @Override
    public String toString() {
    switch (Tag_Type) {
    case TYPE_NONE:
    return "NONE\n";
    case TYPE_BYTE:
    return "BYTE: "+String.valueOf(NBTData.unsignedByte((byte)integer))+"\n";
    case TYPE_SHORT:
    return "SHORT: "+String.valueOf((short)integer)+"\n";
    case TYPE_INT:
    return "INTEGER: "+String.valueOf((int)integer)+"\n";
    case TYPE_LONG:
    return "LONG: "+String.valueOf(integer)+"\n";
    case TYPE_FLOAT:
    return "FLOAT: "+String.valueOf((float)floating);
    case TYPE_DOUBLE:
    return "DOUBLE: "+String.valueOf(floating) + "\n";
    case TYPE_BYTE_ARRAY:
    StringBuilder bArr = new StringBuilder("BYTE_ARRAY: [");
    for (int i = 0;i < byteArr.length; i++) {
        bArr.append(NBTData.unsignedByte(byteArr[i]));
        bArr.append(", ");
    }
    bArr.append("]");
    bArr.append("\n");
    return bArr.toString();
    case TYPE_STRING:
    return "STRING: \""+tag_data+"\"\n";
    case TYPE_LIST:
    StringBuilder listBuilder = new StringBuilder("LIST:\n{\n");
    for (int i = 0; i < subTags.size(); i++) {
        listBuilder.append(subTags.get(i).toString());
    }
    listBuilder.append("}\n");
    return listBuilder.toString();
    case TYPE_COMPOUND:
    StringBuilder compBuilder = new StringBuilder("COMPOUND:\n{");
    for (int i = 0; i < subTags.size(); i++) {
        compBuilder.append(subTags.get(i).toString());
    }
    compBuilder.append("}\n");
    return compBuilder.toString();
    case TYPE_INTEGER_ARRAY:
    StringBuilder intArrBuild = new StringBuilder("INT_ARRAY: [");
    for (int i = 0; i < intArr.length; i++) {
        intArrBuild.append(String.valueOf(intArr[i]));
        intArrBuild.append(", ");
    }
    intArrBuild.append("]\n");
    return intArrBuild.toString();
    case TYPE_LONG_ARRAY:
    StringBuilder lArr = new StringBuilder("LONG_ARRAY: [");
    for (int i = 0; i < longArr.length;i++) {
    lArr.append(String.valueOf(longArr[i]));
    lArr.append(", ");
    }
    lArr.append("]\n");
    return lArr.toString();
    }
    //should not get here
    return null;
    }
    @Override
    public boolean equals(Object o) {
    NBT_Tag nbt = null;
    try {
    nbt = (NBT_Tag)o;
    } catch (ClassCastException css) {return false;}
    if (this.toString().equals(nbt.toString())) {return true;}
    else {return false;}
    }
}