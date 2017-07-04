package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MinimumCostFlow {

  class Edge {

    int to, rev;
    long cap, cost;

    Edge(int to, long cap, long cost, int rev) {
      this.to = to;
      this.cap = cap;
      this.cost = cost;
      this.rev = rev;
    }
  }

  private static final long INF = Long.MAX_VALUE / 2;
  private ArrayList<ArrayList<Edge>> G;
  private int V;
  private long[] potential;
  private long[] dist;
  private int[] prevV, prevE;

  MinimumCostFlow(int V) {
    this.V = V;
    G = new ArrayList<>(V);
    for (int i = 0; i < V; i++) {
      G.add(new ArrayList<Edge>());
    }
    potential = new long[V];
    dist = new long[V];
    prevE = new int[V];
    prevV = new int[V];
  }

  void addEdge(int from, int to, long cap, long cost) {
    G.get(from).add(new Edge(to, cap, cost, G.get(to).size()));
    G.get(to).add(new Edge(from, 0, -cost, G.get(from).size() - 1));
  }

  long run(int s, int t, long flow) {
    long cost = 0;
    Arrays.fill(potential, 0);
    while (flow > 0) {
      PriorityQueue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
      Arrays.fill(dist, INF);
      dist[s] = 0;
      queue.add(new long[]{0, s});
      while (!queue.isEmpty()) {
        long[] p = queue.poll();
        int v = (int) p[1];
        if (dist[v] < p[0]) {
          continue;
        }
        for (int i = 0; i < G.get(v).size(); i++) {
          Edge e = G.get(v).get(i);
          int u = e.to;
          if (e.cap > 0 && dist[u] > dist[v] + e.cost + potential[v] - potential[u]) {
            dist[u] = dist[v] + e.cost + potential[v] - potential[u];
            prevV[u] = v;
            prevE[u] = i;
            queue.add(new long[]{dist[u], u});
          }
        }
      }
      if (dist[t] == INF) {
        return -1;
      }
      for (int v = 0; v < V; v++) {
        potential[v] += dist[v];
      }

      long d = flow;
      for (int v = t; v != s; v = prevV[v]) {
        d = Math.min(d, G.get(prevV[v]).get(prevE[v]).cap);
      }
      flow -= d;
      cost += d * potential[t];
      for (int v = t; v != s; v = prevV[v]) {
        Edge e = G.get(prevV[v]).get(prevE[v]);
        e.cap -= d;
        G.get(v).get(e.rev).cap += d;
      }
    }
    return cost;
  }
}