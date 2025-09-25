package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import shared.NanoID;
import shared.StringValidate;

public final class GiftList extends Entity {
    private String name = "";
    private String detailedDescription = "";
    private LocalDate createdAt = LocalDate.now();
    private Optional<LocalDate> expirationDate = null;
    private String shareCode = "";
    private int userId = -1;

    public GiftList() {
    }

    private GiftList(
            final int id,
            final String name,
            final String detailedDescription,
            final LocalDate createdAt,
            final Optional<LocalDate> expirationDate,
            final String shareCode,
            final int userId,
            final boolean status) {
        super(id, status);
        this.name = StringValidate.requireNonBlank(name);
        this.detailedDescription = detailedDescription;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.shareCode = StringValidate.requireNonBlank(shareCode);
        this.userId = userId;
    }

    public int getUserId() {
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

    public String getExpirationDateFormated(Object... pre) {
        String prefix = (pre != null && pre.length > 0)
                ? String.join(" ", Arrays.stream(pre)
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .toArray(String[]::new)) + " "
                : "";
        return this.expirationDate
                .map(v -> prefix + v.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .orElse(prefix + "sem data");
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
            final int id,
            final boolean status) {
        return new GiftList(id, name, detailedDescription, createdAt, expirationDate, shareCode, userId, status);
    }

    public static GiftList create(
            final String name,
            final String detailedDescription,
            final Optional<LocalDate> expirationDate,
            final int userId) {
        return new GiftList(-1, name, detailedDescription, LocalDate.now(), expirationDate, NanoID.nanoid(), userId,
                true);
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
        this.userId = dis.readInt();
        this.isActive = dis.readBoolean();
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.name);
        dos.writeUTF(this.detailedDescription);
        dos.writeInt((int) this.createdAt.toEpochDay());
        dos.writeInt(this.expirationDate.map(expDate -> (int) expDate.toEpochDay()).orElse(-1));
        dos.write(this.shareCode.getBytes());
        dos.writeInt(userId);
        dos.writeBoolean(this.isActive);
        return baos.toByteArray();
    }
}
