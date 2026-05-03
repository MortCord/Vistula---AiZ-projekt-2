package Grafy;

import java.util.ArrayList;
import java.util.List;

public class Graf {


    private int v;
    private boolean skierowany;

    private List<Krawedz>[] lista;

    public Graf(int v, boolean skierowany) {
        this.v = v;
        this.skierowany = skierowany;

        lista = new ArrayList[v];

        for(int i=0;i<v;i++) {
            lista[i] = new ArrayList<>();
        }
    }

    public void dodajKrawedz(int from, int to, int waga) {

        lista[from].add(new Krawedz(to, waga));

        if(!skierowany) {
            lista[to].add(new Krawedz(from, waga));
        }
    }

    public int getV() {
        return v;
    }

    public List<Krawedz>[] getLista() {
        return lista;
    }

    public boolean isSkierowany() {
        return skierowany;
    }

}
