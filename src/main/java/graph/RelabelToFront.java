package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

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
    long send = Math.min(excess[e.from], e.cap - e.flow);
    e.flow += send;
    graph.get(e.to).get(e.rev).flow -= send;
    excess[e.from] -= send;
    excess[e.to] += send;
  }

  private void relabel(int u) {
    int minHeight = N * 2;
    for (Edge e : graph.get(u))
      if (e.cap - e.flow > 0)
        minHeight = Math.min(minHeight, height[e.to]);
    height[u] = minHeight + 1;
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
    LinkedList<Integer> list = new LinkedList<>();
    for (int i = 0; i < N; i++) {
      if (i != source && i != sink) list.add(i);
    }
    height[source] = N;
    for (Edge e : graph.get(source)) {
      excess[source] += e.cap;
      push(e);
    }

    Iterator<Integer> it = list.iterator();
    while (it.hasNext()) {
      int u = it.next();
      int oldHeight = height[u];
      discharge(u);
      if (height[u] > oldHeight) {
        it.remove();
        list.add(0, u);
        it = list.iterator();
      }
    }

    long maxFlow = 0;
    for (Edge e : graph.get(source)) maxFlow += e.flow;

    return maxFlow;
  }
}
