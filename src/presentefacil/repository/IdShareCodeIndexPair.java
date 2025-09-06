package repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IdShareCodeIndexPair implements ExtensibleHashContract {
    private int id = -1;
    private String shareCode = "";
    private short SIZE = 30;

    public IdShareCodeIndexPair(){};

    private IdShareCodeIndexPair(final int id, final String shareCode){
        this.id = id;
        this.shareCode = shareCode;
    }

    public static IdShareCodeIndexPair create(final int id, final String shareCode){
        return new IdShareCodeIndexPair(id, shareCode);
    }

    public String getShareCode(){
        return this.shareCode;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public short size(){
        return this.SIZE;
    }

    @Override
    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.shareCode);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(final byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.shareCode = dis.readUTF();
    }

    @Override
    public int hashCode() {
        return this.shareCode.hashCode();
    }
}
