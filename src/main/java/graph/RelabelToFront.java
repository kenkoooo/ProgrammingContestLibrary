package graph;

import java.util.*;

public class RelabelToFront {
  class Edge {
    int from, to, rev;
    long flow, cap;
    Edge(int from, int to, long cap, int rev) {
      this.from = from;
      this.to = to;
      this.cap = cap;
      this.rev = rev;
      this.flow = 0;
    }
  }

  int N;
  ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
  private long[] excess;
  private int[] height;
  private int[] seen;

  PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
    @Override
    public int compare(int[] o1, int[] o2) {
      return -Integer.compare(o1[0], o2[0]);
    }
  });

  RelabelToFront(int N) {
    this.N = N;
    for (int i = 0; i < N; i++) graph.add(new ArrayList<>());
    excess = new long[N];
    height = new int[N];
    seen = new int[N];
  }

  void addEdge(int from, int to, long cap) {
    graph.get(from).add(new Edge(from, to, cap, graph.get(to).size()));
    graph.get(to).add(new Edge(to, from, 0, graph.get(from).size() - 1));
  }

  private void push(Edge e) {
    int u = e.from;
    int v = e.to;

    long send = Math.min(excess[u], e.cap - e.flow);
    e.flow += send;
    graph.get(v).get(e.rev).flow -= send;
    excess[u] -= send;
    excess[v] += send;

    if (excess[v] > 0) queue.add(new int[]{height[v], v});
    if (excess[u] > 0) queue.add(new int[]{height[u], u});
  }

  private void relabel(int u) {
    int minHeight = N * 2;
    for (Edge e : graph.get(u))
      if (e.cap - e.flow > 0)
        minHeight = Math.min(minHeight, height[e.to]);
    height[u] = minHeight + 1;

    queue.add(new int[]{height[u], u});
  }

  private void discharge(int u) {
    while (excess[u] > 0) {
      if (seen[u] < graph.get(u).size()) {
        Edge e = graph.get(u).get(seen[u]);
        if (e.cap - e.flow > 0 && height[u] == height[e.to] + 1)
          push(e);
        else
          seen[u] += 1;
      } else {
        relabel(u);
        seen[u] = 0;
      }
    }
  }

  long maxFlow(int source, int sink) {
    height[source] = N;
    for (Edge e : graph.get(source)) {
      excess[source] += e.cap;
      push(e);
    }

    while (!queue.isEmpty()) {
      int[] q = queue.poll();
      int u = q[1];
      if (excess[u] == 0) continue;
      if (u == source || u == sink) continue;
      discharge(u);
    }

    long maxFlow = 0;
    for (Edge e : graph.get(source)) maxFlow += e.flow;

    return maxFlow;
  }
}
