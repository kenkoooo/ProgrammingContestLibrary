package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

@SuppressWarnings("unchecked")
public class DynamicPrunedLandmarkLabeling {
  public static int INF = (int) 10000;

  public static class Edge implements Comparable<Edge> {
    int to, weight;
    Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
      return this.weight - o.weight;
    }
  }

  class Index {
    ArrayList<Integer> spt_v = new ArrayList<>();
    ArrayList<Integer> spt_d = new ArrayList<>();
    int size() {
      return spt_v.size();
    }

    void update(int v, int d) {
      if (spt_v.isEmpty() || spt_v.get(spt_v.size() - 1) < v) {
        spt_v.add(v);
        spt_d.add(d);
        return;
      }

      int i;
      for (i = 0; i < spt_v.size(); ++i)
        if (spt_v.get(i) >= v) break;

      if (spt_v.get(i) == v) {
        if (spt_d.get(i) > d) spt_d.set(i, d);
        return;
      }

      spt_v.add(spt_v.get(size() - 1));
      spt_d.add(spt_d.get(size() - 1));
      for (int j = spt_v.size() - 1; j > i; --j) {
        spt_v.set(j, spt_v.get(j - 1));
        spt_d.set(j, spt_d.get(j - 1));
      }
      spt_v.set(i, v);
      spt_d.set(i, d);
    }

  }

  int numV;
  int[] rank;//rank[v] := 頂点 v の rank
  int[] inv;// inv[r] := rank が r の頂点
  ArrayList<Edge>[][] G = new ArrayList[2][];
  Index[][] idx = new Index[2][];

  DynamicPrunedLandmarkLabeling(ArrayList<Edge>[] g) {
    numV = g.length;
    rank = new int[numV];
    inv = new int[numV];
    for (int i = 0; i < 2; i++) {
      G[i] = new ArrayList[numV];
      idx[i] = new Index[numV];
      for (int j = 0; j < numV; j++) {
        G[i][j] = new ArrayList<>();
        idx[i][j] = new Index();
      }
    }

    for (int from = 0; from < numV; from++)
      for (Edge edge : g[from]) {
        G[0][from].add(new Edge(edge.to, edge.weight));
        G[1][edge.to].add(new Edge(from, edge.weight));
      }

    //頂点を次数の降順でソートする
    int[] deg = new int[numV];
    for (int i = 0; i < numV; i++)
      for (Edge edge : g[i]) {
        deg[i]++;
        deg[edge.to]++;
      }
    int[][] sorting = new int[numV][];
    for (int i = 0; i < numV; i++)
      sorting[i] = new int[]{deg[i], i};

    Arrays.sort(sorting, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return -Integer.compare(o1[1], o2[1]);
      }
    });

    //rank を割り振る
    for (int i = 0; i < numV; i++) {
      inv[i] = sorting[i][1];
      rank[inv[i]] = i;
    }

    boolean[] used = new boolean[numV];
    for (int root = 0; root < numV; root++) {
      if (used[root]) continue;
      pruneBFS(root, 0, used);
      pruneBFS(root, 1, used);
      used[root] = true;
    }
  }

  void pruneBFS(int root, int direction, boolean[] used) {
    int another = direction ^ 1;
    PriorityQueue<Edge> queue = new PriorityQueue<>();
    queue.add(new Edge(root, 0));
    int[] dist = new int[numV];
    Arrays.fill(dist, INF);
    dist[root] = 0;

    while (!queue.isEmpty()) {
      int u = queue.poll().to;
      if (u != root && distanceLess(root, u, direction, dist[u]) <= dist[u])
        continue;
      idx[another][inv[u]].update(root, dist[u]);

      for (Edge edge : G[direction][inv[u]]) {
        int w = rank[edge.to];
        if (used[w]) continue;
        if (dist[w] < dist[u] + edge.weight) continue;
        dist[w] = dist[u] + edge.weight;
        queue.add(new Edge(w, dist[w]));
      }
    }
    return;
  }

  int distanceLess(int from, int to, int direction, int upperLimit) {

    int another = direction ^ 1;

    int d = INF;
    Index idx_from = idx[direction][inv[from]];
    Index idx_to = idx[another][inv[to]];
    for (int i1 = 0, i2 = 0; i1 < idx_from.size() || i2 < idx_to.size(); ) {
      int v1 = (i1 < idx_from.size() ? idx_from.spt_v.get(i1) : INF);
      int v2 = (i2 < idx_to.size() ? idx_to.spt_v.get(i2) : INF);
      if (v1 == v2) {
        int td = idx_from.spt_d.get(i1) + idx_to.spt_d.get(i2);
        if (td < d) {
          d = td;
          if (d <= upperLimit) return d;
        }
        i1++;
        i2++;
      } else {
        if (v1 == to && idx_from.spt_d.get(i1) <= d) return idx_from.spt_d.get(i1);
        if (v2 == from && idx_to.spt_d.get(i2) <= d) return idx_to.spt_d.get(i2);
        if (v1 < v2) i1++;
        if (v1 > v2) i2++;
      }
    }
    if (d >= INF - 2) d = INF;
    return d;
  }

  public int queryDistance(int from, int to) {
    if (from == to) return 0;
    from = rank[from];
    to = rank[to];
    return distanceLess(from, to, 0, 0);
  }

}