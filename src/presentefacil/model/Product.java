package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import shared.StringValidate;

public final class Product extends Entity {
    private String name;
    private String description;
    private String gtin;

    public String getGtin() {
        return this.gtin;
    }

    public Product() {
    }

    private Product(
            final int id,
            final String name,
            final String description,
            final boolean status,
            final String gtin) {
        super(id, status);
        this.name = StringValidate.requireNonBlank(name);
        this.description = description;
        this.gtin = StringValidate.requireMinSize(gtin, 13);
    }

    public Product create(
            final String name,
            final String description,
            final String gtin) {
        return new Product(-1, name, description, true, gtin);
    }
    
    public String getName() {
        return this.name;
    }

    @Override
    public void fromByteArray(byte[] array) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);
        byte[] gtin = new byte[13];

        this.id = dis.readInt();
        this.name = dis.readUTF();
        this.description = dis.readUTF();
        dis.read(gtin);
        this.gtin = new String(gtin);
        this.isActive = dis.readBoolean();
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.name);
        dos.writeUTF(this.description);
        dos.write(gtin.getBytes());
        dos.writeBoolean(isActive);
        return baos.toByteArray();
    }

}
