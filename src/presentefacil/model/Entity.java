package model;
import java.io.IOException;

public abstract class Entity {
    protected int id = -1;
    public Entity() {}
    public Entity(final int id) {
        this.id = id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public abstract void fromByteArray(byte[] array) throws IOException;
    public abstract byte[] toByteArray() throws IOException;
}