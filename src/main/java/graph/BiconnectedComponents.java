package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class BiconnectedComponents {
  static class Edge {
    int to;
    Edge(int to) {
      this.to = to;
    }
  }

  private int[] order;
  private ArrayDeque<Integer> stack = new ArrayDeque<>();
  private ArrayDeque<Integer> path = new ArrayDeque<>();
  private boolean[] inStack;
  private int idx;
  private int k;
  private final ArrayList<ArrayList<Edge>> G;

  public ArrayList<int[]> bridges = new ArrayList<>();
  public int[] cmp;

  BiconnectedComponents(ArrayList<ArrayList<Edge>> g) {
    int n = g.size();
    this.G = g;
    idx = 0;
    k = 0;
    order = new int[n];
    Arrays.fill(order, -1);
    inStack = new boolean[n];
    cmp = new int[n];

    for (int v = 0; v < n; v++) {
      if (order[v] == -1) {
        dfs(v, n);

        // [v, N] が追加されてしまっているので削除
        bridges.remove(bridges.size() - 1);
      }
    }
  }

  private void dfs(int v, int p) {
    order[v] = idx++;
    stack.addLast(v);
    inStack[v] = true;
    path.addLast(v);
    for (Edge e : G.get(v)) {
      if (e.to == p) continue;

      if (order[e.to] == -1)
        dfs(e.to, v);
      else if (inStack[e.to])
        while (order[path.peekLast()] > order[e.to]) path.pollLast();
    }

    if (v == path.peekLast()) {
      bridges.add(new int[]{p, v});
      while (true) {
        int w = stack.pollLast();
        inStack[w] = false;
        cmp[w] = k;
        if (v == w) break;
      }
      path.pollLast();
      ++k;
    }
  }
}
