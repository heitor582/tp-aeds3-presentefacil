package repository.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import repository.ExtensibleHashContract;
import shared.StringValidate;

public final class IdEmailIndexPair implements ExtensibleHashContract {
    private int id = -1;
    private String email = "";
    private short SIZE = 30;

    public IdEmailIndexPair() {
    };

    private IdEmailIndexPair(final int id, final String email) {
        this.id = id;
        this.email = email;
    }

    public static IdEmailIndexPair create(final int id, final String email) {
        StringValidate.requireNonBlank(email);
        return new IdEmailIndexPair(id, email);
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public short size() {
        return this.SIZE;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Email: %s | Size: %d",
                id, email, SIZE);
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);

        byte[] emailBytes = this.email.getBytes(StandardCharsets.UTF_8);
        byte[] emailBuffer = new byte[26];
        int length = Math.min(emailBytes.length, emailBuffer.length);
        System.arraycopy(emailBytes, 0, emailBuffer, 0, length);

        dos.write(emailBuffer);

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(final byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();

        byte[] emailBuffer = new byte[26];
        dis.readFully(emailBuffer);

        this.email = new String(emailBuffer, StandardCharsets.UTF_8).trim();
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
