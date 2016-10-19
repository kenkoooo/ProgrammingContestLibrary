package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class BiconnectedComponents {

  private int[] order;
  private int[] stack;
  private int[] path;
  private int s = 0, t = 0;
  private BitSet inStack;
  private int idx;
  private int k;
  private final ArrayList<ArrayList<Integer>> G;

  public ArrayList<int[]> bridges = new ArrayList<>();
  public int[] cmp;

  BiconnectedComponents(ArrayList<ArrayList<Integer>> g) {
    int n = g.size();
    this.G = g;
    idx = 0;
    k = 0;
    order = new int[n];
    Arrays.fill(order, -1);
    inStack = new BitSet(n);
    cmp = new int[n];
    stack = new int[n];
    path = new int[n];

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
    stack[s++] = v;
    inStack.set(v);
    path[t++] = v;
    for (Integer to : G.get(v)) {
      if (to == p) continue;

      if (order[to] == -1)
        dfs(to, v);
      else if (inStack.get(to))
        while (order[path[t - 1]] > order[to]) t--;
    }

    if (v == path[t - 1]) {
      bridges.add(new int[]{p, v});
      while (true) {
        int w = stack[--s];
        inStack.set(w, false);
        cmp[w] = k;
        if (v == w) break;
      }
      t--;
      k++;
    }
  }
}
