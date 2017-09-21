package graph

import spock.lang.Specification
import utils.TestCaseCounter
import utils.TestUtils

class PushRelabelTest extends Specification {
    def "Google Code Jam 2016 Round 1B Problem C. Technobabble を解かせる"() {
        setup:
        ArrayDeque<String> inputStringDeque = TestUtils.parseResourceInput(getClass(), "GCJ2016R1B-C-large-practice.in")

        when:
        int testCaseNum = Integer.parseInt(inputStringDeque.poll())
        ArrayList<String> answers = new ArrayList<>()
        testCaseNum.times {
            int N = Integer.parseInt(inputStringDeque.poll())
            String[] prefix = new String[N]
            String[] suffix = new String[N]
            TreeSet<String> prefixSet = new TreeSet<>()
            TreeSet<String> suffixSet = new TreeSet<>()
            for (int i = 0; i < N; i++) {
                prefix[i] = inputStringDeque.poll()
                suffix[i] = inputStringDeque.poll()
                prefixSet.add(prefix[i])
                suffixSet.add(suffix[i])
            }
            ArrayList<String> prefixList = new ArrayList<>(prefixSet)
            ArrayList<String> suffixList = new ArrayList<>(suffixSet)

            int V = prefixList.size() + suffixList.size() + 2
            int source = V - 2, sink = V - 1

            PushRelabel dinitz = new PushRelabel(V)
            for (int i = 0; i < N; i++) {
                int from = Collections.binarySearch(prefixList, prefix[i])
                int to = Collections.binarySearch(suffixList, suffix[i]) + prefixList.size()
                dinitz.addEdge(from, to, 1)
            }
            for (int i = 0; i < prefixList.size(); i++) dinitz.addEdge(source, i, 1)
            for (int i = 0; i < suffixList.size(); i++) dinitz.addEdge(i + prefixList.size(), sink, 1)
            int ans = N - (source - dinitz.maxFlow(source, sink))
            answers.add(String.valueOf(ans))
        }

        then:
        ArrayDeque<String> check = TestUtils.parseResourceInput(getClass(), "GCJ2016R1B-C-large-practice.out")
        assert answers.size() * 3 == check.size()
        for (int i = 0; i < answers.size(); i++) {
            check.poll()
            check.poll()
            assert answers.get(i) == check.poll()
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
            PushRelabel pushRelabel = new PushRelabel(V)
            E.times {
                int u = Integer.parseInt(inQ.poll())
                int v = Integer.parseInt(inQ.poll())
                int c = Integer.parseInt(inQ.poll())
                pushRelabel.addEdge(u, v, c)
            }
            long ans = pushRelabel.maxFlow(0, V - 1)
            assert ans == Long.parseLong(outQ.poll())
        }
    }
}
