package Algorytmy;

import Data.Rezultat;
import Grafy.Graf;
import Grafy.Krawedz;

import java.util.Arrays;

public class FloydWarshall {

    static final int INF = 1_000_000_000;

    public static Rezultat start(Graf graf, int start) {

        int n = graf.getV();

        int[][] dist = new int[n][n];
        int[][] next = new int[n][n];

        for (int i = 0; i < n; i++) {

            Arrays.fill(dist[i], INF);
            Arrays.fill(next[i], -1);

            dist[i][i] = 0;
            next[i][i] = i;
        }

        for (int i = 0; i < n; i++) {

            for (Krawedz k : graf.getLista()[i]) {

                int to = k.getTo();

                dist[i][to] = k.getWaga();
                next[i][to] = to;
            }
        }

        long t1 = System.nanoTime();

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (dist[i][k] < INF &&
                            dist[k][j] < INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {

                        dist[i][j] = dist[i][k] + dist[k][j];

                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        long t2 = System.nanoTime();

        int[] result = new int[n];
        int[] parent = new int[n];

        Arrays.fill(parent, -1);

        for (int i = 0; i < n; i++) {

            result[i] = dist[start][i];

            if(i != start && next[start][i] != -1) {
                parent[i] = znajdzPoprzednika(start, i, next);
            }
        }

        return new Rezultat(result, parent, t2 - t1);
    }

    private static int znajdzPoprzednika(int start, int end, int[][] next){

        int current = start;
        int prev = -1;

        while(current != end){

            prev = current;
            current = next[current][end];

            if(current == -1)
                return -1;
        }

        return prev;
    }
}