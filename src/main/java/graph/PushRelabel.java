package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class PushRelabel {
  class Edge {
    int from, to, cap, flow, rev;
    Edge(int from, int to, int cap, int flow, int rev) {
      this.from = from;
      this.to = to;
      this.cap = cap;
      this.flow = flow;
      this.rev = rev;
    }
  }

  int N;
  private ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
  private long[] excess;
  private int[] height, count;
  private boolean[] queued;
  private ArrayDeque<Integer> queue = new ArrayDeque<>();

  PushRelabel(int N) {
    this.N = N;
    for (int i = 0; i < N; i++) graph.add(new ArrayList<>());
    excess = new long[N];
    height = new int[N];
    queued = new boolean[N];
    count = new int[N * 2];
  }

  private void push(Edge e) {
    long pushableFlow = Math.min(excess[e.from], e.cap - e.flow);
    if (height[e.from] <= height[e.to] || pushableFlow == 0) return;

    e.flow += pushableFlow;
    graph.get(e.to).get(e.rev).flow -= pushableFlow;
    excess[e.to] += pushableFlow;
    excess[e.from] -= pushableFlow;
    enqueue(e.to);
  }

  void addEdge(int from, int to, int cap) {
    graph.get(from).add(new Edge(from, to, cap, 0, graph.get(to).size()));
    graph.get(to).add(new Edge(to, from, 0, 0, graph.get(from).size() - 1));
  }

  private void discharge(int u) {
    for (Edge e : graph.get(u)) {
      if (excess[u] == 0) break;
      push(e);
    }

    if (excess[u] > 0) {
      if (count[height[u]] == 1)
        gap(height[u]);
      else
        relabel(u);
    }
  }

  private void enqueue(int v) {
    if (!queued[v] && excess[v] > 0) {
      queued[v] = true;
      queue.push(v);
    }
  }

  private void gap(int h) {
    for (int v = 0; v < N; v++) {
      if (height[v] < h) continue;
      count[height[v]]--;
      height[v] = Math.max(height[v], N + 1);
      count[height[v]]++;
      enqueue(v);
    }
  }

  void relabel(int v) {
    count[height[v]]--;
    height[v] = 2 * N;
    for (Edge e : graph.get(v))
      if (e.cap - e.flow > 0)
        height[v] = Math.min(height[v], height[e.to] + 1);
    count[height[v]]++;
    enqueue(v);
  }

  long maxFlow(int s, int t) {
    count[0] = N - 1;
    count[N] = 1;
    height[s] = N;
    queued[s] = queued[t] = true;
    for (Edge e : graph.get(s)) {
      excess[s] += e.cap;
      push(e);
    }

    while (!queue.isEmpty()) {
      int v = queue.poll();
      queued[v] = false;
      discharge(v);
    }

    long flow = 0;
    for (Edge e : graph.get(s)) flow += e.flow;
    return flow;
  }

}
