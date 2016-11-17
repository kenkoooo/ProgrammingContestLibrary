package structure;

import java.util.Arrays;

public class RMQ {
  private static long INF = (long) 1e18;
  private int N;
  private long[] seg;

  RMQ(long[] array) {
    N = Integer.highestOneBit(array.length) * 2;
    seg = new long[N * 2];
    Arrays.fill(seg, INF);
    for (int i = 0; i < array.length; i++) update(i, array[i]);
  }

  RMQ(int M) {
    N = Integer.highestOneBit(M) * 2;
    seg = new long[N * 2];
    Arrays.fill(seg, INF);
  }

  public void update(int k, long value) {
    seg[k += N - 1] = value;
    while (k > 0) {
      k = (k - 1) / 2;
      seg[k] = Math.min(seg[k * 2 + 1], seg[k * 2 + 2]);
    }
  }

  //[a, b)
  long query(int a, int b) {
    return query(a, b, 0, 0, N);
  }

  long query(int a, int b, int k, int l, int r) {
    if (r <= a || b <= l) return INF;
    if (a <= l && r <= b) return seg[k];
    long x = query(a, b, k * 2 + 1, l, (l + r) / 2);
    long y = query(a, b, k * 2 + 2, (l + r) / 2, r);
    return Math.min(x, y);
  }
}