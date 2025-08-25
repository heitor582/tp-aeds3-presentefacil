package repository;

import java.io.IOException;

public interface ExtensibleHashContract {
    public int hashCode();

  public short size();

  public byte[] toByteArray() throws IOException;

  public void fromByteArray(byte[] ba) throws IOException;
}
