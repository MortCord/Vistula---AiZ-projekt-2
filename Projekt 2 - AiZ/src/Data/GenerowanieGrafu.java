package Data;

import Grafy.Graf;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerowanieGrafu {

    public static Graf generuj(int v, int e, boolean skierowany) {

        Graf graf = new Graf(v, skierowany);

        Random rand = new Random();

        Set<String> used = new HashSet<>();

        while (used.size() < e) {

            int from = rand.nextInt(v);
            int to = rand.nextInt(v);

            if (from == to)
                continue;

            String key = from + "-" + to;

            if (used.contains(key))
                continue;

            used.add(key);

            int waga = rand.nextInt(20) + 1;

            graf.dodajKrawedz(from, to, waga);
        }

        return graf;
    }

}
