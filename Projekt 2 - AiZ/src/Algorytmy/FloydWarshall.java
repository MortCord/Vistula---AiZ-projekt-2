package Algorytmy;

import Data.Rezultat;
import Grafy.Graf;
import Grafy.Krawedz;

import java.util.Arrays;

public class FloydWarshall {

    public static Rezultat start(Graf graf, int start){

        int v = graf.getWierzcholki();
        int niesk = 999999;

        int[][] odleglosc = new int[v][v];

        for(int i = 0; i < v; i++){
            Arrays.fill(odleglosc[i], niesk);
            odleglosc[i][i] = 0;
        }

        for(int i = 0; i < v; i++ ){
            for(Krawedz krawedz : graf.getListaSasiedztwa()[i]){
                odleglosc[i][krawedz.getTo()] = krawedz.getWaga();
            }
        }

        long czasStartu = System.nanoTime();

        for(int k = 0; k < v; k++){
            for(int i = 0; i < v; i++){
                for(int j = 0; j < v; j++){

                    if(odleglosc[i][k] + odleglosc[k][j] < odleglosc[i][j]){
                        odleglosc[i][j] = odleglosc[i][k] + odleglosc[k][j];
                    }

                }
            }
        }

        long czasStopu = System.nanoTime();

        int[] rezultat = new int[v];

        for(int i = 0; i < v; i++){
            rezultat[i] = odleglosc[start][i];
        }

        return new Rezultat(rezultat, new int[v], czasStopu - czasStartu);

    }

}
