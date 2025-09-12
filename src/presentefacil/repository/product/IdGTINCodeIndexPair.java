package repository.product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import repository.ExtensibleHashContract;
import shared.StringValidate;

public final class IdGTINCodeIndexPair implements ExtensibleHashContract {
    private int id = -1;
    private String gtin = "";
    private short SIZE = 17;

    public IdGTINCodeIndexPair(){};

    private IdGTINCodeIndexPair(final int id, final String gtin){
        this.id = id;
        this.gtin = StringValidate.requireMinSize(gtin, 13);
    }

    public static IdGTINCodeIndexPair create(final int id, final String gtin){
        return new IdGTINCodeIndexPair(id, gtin);
    }

    public String getGtin(){
        return this.gtin;
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
        dos.write(this.gtin.getBytes());
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(final byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] gtin = new byte[13];
        this.id = dis.readInt();
        dis.read(gtin);
        this.gtin = new String(gtin);
    }

    @Override
    public int hashCode() {
        return this.gtin.hashCode();
    }
}
