package minecraft.nbt;
import java.util.zip.Inflater;
import java.util.zip.Deflater;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.Arrays;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
/**
 * Write a description of class NBTData here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NBTData
{
    protected ArrayList<NBT_Tag> topLevelNBT = null;
    public NBTData(ArrayList<NBT_Tag> topTags) {
        if (topTags == null) {throw new NullPointerException();}
        else {
        topLevelNBT = topTags;
    }
    }
    public NBTData(NBT_Tag[] topTags) {
    topLevelNBT = new ArrayList(topTags.length);
    for (NBT_Tag t:topTags) {
        topLevelNBT.add(t);
    }
    }
    //builds nbt_tags from data
    public NBTData(byte[] uncompressedNBTData) {
        topLevelNBT = new ArrayList(2);
        ByteBuffer bb = ByteBuffer.wrap(uncompressedNBTData);
    do {  
        {
            topLevelNBT.add(recursiveRead(bb));
        }
    } while (bb.hasRemaining());
     }
    
    public final byte[] getAsBytes() {
    int size = 0;
    for (int i = 0; i < topLevelNBT.size(); i++) {
    size += topLevelNBT.get(i).getSize();
    }
    //System.out.println(size);
    ByteBuffer bb = ByteBuffer.allocate(size);
    for (int i = 0; i < topLevelNBT.size(); i++) {
    topLevelNBT.get(i).getBytes(bb);
    }
    return bb.array();
    }
    @Override
    public int hashCode() {
    return topLevelNBT.hashCode();
    }
    @Override
    public boolean equals(Object other) {
        if (other instanceof NBTData) {
        NBTData against = (NBTData)other;
        return Arrays.equals(this.getAsBytes(), against.getAsBytes());    
        }
        else {return false;}
    }
    private NBT_Tag recursiveRead(ByteBuffer bytebuffer) throws BufferUnderflowException {
        //for named tags and tag 0
        StringBuilder sb;
        byte tag_type;
        int strlen;
        tag_type = bytebuffer.get();
        if (tag_type == NBT_Tag.TYPE_NONE) {return new NBT_Tag();}
        strlen = unsignedByte(bytebuffer.get()) * 256 + unsignedByte(bytebuffer.get());
        sb = new StringBuilder(strlen);
        for (int i = 0; i < strlen; i++) {
        sb.append((char)bytebuffer.get());    
        }
        if (tag_type == NBT_Tag.TYPE_BYTE) {return new Named_NBT_Tag(sb.toString(), bytebuffer.get());}
        else if (tag_type == NBT_Tag.TYPE_SHORT) {
            return new Named_NBT_Tag(sb.toString(), bytebuffer.getShort());}
        else if (tag_type == NBT_Tag.TYPE_INT)
        {   
            int i = bytebuffer.getInt();
            return new Named_NBT_Tag(sb.toString(), i);}
        else if (tag_type == NBT_Tag.TYPE_LONG) 
        {return new Named_NBT_Tag(sb.toString(), bytebuffer.getLong());}
        else if (tag_type == NBT_Tag.TYPE_FLOAT) 
        {return new Named_NBT_Tag(sb.toString(), bytebuffer.getFloat());}
        else if (tag_type == NBT_Tag.TYPE_DOUBLE)
        {return new Named_NBT_Tag(sb.toString(), bytebuffer.getDouble());}
        else if (tag_type == NBT_Tag.TYPE_BYTE_ARRAY)
        {
        int numberOf = bytebuffer.getInt();
        byte[] bytes = new byte[numberOf];
        bytebuffer.get(bytes, 0, numberOf);
        return new Named_NBT_Tag(sb.toString(), bytes);
        }
        else if(tag_type == NBT_Tag.TYPE_STRING) {
        int length = unsignedByte(bytebuffer.get()) * 256 + unsignedByte(bytebuffer.get());
        StringBuilder str = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
        str.append((char)bytebuffer.get());    
        }
        return new Named_NBT_Tag(sb.toString(), str.toString());
        }
        else if (tag_type == NBT_Tag.TYPE_LIST) {
            byte tagId = bytebuffer.get();
            int entries = bytebuffer.getInt();
            //to deal with empty lists that should be a list of compound tags
            if (tagId == NBT_Tag.TYPE_NONE) {
                tagId = NBT_Tag.TYPE_COMPOUND;
            }
            Named_NBT_Tag list_tag = new Named_NBT_Tag(sb.toString(), true, tagId);
            for (int i = 0; i < entries; i++) {
                list_tag.addSubtag(recursiveRead(bytebuffer,tagId));
            }
            return list_tag;
        }
        else if (tag_type == NBT_Tag.TYPE_COMPOUND) {
            Named_NBT_Tag compound = new Named_NBT_Tag(sb.toString() ,false, NBT_Tag.TYPE_COMPOUND);
            //byte is not important, fix later
            NBT_Tag tag;
            do {
            tag = recursiveRead(bytebuffer);
            compound.addSubtag(tag);
            } while(tag.Tag_Type != NBT_Tag.TYPE_NONE);
            return compound;
        }
        else if (tag_type == NBT_Tag.TYPE_INTEGER_ARRAY) {
            int numberOf = bytebuffer.getInt();
            int[] integers = new int[numberOf];
            for (int i = 0; i < integers.length; i++) {
            integers[i] = bytebuffer.getInt();    
            }
            return new Named_NBT_Tag(sb.toString(), integers);
        }
        else if (tag_type == NBT_Tag.TYPE_LONG_ARRAY) {
            int numberOf = bytebuffer.getInt();
            long[] longs = new long[numberOf];
            for (int i = 0; i < longs.length; i++) {
            longs[i] = bytebuffer.getLong();    
            }
            return new Named_NBT_Tag(sb.toString(), longs);
        }
        else {System.out.println(tag_type); return null;}
        //should not return this, indicates error
        
    }
    private NBT_Tag recursiveRead(ByteBuffer bytebuffer,byte tag_type) throws BufferUnderflowException
    //for reading in unnamed tags
    {
        if (tag_type == NBT_Tag.TYPE_BYTE) {return new NBT_Tag(bytebuffer.get());}
        else if (tag_type == NBT_Tag.TYPE_SHORT) {
            return new NBT_Tag(bytebuffer.getShort());}
        else if (tag_type == NBT_Tag.TYPE_INT)
        {return new NBT_Tag(bytebuffer.getInt());}
        else if (tag_type == NBT_Tag.TYPE_LONG) 
        {return new NBT_Tag(bytebuffer.getLong());}
        else if (tag_type == NBT_Tag.TYPE_FLOAT) 
        {return new NBT_Tag(bytebuffer.getFloat());}
        else if (tag_type == NBT_Tag.TYPE_DOUBLE)
        {return new NBT_Tag(bytebuffer.getDouble());}
        else if (tag_type == NBT_Tag.TYPE_BYTE_ARRAY)
        {
        int numberOf = bytebuffer.getInt();
        byte[] bytes = new byte[numberOf];
        bytebuffer.get(bytes, 0, numberOf);
        return new NBT_Tag(bytes);
        }
        else if(tag_type == NBT_Tag.TYPE_STRING) {
        int length = unsignedByte(bytebuffer.get()) * 256 + unsignedByte(bytebuffer.get());
        StringBuilder str = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
        str.append((char)bytebuffer.get());    
        }
        return new NBT_Tag(str.toString());
        }
        else if (tag_type == NBT_Tag.TYPE_LIST) {
        byte tagId = bytebuffer.get();
        int entries = bytebuffer.getInt();
        NBT_Tag list_tag = new NBT_Tag(true, tagId);
        for (int i = 0; i < entries; i++) {
            list_tag.addSubtag(recursiveRead(bytebuffer,tagId));
        }    
        return list_tag;
        }
        else if (tag_type == NBT_Tag.TYPE_COMPOUND) {
            NBT_Tag compound = new NBT_Tag(false, NBT_Tag.TYPE_COMPOUND);
            //byte is not important, fix later
            NBT_Tag tag;
            do {
            tag = recursiveRead(bytebuffer);
            compound.addSubtag(tag);
            } while(tag.Tag_Type != NBT_Tag.TYPE_NONE);
            return compound;
        }
        else if (tag_type == NBT_Tag.TYPE_INTEGER_ARRAY) {
            int numberOf = bytebuffer.getInt();
            int[] integers = new int[numberOf];
            for (int i = 0; i < integers.length; i++) {
            integers[i] = bytebuffer.getInt();    
            }
            return new NBT_Tag(integers);
        }
        else if (tag_type == NBT_Tag.TYPE_LONG_ARRAY) {
            int numberOf = bytebuffer.getInt();
            long[] longs = new long[numberOf];
            for (int i = 0; i < longs.length; i++) {
            longs[i] = bytebuffer.getLong();    
            }
            return new NBT_Tag(longs);
        }
        else {System.out.println(tag_type); return null;}
        //should not return this, indicates error
    }
    @Override
    public String toString() {
    StringBuilder compiledTagS = new StringBuilder();
    for (int i = 0; i < topLevelNBT.size(); i++) {
        compiledTagS.append(topLevelNBT.get(i).toString());
    }
    String changeStr = compiledTagS.toString();
    String[] sep = changeStr.split("\n");
    int spaceAdd = 0;
    compiledTagS = new StringBuilder();
    for (int i = 0; i < sep.length; i++) {
    if (sep[i].charAt(0) == '{')
        {spaceAdd++;}
    String spaces = "";
    for (int j = 0; j < spaceAdd; j++) {
    spaces = spaces + "\t";        
    }
    if (sep[i].charAt(0) == '}')
    {spaceAdd--;}
    compiledTagS.append(spaces);
    compiledTagS.append(sep[i]);
    compiledTagS.append("\n");
    }
    return compiledTagS.toString();
    }
    //public static utility functions
    public static byte[] decompressNBT(byte[] compressedData, byte compressionType) 
    throws DataFormatException, IOException {
    byte[] uncompressed = new byte[compressedData.length * 30];
    int cropTo;
    switch (compressionType) {
    case 1:
    ByteArrayInputStream bIn = new ByteArrayInputStream(compressedData);
    GZIPInputStream decompress = new GZIPInputStream(bIn);
    cropTo = 0;
    int integerByte = decompress.read();
    while (integerByte != -1) {
        byte b = (byte)integerByte;
        uncompressed[cropTo++] = b;
        integerByte = decompress.read();
        if (cropTo == uncompressed.length) {
            byte[] extendedUncompressed = new byte[uncompressed.length * 2];
            for (int i = 0; i < uncompressed.length;i++) {
            extendedUncompressed[i] = uncompressed[i];    
            }
            uncompressed = extendedUncompressed;
        }
    }
    uncompressed = Arrays.copyOf(uncompressed,cropTo);
    break;
    case 2:
    ArrayList<byte[]> allUncompressed = new ArrayList(4);
    Inflater decompresser = new Inflater();
    decompresser.setInput(compressedData);
    do {
    uncompressed = new byte[compressedData.length * 30];
    cropTo = decompresser.inflate(uncompressed);
    if (decompresser.needsInput()) {
    uncompressed = Arrays.copyOf(uncompressed, cropTo);
    }
    allUncompressed.add(uncompressed);
    } while(!decompresser.needsInput());
    decompresser.end();
    int unSize = 0;
    for (int i = 0; i < allUncompressed.size(); i++)
    {
    unSize += allUncompressed.get(i).length;    
    }
    uncompressed = new byte[unSize];
    unSize = 0;
    for (int i = 0; i < allUncompressed.size(); i++) {
    byte[] part = allUncompressed.get(i);
    for (int j = 0; j < part.length; j++) {
    uncompressed[unSize++] = part[j];    
    }
    }
    break;
    default: //case 3, uncompressed
    uncompressed = compressedData;
    
    }
    return uncompressed;    
    }
    public static byte[] compressNBT(byte[] uncompressedData, byte compressionType) throws IOException{
    byte[] compressedData = new byte[uncompressedData.length];
    switch (compressionType) {
    case 1:
    ByteArrayOutputStream bOS = new ByteArrayOutputStream();
    GZIPOutputStream gZipCompress= new GZIPOutputStream(bOS);
    gZipCompress.write(uncompressedData, 0, uncompressedData.length);
    gZipCompress.flush();
    gZipCompress.finish();
    return bOS.toByteArray();
    case 2:
    Deflater compresser = new Deflater();
    compresser.setInput(uncompressedData);
    compresser.finish();
    int cropTo = compresser.deflate(compressedData);
    compressedData = Arrays.copyOf(compressedData,cropTo);
    return compressedData;
    case 3: //uncompressed; don't compress
    return compressedData;
    }
    //if compression type is not supported, returns null
    return null;
    }
    public static int unsignedByte(byte b) {
    return b & 0xFF;    
    }
}
