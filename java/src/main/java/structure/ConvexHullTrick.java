package structure;

import java.util.ArrayList;

public class ConvexHullTrick {
  private ArrayList<long[]> ls = new ArrayList<>();

  private boolean check(long[] l1, long[] l2, long[] l3) {
    return (l2[0] - l1[0]) * (l3[1] - l2[1]) >=
      (l2[1] - l1[1]) * (l3[0] - l2[0]);
  }

  /**
   * 傾き a の値が単調増加になるように直線を追加する
   *
   * @param a
   * @param b
   */
  public void add(long a, long b) {
    long[] l = new long[]{a, b};
    while (ls.size() >= 2 && check(ls.get(ls.size() - 2), ls.get(ls.size() - 1), l))
      ls.remove(ls.size() - 1);
    ls.add(l);
  }

  private long f(int k, long x) {
    long[] l = ls.get(k);
    return l[0] * x + l[1];
  }

  /**
   * x に対して a*x+b が最小となる直線を探し、その値を返す。
   *
   * @param x
   * @return
   */
  public long query(long x) {
    int low = -1;
    int high = ls.size() - 1;
    while (high - low > 1) {
      int mid = (high + low) / 2;
      if (f(mid, x) >= f(mid + 1, x)) {
        low = mid;
      } else {
        high = mid;
      }
    }
    return f(high, x);
  }
}
