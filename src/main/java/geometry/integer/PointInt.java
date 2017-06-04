package geometry.integer;

public class PointInt implements Comparable<PointInt> {

  final int x, y;

  public PointInt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int compareTo(PointInt o) {
    if (this.x == o.x) {
      return this.y - o.y;
    }
    return this.x - o.x;
  }

  /**
   * Calculate vector(a) + vector(b)
   *
   * @param a vector a
   * @param b vector b
   * @return vector(a) + vector(b)
   */
  public static PointInt add(PointInt a, PointInt b) {
    return new PointInt(a.x + b.x, a.y + b.y);
  }

  /**
   * Calculate vector(a) - vector(b)
   *
   * @param a vector a
   * @param b vector b
   * @return vector(a) - vector(b)
   */
  public static PointInt substract(PointInt a, PointInt b) {
    return new PointInt(a.x - b.x, a.y - b.y);
  }

  /**
   * Calculate vector(a) cross vector(b)
   *
   * @param a vector a
   * @param b vector b
   * @return vector(a) cross vector(b)
   */
  public static int cross(PointInt a, PointInt b) {
    return a.x * b.y - a.y * b.x;
  }

  /**
   * Calculate vector(a) dot vector(b)
   *
   * @param a vector a
   * @param b vector b
   * @return vector(a) dot vector(b)
   */
  public static int dot(PointInt a, PointInt b) {
    return a.x * b.x + a.y * b.y;
  }

  /**
   * @param line an array of line which consists of two {@link PointInt}
   * @param p {@link PointInt}
   * @return {@link true} if p is on the line
   */
  public static boolean onLine(PointInt[] line, PointInt p) {
    PointInt a = line[0];
    PointInt b = line[1];
    return cross(substract(a, b), substract(p, a)) == 0;
  }

  /**
   * @return {@link true} if the two segments are intersecting.
   */
  public static boolean isIntersectSegments(PointInt[] segment1, PointInt[] segment2) {
    PointInt a1 = segment1[0];
    PointInt a2 = segment1[1];
    PointInt b1 = segment2[0];
    PointInt b2 = segment2[1];
    return (cross(substract(a2, a1), substract(b1, a1))
        * cross(substract(a2, a1), substract(b2, a1)) < 0)
        && (cross(substract(b2, b1), substract(a1, b1))
        * cross(substract(b2, b1), substract(a2, b1)) < 0);
  }

  /**
   * @return {@link true} if two lines are parallel.
   */
  public static boolean isParallel(PointInt[] line1, PointInt[] line2) {
    PointInt a1 = line1[0];
    PointInt a2 = line1[1];
    PointInt b1 = line2[0];
    PointInt b2 = line2[1];
    return cross(substract(a1, a2), substract(b1, b2)) == 0;
  }

  /**
   * @return an array of of point (x, y)
   */
  public static double[] getIntersection(PointInt[] line1, PointInt[] line2) {
    PointInt a1 = line1[0];
    PointInt a2 = line1[1];
    PointInt b1 = line2[0];
    PointInt b2 = line2[1];
    PointInt a = substract(a2, a1);
    PointInt b = substract(b2, b1);
    int d2 = cross(b, a);
    if (d2 == 0) {
      throw new IllegalStateException();
    }
    int d1 = cross(b, substract(b1, a1));

    double x = (double) a1.x + a.x * d1 / d2;
    double y = (double) a1.y + a.y * d1 / d2;
    return new double[]{x, y};
  }
}