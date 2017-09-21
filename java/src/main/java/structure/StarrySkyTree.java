package structure;

import java.util.Arrays;

public class StarrySkyTree {
  private final int N = 1 << 20;
  private static final long INF = (long) 1e18;

  private long[] segMin, segMax, segAdd;
  public StarrySkyTree() {
    segMin = new long[N * 2];
    segMax = new long[N * 2];
    segAdd = new long[N * 2];
    Arrays.fill(segMin, INF);
    Arrays.fill(segMax, -INF);
  }

  public void add(int a, int b, long x) {
    add(a, b, x, 0, 0, N);
  }

  // Add x to [a, b)
  public void add(int a, int b, long x, int k, int l, int r) {
    if (r <= a || b <= l) return;
    if (a <= l && r <= b) {
      segAdd[k] += x;
      while (k > 0) {
        k = (k - 1) / 2;
        segMin[k] = Math.min(segMin[k * 2 + 1] + segAdd[k * 2 + 1], segMin[k * 2 + 2] + segAdd[k * 2 + 2]);
        segMax[k] = Math.max(segMax[k * 2 + 1] + segAdd[k * 2 + 1], segMax[k * 2 + 2] + segAdd[k * 2 + 2]);
      }
      return;
    }
    add(a, b, x, k * 2 + 1, l, (l + r) / 2);
    add(a, b, x, k * 2 + 2, (l + r) / 2, r);
  }

  public void update(int k, long value) {
    k += N - 1;
    segMin[k] = value;
    segMax[k] = value;
    while (k > 0) {
      k = (k - 1) / 2;
      segMin[k] = Math.min(segMin[k * 2 + 1], segMin[k * 2 + 2]);
      segMax[k] = Math.max(segMax[k * 2 + 1], segMax[k * 2 + 2]);
    }
  }

  public long getMin(int a, int b) {
    return getMin(a, b, 0, 0, N);
  }

  //[a, b)
  private long getMin(int a, int b, int k, int l, int r) {
    if (r <= a || b <= l) return INF;
    if (a <= l && r <= b) return (segMin[k] + segAdd[k]);
    long x = getMin(a, b, k * 2 + 1, l, (l + r) / 2);
    long y = getMin(a, b, k * 2 + 2, (l + r) / 2, r);
    return (Math.min(x, y) + segAdd[k]);
  }

  public long getMax(int a, int b) {
    return getMax(a, b, 0, 0, N);
  }

  //[a, b)
  private long getMax(int a, int b, int k, int l, int r) {
    if (r <= a || b <= l) return -INF;
    if (a <= l && r <= b) return (segMax[k] + segAdd[k]);
    long x = getMax(a, b, k * 2 + 1, l, (l + r) / 2);
    long y = getMax(a, b, k * 2 + 2, (l + r) / 2, r);
    return (Math.max(x, y) + segAdd[k]);
  }
}