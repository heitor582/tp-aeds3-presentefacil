package repository;
import java.io.File;
import java.io.RandomAccessFile;

import model.Entity;


public class DBFile<T extends Entity> {
    private final int HEAD_LENGTH = 4;
    private RandomAccessFile file;
    private Class<T> clazz;
    public DBFile(final Class<T> clazz) throws Exception {
        this.clazz = clazz;

        String className = this.clazz.getSimpleName().toLowerCase();
        File dir = new File(".\\data");
        if(!dir.exists())
            dir.mkdir();

        dir = new File(".\\data\\"+className);
        if(!dir.exists())
            dir.mkdir();

        String fileName = ".\\data\\"+className+"\\"+className+".db";
        file = new RandomAccessFile(fileName, "rw");
        if(file.length()<HEAD_LENGTH) {
            file.writeInt(0);
        }
    }

    public int create(T obj) throws Exception {
        file.seek(0);
        int nextId = file.readInt()+1;
        file.seek(0);
        file.writeInt(nextId);
        obj.setId(nextId);
        byte[] b = obj.toByteArray();

        file.seek(file.length());
        file.writeByte(' ');
        file.writeShort(b.length);
        file.write(b); 
        return obj.getId();
    }
    
    public void close() throws Exception {
        file.close();
    }
}