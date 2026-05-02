package Grafy;

import java.util.ArrayList;
import java.util.List;

public class Graf {

    private int wierzcholki;
    private List<Krawedz>[] listaSasiedztwa;

    public Graf(int wierzcholki){

        this.wierzcholki = wierzcholki;

        listaSasiedztwa = new ArrayList[wierzcholki];

        for(int i = 0; i < wierzcholki; i++){
            listaSasiedztwa[i] = new ArrayList<>();
        }

    }

    public void dodajKrawedz(int od, int to, int waga){
        listaSasiedztwa[od].add(new Krawedz(to, waga));
    }

    public int getWierzcholki(){
        return wierzcholki;
    }

    public List<Krawedz>[] getListaSasiedztwa(){
        return listaSasiedztwa;
    }

}
