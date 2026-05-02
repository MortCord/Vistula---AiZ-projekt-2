package Algorytmy;

import Data.Rezultat;
import Grafy.Graf;
import Grafy.Krawedz;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {

    public static Rezultat start(Graf graf, int start){
        int v = graf.getWierzcholki();

        int[] odleglosc = new int[v];
        int[] rodzic = new int[v];

        Arrays.fill(odleglosc, Integer.MAX_VALUE);
        Arrays.fill(rodzic, -1);

        odleglosc[start] = 0;

        PriorityQueue<Wezel> queue = new PriorityQueue<>();
        queue.add(new Wezel(start, 0));

        long czasStartu = System.nanoTime();

        while(!queue.isEmpty()){
            Wezel aktualny = queue.poll();

            for(Krawedz krawedz : graf.getListaSasiedztwa()[aktualny.wierzcholek]){

                int nastepny = krawedz.getTo();
                int nowaOdleglosc = odleglosc[aktualny.wierzcholek] + krawedz.getWaga();

                if(nowaOdleglosc < odleglosc[nastepny]){
                    odleglosc[nastepny] = nowaOdleglosc;
                    rodzic[nastepny] = aktualny.wierzcholek;

                    queue.add(new Wezel(nastepny, nowaOdleglosc));
                }

            }
        }

        long czasStopu = System.nanoTime();

        return new Rezultat(odleglosc, rodzic, czasStopu - czasStartu);
    }

}
