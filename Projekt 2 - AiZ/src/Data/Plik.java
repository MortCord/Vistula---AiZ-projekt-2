package Data;

import Grafy.Graf;

import java.io.File;
import java.util.Scanner;

public class Plik {
        /*
        FORMAT PLIKU:

        5 7
        0 1 4
        0 2 3
        1 3 8
        2 3 2
        3 4 1
        1 4 9
        2 4 7

        pierwszy wiersz:
        liczba_wierzcholkow liczba_krawedzi
    */

    public static Graf wczytaj(String path, boolean skierowany) throws Exception {

        Scanner sc = new Scanner(new File(path));

        int v = sc.nextInt();
        int e = sc.nextInt();

        Graf graf = new Graf(v, skierowany);

        for (int i = 0; i < e; i++) {

            int from = sc.nextInt();
            int to = sc.nextInt();
            int waga = sc.nextInt();

            graf.dodajKrawedz(from, to, waga);
        }

        sc.close();

        return graf;
    }
}
