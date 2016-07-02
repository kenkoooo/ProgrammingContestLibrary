package graph;

import java.util.Arrays;

class UnionFind {
  // par[i]：データiが属する木の親の番号。i == par[i]のとき、データiは木の根ノードである
  int[] par;
  // sizes[i]：根ノードiの木に含まれるデータの数。iが根ノードでない場合は無意味な値となる
  int[] sizes;

  UnionFind(int n) {
    par = new int[n];
    sizes = new int[n];
    Arrays.fill(sizes, 1);
    // 最初は全てのデータiがグループiに存在するものとして初期化
    for (int i = 0; i < n; i++) par[i] = i;
  }

  // データxが属する木の根を得る
  int find(int x) {
    if (x == par[x]) return x;
    return par[x] = find(par[x]);  // 根を張り替えながら再帰的に根ノードを探す
  }

  // 2つのデータx, yが属する木をマージする
  void unite(int x, int y) {
    // データの根ノードを得る
    x = find(x);
    y = find(y);

    // 既に同じ木に属しているならマージしない
    if (x == y) return;

    // xの木がyの木より大きくなるようにする
    if (sizes[x] < sizes[y]) {
      int tx = x;
      x = y;
      y = tx;
    }

    // xがyの親になるように連結する
    par[y] = x;
    sizes[x] += sizes[y];
    // sizes[y] = 0;  // sizes[y]は無意味な値となるので0を入れておいてもよい
  }

  // 2つのデータx, yが属する木が同じならtrueを返す
  boolean same(int x, int y) {
    return find(x) == find(y);
  }

  // データxが含まれる木の大きさを返す
  int size(int x) {
    return sizes[find(x)];
  }
}