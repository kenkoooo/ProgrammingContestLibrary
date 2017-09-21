package graph

import spock.lang.Specification
import utils.TestCaseCounter
import utils.TestUtils


class MinimumCostFlowTest extends Specification {
    def "AOJ 1246 を解かせる"() {
        setup:
        def inQ = TestUtils.parseResourceInput(getClass(), "AOJ-1246.in")
        def outQ = TestUtils.parseResourceInput(getClass(), "AOJ-1246.out")

        while (true) {
            int N = Integer.parseInt(inQ.poll())
            if (N == 0) break

            int INF = (int) 1e6
            MinimumCostFlow flow = new MinimumCostFlow(366)
            for (int i = 0; i < N; i++) {
                int u = Integer.parseInt(inQ.poll()) - 1
                int v = Integer.parseInt(inQ.poll()) - 1
                int w = Integer.parseInt(inQ.poll())
                flow.addEdge(u, v + 1, 1, INF * (v + 1 - u) - w)
            }
            for (int i = 0; i < 365; i++) {
                flow.addEdge(i, i + 1, 2, INF)
            }
            int ans = INF * 2 * 365 - flow.run(0, 365, 2)
            assert ans == Integer.parseInt(outQ.poll())
        }
    }

    def "模擬国内予選 2017 F"() {
        setup:
        def inQ = TestUtils.loadResourceFiles("jag-domestic-2017-F/in", getClass())
        def outQ = TestUtils.loadResourceFiles("jag-domestic-2017-F/out", getClass())
        def counter = new TestCaseCounter(inQ)

        while (counter.hasNext()) {
            int N = Integer.parseInt(inQ.poll())
            if (N == 0) {
                continue
            }

            int[][] matryoshka = new int[N][3]
            for (int i = 0; i < N; i++) {
                matryoshka[i][0] = Integer.parseInt(inQ.poll())
                matryoshka[i][1] = Integer.parseInt(inQ.poll())
                matryoshka[i][2] = Integer.parseInt(inQ.poll())

                Arrays.sort(matryoshka[i])
            }

            MinimumCostFlow costFlow = new MinimumCostFlow(N * 2 + 2)
            int source = N * 2
            int sink = source + 1
            for (int i = 0; i < N; i++) {
                int[] m = matryoshka[i]
                costFlow.addEdge(i, sink, 1, m[0] * m[1] * m[2])
                costFlow.addEdge(i + N, sink, 1, 0)
                costFlow.addEdge(source, i, 1, 0)
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == j) continue
                    int[] p = matryoshka[i]
                    int[] q = matryoshka[j]

                    if (p[0] < q[0] && p[1] < q[1] && p[2] < q[2]) {
                        costFlow.addEdge(i, j + N, 1, 0)
                    }
                }
            }
            int cost = costFlow.run(source, sink, N)
            assert cost==Integer.parseInt(outQ.poll())
        }

    }
}
