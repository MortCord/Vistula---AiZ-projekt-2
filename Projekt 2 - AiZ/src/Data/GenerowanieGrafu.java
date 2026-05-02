package Data;

import Grafy.Graf;

import java.util.Random;

public class GenerowanieGrafu {

    public static Graf generujGraf(int wierzcholki, int krawedzi){

        Graf graf = new Graf(wierzcholki);
        Random random = new Random();

        int dodaneKrawedzi = 0;

        while(dodaneKrawedzi < krawedzi){
            int od = random.nextInt(wierzcholki);
            int to = random.nextInt(wierzcholki);

            if(od != to){
                int waga = random.nextInt(20) + 1;

                graf.dodajKrawedz(od, to, waga);
                dodaneKrawedzi++;
            }
        }

        return graf;

    }

}
