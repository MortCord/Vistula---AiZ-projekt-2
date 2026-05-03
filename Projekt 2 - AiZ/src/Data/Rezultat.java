package Data;

public class Rezultat {

    private int[] dist;
    private int[] parent;
    private long czas;

    public Rezultat(int[] dist, int[] parent, long czas) {
        this.dist = dist;
        this.parent = parent;
        this.czas = czas;
    }

    public int[] getDist() {
        return dist;
    }

    public int[] getParent() {
        return parent;
    }

    public long getCzas() {
        return czas;
    }

}
