package graph

import spock.lang.Specification


class DinitzTest extends Specification {
    def "Google Code Jam 2016 Round 1B Problem C. Technobabble を解かせる"() {
        setup:
        InputStream stream = getClass().getClassLoader().getResourceAsStream("GCJ2016R1B-C-large-practice.in")
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream))

        def line
        ArrayDeque<String> list = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) list.add((String) line)
        int T = Integer.parseInt(list.poll())
        ArrayList<Integer> answers = new ArrayList<>()
        T.times {
            int N = Integer.parseInt(list.poll())
            String[] prefix = new String[N]
            String[] suffix = new String[N]
            TreeSet<String> prefixSet = new TreeSet<>()
            TreeSet<String> suffixSet = new TreeSet<>()
            for (int i = 0; i < N; i++) {
                String[] titles = list.poll().split(" ")
                prefix[i] = titles[0]
                suffix[i] = titles[1]
                prefixSet.add(prefix[i])
                suffixSet.add(suffix[i])
            }
            ArrayList<String> prefixList = new ArrayList<>(prefixSet)
            ArrayList<String> suffixList = new ArrayList<>(suffixSet)

            int V = prefixList.size() + suffixList.size() + 2
            int source = V - 2, sink = V - 1;
            Dinitz dinitz = new Dinitz(V)
            for (int i = 0; i < N; i++) {
                int from = Collections.binarySearch(prefixList, prefix[i])
                int to = Collections.binarySearch(suffixList, suffix[i]) + prefixList.size()
                dinitz.addEdge(from, to, 1)
            }
            for (int i = 0; i < prefixList.size(); i++) dinitz.addEdge(source, i, 1)
            for (int i = 0; i < suffixList.size(); i++) dinitz.addEdge(i + prefixList.size(), sink, 1)
            int ans = N - (source - dinitz.maxFlow(source, sink))
            answers.add(ans)
        }
        stream = getClass().getClassLoader().getResourceAsStream("GCJ2016R1B-C-large-practice.out")
        reader = new BufferedReader(new InputStreamReader(stream))
        ArrayDeque<String> check = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) check.add((String) line)

        assert answers.size() == check.size()
        for (int i = 0; i < answers.size(); i++) {
            String[] ans = check.poll().split(" ")
            assert answers.get(i) == Integer.parseInt(ans[2])
        }


    }
}
