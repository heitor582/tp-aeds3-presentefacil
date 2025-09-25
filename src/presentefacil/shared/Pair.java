package shared;

public final class Pair<T,R> {
    private T first;
    private R second;

    public Pair(final T first, final R second){
        this.first = first;
        this.second = second;
    }

    public T getFirst(){
        return this.first;
    }

    public R getSecond() {
        return this.second;
    }
}
