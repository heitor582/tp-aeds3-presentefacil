package model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import shared.NanoID;


public class GiftList extends Entity {
    private String name = "";
    private String detailedDescription = "";
    private LocalDate createdAt = LocalDate.now();
    private Optional<LocalDate> expirationDate = null;
    private String shareCode = "";

    public GiftList(){}
    private GiftList(
        final int id,
        final String name,
        final String detailedDescription,
        final LocalDate createdAt,
        final Optional<LocalDate> expirationDate,
        final String shareCode
    ) {
        super(id);
        this.name = name;
        this.detailedDescription = detailedDescription;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.shareCode = shareCode;
    }

    public static GiftList from(
        final String name,
        final String detailedDescription,
        final LocalDate createdAt,
        final Optional<LocalDate> expirationDate,
        final String shareCode
    ) {
        return new GiftList(-1, name, detailedDescription, createdAt, expirationDate, shareCode);
    }
     public static GiftList create(
        final String name,
        final String detailedDescription,
        final LocalDate createdAt,
        final Optional<LocalDate> expirationDate
    ) {
        return new GiftList(-1, name, detailedDescription, createdAt, expirationDate, NanoID.nanoid());
    }
    @Override
    public void fromByteArray(byte[] array) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);
        byte[] shareCode = new byte[10];
        
        this.id = dis.readInt();
        this.name = dis.readUTF();
        this.detailedDescription = dis.readUTF();
        this.createdAt = LocalDate.ofEpochDay(dis.readInt());
        final int optionalExpDate = dis.readInt();
        this.expirationDate = Optional.ofNullable(optionalExpDate == -1 ? null : LocalDate.ofEpochDay(optionalExpDate));
        dis.read(shareCode);
        this.shareCode = new String(shareCode);
    }
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        

        dos.writeInt(this.id);
        dos.writeUTF(this.name);
        dos.writeUTF(this.detailedDescription);
        dos.writeInt((int) this.createdAt.toEpochDay());
        dos.writeInt(this.expirationDate.map(expDate -> (int)expDate.toEpochDay()).orElse(-1));
        dos.write(this.shareCode.getBytes());

        return baos.toByteArray();
    }
}
