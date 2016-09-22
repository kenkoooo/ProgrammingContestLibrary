package geometry;

public class Geometry2D {
  private static double EPS = 2e-8;
  /**
   * Return the point which is intersection of line p1-p2 and q1-q2
   *
   * @param p1
   * @param p2
   * @param q1
   * @param q2
   * @return
   */
  public static Point lineIntersection(Point p1, Point p2, Point q1, Point q2) {
    double a = (q2.sub(q1)).det(q1.sub(p1));
    double b = (q2.sub(q1)).det(p2.sub(p1));
    return p1.add((p2.sub(p1)).multiply(a / b));
  }

  /**
   * Return point p is on segment a-b
   *
   * @param a
   * @param b
   * @param p
   * @return
   */
  public static boolean onSegment(Point a, Point b, Point p) {
    if (Math.abs(a.x - b.x) < EPS)
      return Math.min(a.y, b.y) <= p.y && p.y <= Math.max(a.y, b.y);
    double alpha = (b.y - a.y) / (b.x - a.x);
    double beta = a.y - alpha * a.x;
    return Math.abs(p.y - (alpha * p.x + beta)) < EPS && Math.min(a.x, b.x) <= p.x && p.x <= Math.max(a.x, b.x);
  }
}
