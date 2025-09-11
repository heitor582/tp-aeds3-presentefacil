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
    private int userId = -1;;

    public GiftList(){}
    private GiftList(
        final int id,
        final String name,
        final String detailedDescription,
        final LocalDate createdAt,
        final Optional<LocalDate> expirationDate,
        final String shareCode,
        final int userId
    ) {
        super(id);
        this.name = name;
        this.detailedDescription = detailedDescription;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.shareCode = shareCode;
        this.userId = userId;
    }
    
    public int getUserId(){
        return this.userId;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.detailedDescription;
    }
    public LocalDate getCreatedAt() {
        return this.createdAt;
    }
    public Optional<LocalDate> getExpirationDate() {
        return this.expirationDate;
    }
    public String getCode() {
        return this.shareCode;
    }
    public static GiftList from(
        final String name,
        final String detailedDescription,
        final LocalDate createdAt,
        final Optional<LocalDate> expirationDate,
        final String shareCode,
        final int userId,
        final int id
    ) {
        return new GiftList(id, name, detailedDescription, createdAt, expirationDate, shareCode, userId);
    }
    public static GiftList create(
        final String name,
        final String detailedDescription,
        final Optional<LocalDate> expirationDate,
        final int userId
    ) {
        return new GiftList(-1, name, detailedDescription, LocalDate.now(), expirationDate, NanoID.nanoid(), userId);
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
