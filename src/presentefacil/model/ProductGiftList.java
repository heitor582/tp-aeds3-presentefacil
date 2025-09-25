package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ProductGiftList extends Entity {
    private int productId = -1;
    private int giftListId = -1;
    private int quantity = 0;
    private String description;

    public void changeQuantity(int q) {
        if(q<0) return;
        this.quantity = q;
    }

    public void changeDescription(final String description){
        this.description = description;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getDescription(){
        return this.description;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getGiftListId() {
        return this.giftListId;
    }

    public ProductGiftList() {
    }

    private ProductGiftList(
            final int id,
            final int productId,
            final int giftListId,
            final int quantity,
            final String description,
            final boolean status) {
        super(id, status);
        this.productId = productId;
        this.giftListId = giftListId;
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }
        this.quantity = quantity;
        this.description = description;
    }

    public static ProductGiftList create(
            final int productId,
            final int giftListId,
            final int quantity,
            final String description) {
        return new ProductGiftList(-1, productId, giftListId, quantity, description, true);
    }

    public static ProductGiftList create(
            final int productId,
            final int giftListId) {
        return new ProductGiftList(-1, productId, giftListId, 0, "", true);
    }

    @Override
    public void fromByteArray(byte[] array) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.productId = dis.readInt();
        this.giftListId = dis.readInt();
        this.quantity = dis.readInt();
        this.description = dis.readUTF();
        this.isActive = dis.readBoolean();
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt(this.productId);
        dos.writeInt(this.giftListId);
        dos.writeInt(this.quantity);
        dos.writeUTF(this.description);
        dos.writeBoolean(this.isActive);
        return baos.toByteArray();
    }

}
