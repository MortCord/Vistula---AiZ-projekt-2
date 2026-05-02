package Algorytmy;

public class Wezel implements Comparable<Wezel> {

    int wierzcholek, odleglosc;

    public Wezel(int wierzcholek, int odleglosc){
        this.wierzcholek = wierzcholek;
        this.odleglosc = odleglosc;
    }

    @Override
    public int compareTo(Wezel inny){
        return Integer.compare(this.odleglosc, inny.odleglosc);
    }

}
