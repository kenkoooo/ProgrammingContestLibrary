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
            Integer[][] xy = new Integer[N][2];
            for (int i = 0; i < N; i++) {
                String[] ixy = list.poll().split(" ")
                xy[i][0] = Integer.parseInt(ixy[0]);
                xy[i][1] = Integer.parseInt(ixy[1]);
            }

            ArrayList<Integer[]> a = ConvexHull.run(xy);
            ans.add(String.valueOf(a.size()));
            int start = 0;
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i)[1] < a.get(start)[1] || (a.get(i)[1] == a.get(start)[1] && a.get(i)[0] < a.get(start)[0]))
                    start = i;
            }
            for (int i = 0; i < a.size(); i++) {
                Integer[] b = a.get((i + start) % a.size());
                ans.add(b[0] + " " + b[1]);
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
