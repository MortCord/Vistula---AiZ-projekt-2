package Algorytmy;

import Data.Rezultat;
import Grafy.Graf;
import Grafy.Krawedz;

import java.util.Arrays;

public class BellmanFord {

    public static Rezultat start(Graf graf, int start) {

        int n = graf.getV();

        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[start] = 0;

        long t1 = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {

            for (int u = 0; u < n; u++) {

                for (Krawedz k : graf.getLista()[u]) {

                    int v = k.getTo();
                    int w = k.getWaga();

                    if (dist[u] != Integer.MAX_VALUE &&
                            dist[u] + w < dist[v]) {

                        dist[v] = dist[u] + w;
                        parent[v] = u;
                    }
                }
            }
        }

        // negative cycle check
        for (int u = 0; u < n; u++) {
            for (Krawedz k : graf.getLista()[u]) {

                int v = k.getTo();
                int w = k.getWaga();

                if (dist[u] != Integer.MAX_VALUE &&
                        dist[u] + w < dist[v]) {

                    System.out.println("UWAGA: Ujemny cykl!");
                }
            }
        }

        long t2 = System.nanoTime();

        return new Rezultat(dist, parent, t2 - t1);
    }

}
