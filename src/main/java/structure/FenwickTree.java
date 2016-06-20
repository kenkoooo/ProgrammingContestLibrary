package structure;

public class FenwickTree {
  int N;
  long[] data;

  FenwickTree(int N) {
    this.N = N + 1;
    data = new long[N + 1];
  }

  void add(int k, long val) {
    for (int x = k; x < N; x |= x + 1) {
      data[x] += val;
    }
  }

  // [0, k)
  long sum(int k) {
    if (k >= N) k = N - 1;
    int ret = 0;
    for (int x = k - 1; x >= 0; x = (x & (x + 1)) - 1) {
      ret += data[x];
    }
    return ret;
  }

  // [l, r)
  long sum(int l, int r) {
    return sum(r) - sum(l);
  }

  long get(int k) {
    assert (0 <= k && k < N);
    return sum(k + 1) - sum(k);
  }

  int getAsSetOf(int w) {
    w++;
    if (w <= 0) return -1;
    int x = 0;
    int k = 1;
    while (k * 2 <= N) k *= 2;
    for (; k > 0; k /= 2) {
      if (x + k <= N && data[x + k - 1] < w) {
        w -= data[x + k - 1];
        x += k;
      }
    }
    return x;
  }
}
