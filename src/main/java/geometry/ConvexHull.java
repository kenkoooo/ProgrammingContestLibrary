package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ConvexHull {
  public static <T extends Number> ArrayList<T[]> run(T[][] xy) {
    int N = xy.length;
    if (N <= 1) {
      ArrayList<T[]> list = new ArrayList<>();
      Collections.addAll(list, xy);
      return list;
    }
    Arrays.sort(xy, new Comparator<T[]>() {
      @Override
      public int compare(T[] a, T[] b) {
        if (!a[0].equals(b[0])) return Double.compare(a[0].doubleValue(), b[0].doubleValue());
        return Double.compare(a[1].doubleValue(), b[1].doubleValue());
      }
    });

    int[] qs = new int[N * 2];//構築中の凸包
    int k = 0;//凸包の頂点数
    for (int i = 0; i < N; i++) {
      if (k >= 1 && xy[qs[k - 1]][0].equals(xy[i][0]) && xy[qs[k - 1]][1].equals(xy[i][1])) continue;
      while (k > 1 && ccw(xy[qs[k - 2]], xy[qs[k - 1]], xy[i]) > 0) k--;
      qs[k++] = i;
    }

    int inf = k + 1;
    for (int i = N - 2; i >= 0; i--) {
      if (xy[qs[k - 1]][0] == xy[i][0] && xy[qs[k - 1]][1] == xy[i][1]) continue;
      while (k >= inf && ccw(xy[qs[k - 2]], xy[qs[k - 1]], xy[i]) > 0) k--;
      qs[k++] = i;
    }

    ArrayList<T[]> ret = new ArrayList<>(k - 1);
    for (int i = 0; i < k - 1; i++) ret.add(xy[qs[i]]);
    return ret;
  }

  private static <T extends Number> double ccw(T[] a, T[] b, T[] t) {
    return (t[0].doubleValue() - a[0].doubleValue()) * (b[1].doubleValue()
      - a[1].doubleValue()) - (b[0].doubleValue() - a[0].doubleValue()) * (t[1].doubleValue() - a[1].doubleValue());
  }
}