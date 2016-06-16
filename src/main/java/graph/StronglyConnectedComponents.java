package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class StronglyConnectedComponents {
  public static int[] decompose(ArrayList<Integer>[] G) {
    ArrayList<Integer> vs = new ArrayList<Integer>();
    int V = G.length;
    int[] cmp = new int[V];

    ArrayList<Integer>[] rG = new ArrayList[V];
    for (int i = 0; i < V; i++) rG[i] = new ArrayList<Integer>();
    for (int i = 0; i < V; i++) for (int v : G[i]) rG[v].add(i);
    boolean[] used = new boolean[V];

    ArrayDeque<Integer> stack = new ArrayDeque<>();
    boolean[] added = new boolean[V];
    for (int i = 0; i < V; i++)
      if (!used[i]) {
        stack.addFirst(i);
        while (!stack.isEmpty()) {
          int v = stack.peekFirst();
          used[v] = true;
          boolean pushed = false;
          for (int j = G[v].size() - 1; j >= 0; j--) {
            int u = G[v].get(j);
            if (!used[u]) {
              stack.addFirst(u);
              pushed = true;
            }
          }
          if (!pushed) {
            stack.pollFirst();
            if (!added[v]) {
              vs.add(v);
              added[v] = true;
            }
          }
        }
      }

    used = new boolean[V];
    int k = 0;
    Collections.reverse(vs);
    for (int i : vs)
      if (!used[i]) {
        stack.push(i);
        used[i] = true;
        cmp[i] = k;
        while (!stack.isEmpty()) {
          int v = stack.peek();
          boolean pushed = false;
          for (int u : rG[v])
            if (!used[u]) {
              used[u] = true;
              cmp[u] = k;
              stack.push(u);
              pushed = true;
            }
          if (!pushed) stack.pop();
        }
        k++;
      }
    return cmp;
  }
}