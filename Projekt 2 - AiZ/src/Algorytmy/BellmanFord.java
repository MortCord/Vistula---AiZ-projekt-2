package Algorytmy;

import Data.Rezultat;
import Grafy.Graf;
import Grafy.Krawedz;

import java.util.Arrays;

public class BellmanFord {

    public static Rezultat start(Graf graf, int start){

        int v = graf.getWierzcholki();

        int[] odleglosc = new int[v];
        int[] rodzic = new int[v];

        Arrays.fill(odleglosc, Integer.MAX_VALUE);
        Arrays.fill(rodzic, -1);

        odleglosc[start] = 0;

        long czasStartu = System.nanoTime();

        for(int i = 0; i < v - 1; i++){
            for(int od = 0; od < v; od++){
                for(Krawedz krawedz : graf.getListaSasiedztwa()[od]){
                    int to = krawedz.getTo();
                    int waga = krawedz.getWaga();

                    if(odleglosc[od] != Integer.MAX_VALUE && odleglosc[od] + waga < odleglosc[to]){
                        odleglosc[to] = odleglosc[od] + waga;
                        rodzic[to] = od;
                    }
                }
            }
        }

        long czasStopu = System.nanoTime();

        return new Rezultat(odleglosc, rodzic, czasStopu - czasStartu);

    }

}
