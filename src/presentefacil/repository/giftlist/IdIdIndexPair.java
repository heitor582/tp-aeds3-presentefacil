package repository.giftlist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import repository.BPlusTreeContract;

public class IdIdIndexPair implements BPlusTreeContract<IdIdIndexPair> {
  private int userId;
  private int listId;
  private short TAMANHO = 8;

  public int getUserId() {
   return this.userId; 
  }
  public int getListId() {
   return this.listId; 
  }

  public IdIdIndexPair() {
    this(-1, -1);
  }

  public IdIdIndexPair(int id) {
    this(id, -1);
  }

  public IdIdIndexPair(int id, int listId) {
    try {
      this.userId = id;
      this.listId = listId;
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public IdIdIndexPair clone() {
    return new IdIdIndexPair(this.userId, this.listId);
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(IdIdIndexPair a) {
    if (this.userId != a.userId)
      return this.userId - a.userId;
    else
      return this.listId == -1 ? 0 : this.listId - a.listId;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.userId);
    dos.writeInt(this.listId);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.userId = dis.readInt();
    this.listId = dis.readInt();
  }
}