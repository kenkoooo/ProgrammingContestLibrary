package geometry;

import java.util.ArrayList;
import java.util.Collections;

public class ConvexHull {
  public static ArrayList<Point> run(ArrayList<Point> ps) {
    int N = ps.size();
    if (N <= 1) {
      return new ArrayList<>(ps);
    }

    Collections.sort(ps);

    int[] qs = new int[N * 2];//構築中の凸包
    int k = 0;//凸包の頂点数

    // 下側の凸包を作成
    for (int i = 0; i < N; i++) {
      if (k >= 1 && ps.get(qs[k - 1]).x == ps.get(i).x
        && ps.get(qs[k - 1]).y == ps.get(i).y) continue;
      while (k > 1 && counterClockWise(ps.get(qs[k - 2]), ps.get(qs[k - 1]), ps.get(i)) > 0) k--;
      qs[k++] = i;
    }

    // 上側の凸包を作成
    int inf = k + 1;
    for (int i = N - 2; i >= 0; i--) {
      if (k >= 1 && ps.get(qs[k - 1]).x == ps.get(i).x
        && ps.get(qs[k - 1]).y == ps.get(i).y) continue;
      while (k >= inf && counterClockWise(ps.get(qs[k - 2]), ps.get(qs[k - 1]), ps.get(i)) > 0) k--;
      qs[k++] = i;
    }

    ArrayList<Point> ret = new ArrayList<>(k - 1);
    for (int i = 0; i < k - 1; i++) ret.add(ps.get(qs[i]));
    return ret;
  }

  /**
   * a->b から見て、t が反時計回り方向にあるなら正の値を返す
   *
   * @param a
   * @param b
   * @param t
   * @return
   */
  public static double counterClockWise(Point a, Point b, Point t) {
    return (t.x - a.x) * (b.y - a.y) - (b.x - a.x) * (t.y - a.y);
  }
}