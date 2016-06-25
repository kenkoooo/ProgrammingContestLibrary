package graph

import spock.lang.Specification


class MinimumCostFlowTest extends Specification {
    def "AOJ 1246 を解かせる"() {
        setup:
        InputStream stream = getClass().getClassLoader().getResourceAsStream("AOJ-1246.in")
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream))

        def line
        ArrayDeque<String> list = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) list.add((String) line)
        ArrayList<Long> answers = new ArrayList<>();
        while (true) {
            int N = Integer.parseInt(list.poll())
            if (N == 0) break;

            int INF = (int) 1e6;
            MinimumCostFlow flow = new MinimumCostFlow(366);
            for (int i = 0; i < N; i++) {
                String[] uvw = list.poll().split(" ")
                int u = Integer.parseInt(uvw[0]) - 1, v = Integer.parseInt(uvw[1]) - 1, w = Integer.parseInt(uvw[2]);
                flow.addEdge(u, v + 1, 1, INF * (v + 1 - u) - w);
            }
            for (int i = 0; i < 365; i++) {
                flow.addEdge(i, i + 1, 2, INF);
            }
            answers.add(INF * 2 * 365 - flow.run(0, 365, 2));
        }
        stream = getClass().getClassLoader().getResourceAsStream("AOJ-1246.out")
        reader = new BufferedReader(new InputStreamReader(stream))
        ArrayDeque<String> check = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) check.add((String) line)
        assert answers.size() == check.size()
        for (int i = 0; i < answers.size(); i++) {
            assert answers.get(i) == Long.parseLong(check.poll())
        }


    }
}
