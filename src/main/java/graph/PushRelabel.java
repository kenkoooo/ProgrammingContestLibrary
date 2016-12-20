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
  ArrayList<ArrayList<Edge>> G = new ArrayList<>();
  long[] excess;
  int[] dist, count;
  boolean[] active;
  ArrayDeque<Integer> Q = new ArrayDeque<>();

  PushRelabel(int N) {
    this.N = N;
    for (int i = 0; i < N; i++) G.add(new ArrayList<>());
    excess = new long[N];
    dist = new int[N];
    active = new boolean[N];
    count = new int[N * 2];
  }

  void push(Edge e) {
    int amt = (int) Math.min(excess[e.from], e.cap - e.flow);
    if (dist[e.from] <= dist[e.to] || amt == 0)
      return;
    e.flow += amt;
    G.get(e.to).get(e.rev).flow -= amt;
    excess[e.to] += amt;
    excess[e.from] -= amt;
    enqueue(e.to);
  }

  void addEdge(int from, int to, int cap) {
    G.get(from).add(new Edge(from, to, cap, 0, G.get(to).size()));
    G.get(to).add(new Edge(to, from, 0, 0, G.get(from).size() - 1));
  }

  void discharge(int v) {

    for (Edge e : G.get(v)) {
      if (excess[v] <= 0) break;
      push(e);
    }
    if (excess[v] > 0) {
      if (count[dist[v]] == 1)
        gap(dist[v]);
      else
        relabel(v);
    }
  }

  void enqueue(int v) {
    if (!active[v] && excess[v] > 0) {
      active[v] = true;
      Q.push(v);
    }
  }

  void gap(int k) {
    for (int v = 0; v < N; v++) {
      if (dist[v] < k)
        continue;
      count[dist[v]]--;
      dist[v] = Math.max(dist[v], N + 1);
      count[dist[v]]++;
      enqueue(v);
    }
  }

  void relabel(int v) {
    count[dist[v]]--;
    dist[v] = 2 * N;
    for (Edge e : G.get(v))
      if (e.cap - e.flow > 0)
        dist[v] = Math.min(dist[v], dist[e.to] + 1);
    count[dist[v]]++;
    enqueue(v);
  }

  long maxFlow(int s, int t) {
    count[0] = N - 1;
    count[N] = 1;
    dist[s] = N;
    active[s] = active[t] = true;
    for (Edge e : G.get(s)) {
      excess[s] += e.cap;
      push(e);
    }

    while (!Q.isEmpty()) {
      int v = Q.poll();
      active[v] = false;
      discharge(v);
    }

    long flow = 0;
    for (Edge e : G.get(s)) flow += e.flow;
    return flow;
  }

}
