package geometry

import spock.lang.Specification
import utils.TestCaseCounter
import utils.TestUtils

class ConvexHullTest extends Specification {
    def "AOJ CGL_4_A を解かせる"() {
        setup:
        def inQ = TestUtils.loadResourceFiles("AOJ-CGL_4_A/in", getClass())
        def outQ = TestUtils.loadResourceFiles("AOJ-CGL_4_A/out", getClass())

        def counter = new TestCaseCounter(inQ)
        while (counter.hasNext()) {
            int N = Integer.parseInt(inQ.poll())
            ArrayList<Point> ps = new ArrayList<>()
            for (int i = 0; i < N; i++) {
                int x = Integer.parseInt(inQ.poll())
                int y = Integer.parseInt(inQ.poll())
                ps.add(new Point(x, y))
            }

            ArrayList<Point> hull = ConvexHull.run(ps, true)
            assert hull.size() == Integer.parseInt(outQ.poll())

            // 凸多角形の頂点で最も下にあるものの中で最も左にある頂点から順に、反時計周りで頂点の座標を出力する。
            int start = 0
            for (int i = 0; i < hull.size(); i++) {
                if (hull.get(i).y < hull.get(start).y) {
                    start = i
                } else if (hull.get(i).y == hull.get(start).y && hull.get(i).x < hull.get(start).x) {
                    start = i
                }
            }
            for (int i = 0; i < hull.size(); i++) {
                Point p = hull.get((start + i) % hull.size())
                assert p.x == Double.parseDouble(outQ.poll())
                assert p.y == Double.parseDouble(outQ.poll())
            }
        }
    }

    def "2017 模擬国内予選 E を解かせる"() {
        setup:
        def inQ = TestUtils.loadResourceFiles("jag-domestic-2017-E/in", getClass())
        def outQ = TestUtils.loadResourceFiles("jag-domestic-2017-E/out", getClass())

        def counter = new TestCaseCounter(inQ)
        while (counter.hasNext()) {
            int N = Integer.parseInt(inQ.poll())
            if (N == 0) {
                continue
            }

            ArrayList<Point>[] lists = new ArrayList[N]
            for (int i = 0; i < N; i++) {
                lists[i] = new ArrayList<>()
            }
            int[] H = new int[N]
            for (int i = 0; i < N; i++) {
                int size = Integer.parseInt(inQ.poll())
                H[i] = Integer.parseInt(inQ.poll())
                for (int j = 0; j < size; j++) {
                    int x = Integer.parseInt(inQ.poll())
                    int y = Integer.parseInt(inQ.poll())
                    lists[i].add(new Point(x, y))
                }
            }

            int theta = Integer.parseInt(inQ.poll())
            int phi = Integer.parseInt(inQ.poll())
            Point start = new Point(Integer.parseInt(inQ.poll()), Integer.parseInt(inQ.poll()))
            Point goal = new Point(Integer.parseInt(inQ.poll()), Integer.parseInt(inQ.poll()))

            for (int i = 0; i < N; i++) {
                int size = lists[i].size()
                int height = H[i]
                for (int j = 0; j < size; j++) {
                    Point p = lists[i].get(j)
                    double rad = Math.toRadians(theta + 180)
                    double radius = height / Math.tan(Math.toRadians(phi))
                    double x = Math.cos(rad) * radius
                    double y = Math.sin(rad) * radius
                    lists[i].add(new Point(x, y))
                }
            }

            ArrayList<Point>[] hull = new ArrayList<Point>[N]
            for (int i = 0; i < N; i++) {
                hull[i] = ConvexHull.run(lists[i])
            }
        }


    }
}
