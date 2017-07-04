package geometry

import spock.lang.Specification
import utils.TestUtils

class ConvexHullTest extends Specification {
    def "AOJ CGL_4_A を解かせる"() {
        setup:
        def inQ = TestUtils.loadResourceFiles("AOJ-CGL_4_A/in", getClass())
        def outQ = TestUtils.loadResourceFiles("AOJ-CGL_4_A/out", getClass())

        int testcase = 0
        while (!inQ.isEmpty()) {
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

            println("Test ${testcase}: Accepted")
            testcase++
        }

    }

}
