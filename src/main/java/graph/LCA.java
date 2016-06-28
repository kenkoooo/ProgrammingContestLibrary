package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

class LCA {
  ArrayList<ArrayList<Integer>> G;
  int[][] parent;
  int[] depth;
  int root, logV;

  void build(int root) {
    Arrays.fill(depth, -1);
    ArrayDeque<Integer> stack = new ArrayDeque<>();
    stack.addFirst(root);
    parent[0][root] = -1;
    depth[root] = 0;
    while (!stack.isEmpty()) {
      int v = stack.peekFirst();
      for (int u : G.get(v)) {
        if (depth[u] >= 0) continue;
        parent[0][u] = v;
        depth[u] = depth[v] + 1;
        stack.addFirst(u);
      }
      if (stack.peekFirst() == v) stack.pollFirst();
    }
  }

  LCA(final ArrayList<ArrayList<Integer>> adj) {
    int V = adj.size();
    root = 0;
    G = adj;
    depth = new int[V];

    logV = 1;
    for (int i = 1; i <= V; ) {
      i *= 2;
      logV++;
    }
    parent = new int[logV][V];

    build(root);

    for (int k = 0; k + 1 < logV; ++k)
      for (int v = 0; v < V; ++v)
        if (parent[k][v] < 0) {
          parent[k + 1][v] = -1;
        } else {
          parent[k + 1][v] = parent[k][parent[k][v]];
        }
  }

  int getLCA(int u, int v) {
    if (depth[u] > depth[v]) {
      int tu = u;
      u = v;
      v = tu;
    }
    for (int k = 0; k < logV; ++k) if (((depth[v] - depth[u]) >> k & 1) != 0) v = parent[k][v];
    if (u == v) return u;
    for (int k = logV - 1; k >= 0; --k)
      if (parent[k][u] != parent[k][v]) {
        u = parent[k][u];
        v = parent[k][v];
      }
    return parent[0][u];
  }

  int getLength(int u, int v) {
    int lca = getLCA(u, v);
    return depth[u] + depth[v] - depth[lca] * 2;
  }
}