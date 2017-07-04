package geometry

import spock.lang.Specification
import utils.TestCaseCounter
import utils.TestUtils

class Geometry2DTest extends Specification {
    class RailPoint implements Comparable<RailPoint> {
        double x, y
        boolean above

        RailPoint(double x, double y, boolean above) {
            this.x = x
            this.y = y
            this.above = above
        }


        @Override
        int compareTo(RailPoint o) {
            return Double.compare(this.x, o.x)
        }
    };

    def "AOJ2003を解かせる"() {
        setup:
        def inQ = TestUtils.loadResourceFiles("AOJ2003/in", getClass())
        def outQ = TestUtils.loadResourceFiles("AOJ2003/out", getClass())

        def counter = new TestCaseCounter(inQ)
        while (counter.hasNext()) {
            int Q = Integer.parseInt(inQ.poll())
            for (int q = 0; q < Q; q++) {
                int xa = Integer.parseInt(inQ.poll())
                int ya = Integer.parseInt(inQ.poll())
                int xb = Integer.parseInt(inQ.poll())
                int yb = Integer.parseInt(inQ.poll())
                Point A = new Point(xa, ya)
                Point B = new Point(xb, yb)

                int N = Integer.parseInt(inQ.poll())
                ArrayList<Point> above = new ArrayList<>()
                ArrayList<Point> under = new ArrayList<>()
                for (int i = 0; i < N; i++) {
                    int xs = Integer.parseInt(inQ.poll())
                    int ys = Integer.parseInt(inQ.poll())
                    int xt = Integer.parseInt(inQ.poll())
                    int yt = Integer.parseInt(inQ.poll())
                    Point S = new Point(xs, ys)
                    Point T = new Point(xt, yt)
                    int o = Integer.parseInt(inQ.poll())
                    int l = Integer.parseInt(inQ.poll())

                    Point intersection = Geometry2D.lineIntersection(S, T, A, B)
                    if (Geometry2D.onSegment(A, B, intersection) && Geometry2D.onSegment(S, T, intersection)) {
                        if (o == 1) {
                            if (l == 1) above.add(intersection)
                            else under.add(intersection)
                        }
                        if (o == 0) {
                            if (l == 0) above.add(intersection)
                            else under.add(intersection)
                        }
                    }
                }

                ArrayList<RailPoint> points = new ArrayList<>()
                for (Point point : above) points.add(new RailPoint(point.x, point.y, true))
                for (Point point : under) points.add(new RailPoint(point.x, point.y, false))
                Collections.sort(points)
                int answer = 0
                if (points.size() > 0) {
                    boolean a = points.get(0).above
                    for (RailPoint point : points) if (point.above != a) {
                        answer++
                        a = point.above
                    }
                }

                assert answer == Integer.parseInt(outQ.poll())
            }
        }
    }
}
