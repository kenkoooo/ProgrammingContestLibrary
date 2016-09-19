package geometry

import spock.lang.Specification

class ConvexHullTest extends Specification {
    def "AOJ CGL_4_A を解かせる"() {
        setup:
        InputStream stream = getClass().getClassLoader().getResourceAsStream("AOJ-CGL_4_A.in")
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream))

        def line
        ArrayDeque<String> list = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) list.add((String) line)
        ArrayList<String> ans = new ArrayList<>();
        while (!list.isEmpty()) {
            int N = Integer.parseInt(list.poll())
            ArrayList<Point> ps = new ArrayList<>(N);
            for (int i = 0; i < N; i++) {
                String[] ixy = list.poll().split(" ")
                int x = Integer.parseInt(ixy[0]);
                int y = Integer.parseInt(ixy[1]);
                ps.add(new Point(x, y))
            }

            ArrayList<Point> a = ConvexHull.run(ps);
            ans.add(String.valueOf(a.size()));
            int start = 0;
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).y < a.get(start).y
                        || (a.get(i).y == a.get(start).y && a.get(i).x < a.get(start).x))
                    start = i;
            }
            for (int i = 0; i < a.size(); i++) {
                Point b = a.get((i + start) % a.size());
                ans.add(b.x + " " + b.y);
            }
        }

        stream.close()
        reader.close()
        stream = getClass().getClassLoader().getResourceAsStream("AOJ-CGL_4_A.out")
        reader = new BufferedReader(new InputStreamReader(stream))
        ArrayDeque<String> check = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) check.add((String) line)
        assert ans.size() == check.size()
        for (String a : ans) {
            assert a == check.poll()
        }

    }

}
