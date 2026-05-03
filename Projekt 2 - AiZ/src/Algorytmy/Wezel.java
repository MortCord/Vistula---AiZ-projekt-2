package Algorytmy;

public class Wezel implements Comparable<Wezel> {

    int v, dist;

    Wezel(int v, int dist) {
        this.v = v;
        this.dist = dist;
    }

    public int compareTo(Wezel o) {
        return Integer.compare(this.dist, o.dist);
    }

}
