package repository.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import repository.ExtensibleHashContract;

public class IdEmailIndexPair implements ExtensibleHashContract {
    private int id = -1;
    private String email = "";
    private short SIZE = 30;

    public IdEmailIndexPair(){};
    
    private IdEmailIndexPair(final int id, final String email){
        this.id = id;
        this.email = email;
    }

    public static IdEmailIndexPair create(final int id, final String email){
        return new IdEmailIndexPair(id, email);
    }

    public String getEmail(){
        return this.email;
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
        dos.write(this.email.getBytes());
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(final byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] code = new byte[26];
        this.id = dis.readInt();
        dis.read(code);
        this.email = new String(code);
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
