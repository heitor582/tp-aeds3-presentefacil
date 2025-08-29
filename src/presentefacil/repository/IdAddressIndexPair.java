package repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IdAddressIndexPair implements ExtensibleHashContract {
    private int id = -1;
    private long address = -1;
    private final short SIZE = 12;

    public IdAddressIndexPair() {}
    private IdAddressIndexPair(final int id, final long address) {
        this.id = id;
        this.address = address;
    }
    public static IdAddressIndexPair create(final int id, final long address) {
        return new IdAddressIndexPair(id, address);
    }
    public long getAddress(){
        return this.address;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public short size() {
        return this.SIZE;
    }
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeLong(this.address);
        return baos.toByteArray();
    }
    @Override
    public void fromByteArray(final byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.address = dis.readLong();
    }
}
