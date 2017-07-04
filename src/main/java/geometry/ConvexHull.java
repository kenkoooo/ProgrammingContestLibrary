package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConvexHull {

  /**
   * 線分の端にない点を含まない凸包を作成する
   *
   * @param ps 頂点のリスト
   * @return 凸包を構成する頂点のリスト
   */
  public static ArrayList<Point> run(ArrayList<Point> ps) {
    return run(ps, false);
  }

  /**
   * 凸包を作成する
   *
   * @param ps 頂点のリスト
   * @param containOnSegment 線分の端にない頂点も凸包に含むかどうか
   * @return 凸包を構成する頂点のリスト
   */
  public static ArrayList<Point> run(ArrayList<Point> ps, boolean containOnSegment) {
    int N = ps.size();
    if (N <= 1) {
      return new ArrayList<>(ps);
    }

    Collections.sort(ps);
    // 凸包の頂点数
    int k = 0;

    // 構築中の凸包
    Point[] qs = new Point[N * 2];

    // 下側の凸包を作成
    for (Point p : ps) {
      while (k > 1) {
        double det = qs[k - 1].sub(qs[k - 2]).det(p.sub(qs[k - 1]));
        if (det < 0 || (det <= 0 && !containOnSegment)) {
          k--;
        } else {
          break;
        }
      }
      qs[k++] = p;
    }

    // 上側の凸包を作成
    for (int i = N - 2, t = k; i >= 0; i--) {
      Point p = ps.get(i);
      while (k > t) {
        double det = qs[k - 1].sub(qs[k - 2]).det(p.sub(qs[k - 1]));
        if (det < 0 || (det <= 0 && !containOnSegment)) {
          k--;
        } else {
          break;
        }
      }
      qs[k++] = p;
    }

    ArrayList<Point> hull = new ArrayList<>(k - 1);
    hull.addAll(Arrays.asList(qs).subList(0, k - 1));
    return hull;
  }
}