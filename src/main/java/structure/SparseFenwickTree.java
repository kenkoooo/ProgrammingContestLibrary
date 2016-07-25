package structure;

import java.util.TreeMap;

public class SparseFenwickTree {
  int N;
  TreeMap<Integer, Integer> data;

  SparseFenwickTree(int N) {
    this.N = N + 1;
    data = new TreeMap<>();
  }

  void add(int k, int val) {
    for (int x = k; x < N; x |= x + 1) {
      Integer d = data.get(x);
      if (d == null) d = 0;
      data.put(x, d + val);
    }
  }

  // [0, k)
  int sum(int k) {
    if (k >= N) k = N - 1;
    int ret = 0;
    for (int x = k - 1; x >= 0; x = (x & (x + 1)) - 1) {
      Integer d = data.get(x);
      if (d == null) d = 0;
      ret += d;
    }
    return ret;
  }

  // [l, r)
  int sum(int l, int r) {
    return sum(r) - sum(l);
  }

  int get(int k) {
    assert (0 <= k && k < N);
    return sum(k + 1) - sum(k);
  }
}
