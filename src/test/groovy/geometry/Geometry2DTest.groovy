package geometry

import spock.lang.Specification

class Geometry2DTest extends Specification {
    class RailPoint implements Comparable<RailPoint> {
        double x, y;
        boolean above;

        RailPoint(double x, double y, boolean above) {
            this.x = x;
            this.y = y;
            this.above = above;
        }


        @Override
        int compareTo(RailPoint o) {
            return Double.compare(this.x, o.x)
        }
    };

    def "AOJ2003を解かせる"() {
        setup:
        for (int test = 1; test <= 4; test++) {
            String filename = "AOJ2003/D" + String.valueOf(test)
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename)
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
            ArrayList<String> ans = new ArrayList<>()

            int Q = Integer.parseInt(reader.readLine())
            for (int q = 0; q < Q; q++) {
                String[] line = reader.readLine().split(" ")
                int xa = Integer.parseInt(line[0])
                int ya = Integer.parseInt(line[1])
                int xb = Integer.parseInt(line[2])
                int yb = Integer.parseInt(line[3])
                Point A = new Point(xa, ya)
                Point B = new Point(xb, yb)

                int N = Integer.parseInt(reader.readLine())
                ArrayList<Point> above = new ArrayList<>();
                ArrayList<Point> under = new ArrayList<>();
                for (int i = 0; i < N; i++) {
                    String[] line2 = reader.readLine().split(" ")
                    int xs = Integer.parseInt(line2[0])
                    int ys = Integer.parseInt(line2[1])
                    int xt = Integer.parseInt(line2[2])
                    int yt = Integer.parseInt(line2[3])
                    Point S = new Point(xs, ys)
                    Point T = new Point(xt, yt)
                    int o = Integer.parseInt(line2[4])
                    int l = Integer.parseInt(line2[5])

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
                for (Point point : above) points.add(new RailPoint(point.x, point.y, true));
                for (Point point : under) points.add(new RailPoint(point.x, point.y, false));
                Collections.sort(points)
                int answer = 0
                if (points.size() > 0) {
                    boolean a = points.get(0).above
                    for (RailPoint point : points) if (point.above != a) {
                        answer++;
                        a = point.above
                    }
                }
                ans.add(String.valueOf(answer))
            }
            stream = getClass().getClassLoader().getResourceAsStream(filename + ".ans")
            reader = new BufferedReader(new InputStreamReader(stream))
            for (String a : ans) {
                assert a == reader.readLine()
            }
        }

    }
}
