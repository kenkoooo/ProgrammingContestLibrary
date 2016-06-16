package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Dinitz {
  class Edge {
    int to, cap, rev;
    Edge(int to, int cap, int rev) {
      this.to = to;
      this.cap = cap;
      this.rev = rev;
    }
  }

  private ArrayDeque<Integer> deque = new ArrayDeque<>();
  private ArrayList<ArrayList<Edge>> g;
  private int[] level;
  private int[] iter;

  Dinitz(int V) {
    g = new ArrayList<>(V);
    for (int i = 0; i < V; i++) g.add(new ArrayList<Edge>());
    level = new int[V];
    iter = new int[V];
  }

  void addEdge(int from, int to, int cap) {
    g.get(from).add(new Edge(to, cap, g.get(to).size()));
    g.get(to).add(new Edge(from, 0, g.get(from).size() - 1));
  }

  private int dfs(int v, int t, int f) {
    if (v == t) return f;
    for (; iter[v] < g.get(v).size(); iter[v]++) {
      Edge e = g.get(v).get(iter[v]);
      if (e.cap > 0 && level[v] < level[e.to]) {
        int d = dfs(e.to, t, Math.min(f, e.cap));
        if (d > 0) {
          e.cap -= d;
          g.get(e.to).get(e.rev).cap += d;
          return d;
        }
      }
    }
    return 0;
  }

  private void bfs(int s) {
    Arrays.fill(level, -1);
    level[s] = 0;
    deque.add(s);
    while (!deque.isEmpty()) {
      int v = deque.poll();
      for (Edge e : g.get(v)) {
        if (e.cap > 0 && level[e.to] < 0) {
          level[e.to] = level[v] + 1;
          deque.add(e.to);
        }
      }
    }
  }

  int maxFlow(int s, int t) {
    int flow = 0;
    for (; ; ) {
      bfs(s);
      if (level[t] < 0) return flow;
      Arrays.fill(iter, 0);
      int f;
      while ((f = dfs(s, t, Integer.MAX_VALUE)) > 0) {
        flow += f;
      }
    }
  }
}