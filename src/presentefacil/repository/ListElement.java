package repository;

public class ListElement implements Comparable<ListElement>, Cloneable {

    private int id;
    private float frequencia;

    public ListElement(int i, float f) {
        this.id = i;
        this.frequencia = f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(float frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public String toString() {
        return "(" + this.id + ";" + this.frequencia + ")";
    }

    @Override
    public ListElement clone() {
        try {
            return (ListElement) super.clone();
        } catch (CloneNotSupportedException e) {
            // Tratamento de exceção se a clonagem falhar
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int compareTo(ListElement outro) {
        return Integer.compare(this.id, outro.id);
    }
}
