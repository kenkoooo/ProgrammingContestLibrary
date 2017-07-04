package graph

import spock.lang.Specification
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
}
