package geometry;

public class Geometry2D {

  private static double EPS = 2e-8;

  /**
   * Return the point which is intersection of line p1-p2 and q1-q2
   */
  public static Point lineIntersection(Point p1, Point p2, Point q1, Point q2) {
    if (Math.abs(p2.sub(p1).det(q2.sub(q1))) < EPS) {
      return null;
    }

    double a = (q2.sub(q1)).det(q1.sub(p1));
    double b = (q2.sub(q1)).det(p2.sub(p1));
    return p1.add(p2.sub(p1).multiply(a / b));
  }

  /**
   * is the given point on the given segment
   *
   * @param p1 {@link Point} the edge of the segment
   * @param p2 {@link Point} the edge of the segment
   * @param q {@link Point} the given point
   * @return {@link true} if the given point is on the given segment, otherwise {@link false}
   */
  public static boolean onSegment(Point p1, Point p2, Point q) {
    return distancePointSegment(p1, p2, q) < EPS;
  }

  /**
   * calculate distance between segment and point
   *
   * @param p1 {@link Point} the edge of the segment
   * @param p2 {@link Point} the edge of the segment
   * @param q {@link Point} the given point
   * @return {@link true} if the given point is on the given segment, otherwise {@link false}
   */
  public static double distancePointSegment(Point p1, Point p2, Point q) {
    Point ab = p2.sub(p1);
    Point ac = q.sub(p1);
    if (ab.dot(ac) < EPS) {
      return ac.abs();
    }

    Point ba = p1.sub(p2);
    Point bc = q.sub(p2);
    if (ba.dot(bc) < EPS) {
      return bc.abs();
    }

    return Math.abs(ab.det(ac)) / ab.abs();
  }

  /**
   * @param p1 the edge of the left line
   * @param p2 the edge of the left line
   * @param q1 the edge of the right line
   * @param q2 the edge of the right line
   * @return {@link Point} the intersection of two lines if the two segments intersect, otherwise
   * {@link null}
   */
  public static Point segmentIntersection(Point p1, Point p2, Point q1, Point q2) {
    Point intersect = lineIntersection(p1, p2, q1, q2);
    if (intersect != null && onSegment(p1, p2, intersect) && onSegment(q1, q2, intersect)) {
      return intersect;
    } else {
      return null;
    }
  }

}
