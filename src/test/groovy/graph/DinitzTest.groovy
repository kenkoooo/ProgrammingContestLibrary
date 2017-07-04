package graph

import spock.lang.Specification
import utils.TestCaseCounter
import utils.TestUtils


class DinitzTest extends Specification {
    def "Google Code Jam 2016 Round 1B Problem C. Technobabble を解かせる"() {
        setup:
        def inQ = TestUtils.parseResourceInput(getClass(), "GCJ2016R1B-C-large-practice.in")
        def outQ = TestUtils.parseResourceInput(getClass(), "GCJ2016R1B-C-large-practice.out")

        int testCaseNum = Integer.parseInt(inQ.poll())
        testCaseNum.times {
            int N = Integer.parseInt(inQ.poll())
            String[] prefix = new String[N]
            String[] suffix = new String[N]
            TreeSet<String> prefixSet = new TreeSet<>()
            TreeSet<String> suffixSet = new TreeSet<>()
            for (int i = 0; i < N; i++) {
                prefix[i] = inQ.poll()
                suffix[i] = inQ.poll()
                prefixSet.add(prefix[i])
                suffixSet.add(suffix[i])
            }
            ArrayList<String> prefixList = new ArrayList<>(prefixSet)
            ArrayList<String> suffixList = new ArrayList<>(suffixSet)

            int V = prefixList.size() + suffixList.size() + 2
            int source = V - 2, sink = V - 1
            Dinitz dinitz = new Dinitz(V)
            for (int i = 0; i < N; i++) {
                int from = Collections.binarySearch(prefixList, prefix[i])
                int to = Collections.binarySearch(suffixList, suffix[i]) + prefixList.size()
                dinitz.addEdge(from, to, 1)
            }
            for (int i = 0; i < prefixList.size(); i++) dinitz.addEdge(source, i, 1)
            for (int i = 0; i < suffixList.size(); i++) dinitz.addEdge(i + prefixList.size(), sink, 1)
            int ans = N - (source - dinitz.maxFlow(source, sink))

            outQ.poll()
            outQ.poll()
            assert ans == Integer.parseInt(outQ.poll())
        }
    }

    def "Solve AOJ GRL_6_A"() {
        setup:
        def inQ = TestUtils.loadResourceFiles("GRL_6_A/in", getClass())
        def outQ = TestUtils.loadResourceFiles("GRL_6_A/out", getClass())

        def counter = new TestCaseCounter(inQ)
        while (counter.hasNext()) {
            int V = Integer.parseInt(inQ.poll())
            int E = Integer.parseInt(inQ.poll())
            Dinitz dinitz = new Dinitz(V)
            E.times {
                int u = Integer.parseInt(inQ.poll())
                int v = Integer.parseInt(inQ.poll())
                int c = Integer.parseInt(inQ.poll())
                dinitz.addEdge(u, v, c)
            }
            long ans = dinitz.maxFlow(0, V - 1)
            assert ans == Long.parseLong(outQ.poll())
        }
    }
}