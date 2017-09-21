package geometry;

import java.util.List;

public class Geometry2D {

  private static double EPS = 2e-8;
  private static double INF = 1e60;

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

  /**
   * @param p1 the edge of the left line
   * @param p2 the edge of the left line
   * @param q1 the edge of the right line
   * @param q2 the edge of the right line
   * @return {@link double} distance between the given two segment
   */
  public static double distanceSegments(Point p1, Point p2, Point q1, Point q2) {
    double dist = INF;
    dist = Math.min(dist, distancePointSegment(p1, p2, q1));
    dist = Math.min(dist, distancePointSegment(p1, p2, q2));
    dist = Math.min(dist, distancePointSegment(q1, q2, p1));
    dist = Math.min(dist, distancePointSegment(q1, q2, p2));
    if (segmentIntersection(p1, p2, q1, q2) != null) {
      return 0;
    }
    return dist;
  }

  public static double distanceHulls(List<Point> hull1, List<Point> hull2) {
    double dist = INF;
    int s1 = hull1.size();
    for (int i1 = 0; i1 < s1; i1++) {
      Point p11 = hull1.get(i1);
      Point p12 = hull1.get((i1 + 1) % s1);

      int s2 = hull2.size();
      for (int i2 = 0; i2 < s2; i2++) {
        Point p21 = hull2.get(i2);
        Point p22 = hull2.get((i2 + 1) % s2);

        dist = Math.min(dist, distanceSegments(p11, p12, p21, p22));
      }
    }

    for (Point p : hull1) {
      if (insideHull(hull2, p)) {
        return 0;
      }
    }
    for (Point p : hull2) {
      if (insideHull(hull1, p)) {
        return 0;
      }
    }

    return dist;
  }

  /**
   * @param hull {@link List<Point>} convex hull
   * @param p {@link Point}
   * @return {@link true} if the point is inside the hull, otherwise {@link false}
   */
  public static boolean insideHull(List<Point> hull, Point p) {
    if (hull.size() < 3) {
      return false;
    }
    Boolean positive = null;
    for (int i = 0; i < hull.size(); i++) {
      Point a = hull.get(i);
      Point b = hull.get((i + 1) % hull.size());
      double det = p.sub(a).det(b.sub(a));
      if (Math.abs(det) < EPS) {
        continue;
      }

      if (positive == null) {
        positive = det > 0;
      } else if (positive != det > 0) {
        return false;
      }
    }
    return true;
  }
}
