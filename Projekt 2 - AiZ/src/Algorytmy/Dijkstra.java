package Algorytmy;

import Data.Rezultat;
import Grafy.Graf;
import Grafy.Krawedz;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {


    public static Rezultat start(Graf graf, int start) {

        int n = graf.getV();

        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[start] = 0;

        PriorityQueue<Wezel> pq = new PriorityQueue<>();
        pq.add(new Wezel(start, 0));

        long t1 = System.nanoTime();

        while (!pq.isEmpty()) {

            Wezel cur = pq.poll();

            if (cur.dist > dist[cur.v])
                continue;

            for (Krawedz k : graf.getLista()[cur.v]) {

                int next = k.getTo();
                int nd = dist[cur.v] + k.getWaga();

                if (nd < dist[next]) {
                    dist[next] = nd;
                    parent[next] = cur.v;
                    pq.add(new Wezel(next, nd));
                }
            }
        }
        long t2 = System.nanoTime();
        return new Rezultat(dist, parent, t2 - t1);
    }

}
