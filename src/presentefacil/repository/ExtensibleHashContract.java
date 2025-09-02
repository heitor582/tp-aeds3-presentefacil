package repository;

import java.io.IOException;

public interface ExtensibleHashContract {
  public int hashCode(); // the Object already have this method so dont throw an error for not implement this

  public short size();

  public byte[] toByteArray() throws IOException;

  public void fromByteArray(byte[] ba) throws IOException;
}
