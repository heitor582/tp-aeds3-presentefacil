package model;
import java.io.IOException;

public abstract class Entity{
    protected int id = -1;
    protected boolean isActive = true;
    public Entity() {}
    public Entity(final int id) {
        this.id = id;
    }
    public Entity(final int id, final boolean status) {
        this.id = id;
        this.isActive = status;
    }
    public void setId(final int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public boolean isActive() {
        return this.isActive;
    }
    public void changeStatus(final boolean isActive){
        this.isActive = isActive;
    }
    
    public abstract void fromByteArray(final byte[] array) throws IOException;
    public abstract byte[] toByteArray() throws IOException;
}