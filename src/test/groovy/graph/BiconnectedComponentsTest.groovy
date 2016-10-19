package graph

import spock.lang.Specification

class BiconnectedComponentsTest extends Specification {
    def "心温まる手作り"() {
        setup:
        int N = 10
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<Integer>());
        }

        def edges = [
                [0, 1],
                [1, 2],
                [1, 3],
                [2, 3],
                [2, 4],
                [4, 5],
                [5, 6],
                [5, 9],
                [9, 7],
                [9, 8],
                [7, 8]
        ]
        edges.each { a ->
            int u = a[1]
            int v = a[0]
            graph.get(a[0]).add(new Integer(u))
            graph.get(a[1]).add(new Integer(v))
        }

        BiconnectedComponents bicc = new BiconnectedComponents(graph)
        ArrayList<int[]> bridges = bicc.bridges;
        def bAns = [[5, 6],
                    [5, 9],
                    [4, 5],
                    [2, 4],
                    [0, 1]]
        for (int i = 0; i < bridges.size(); i++) {
            assert bAns[i][0] == bridges[i][0]
            assert bAns[i][1] == bridges[i][1]
        }
        def ans = [5, 4, 4, 4, 3, 2, 0, 1, 1, 1]
        for (int i = 0; i < ans.size(); i++) {
            assert bicc.cmp[i] == ans[i]
        }

    }
}
