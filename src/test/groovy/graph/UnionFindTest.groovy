package graph

import spock.lang.Specification

class UnionFindTest extends Specification {
    def "AOJ 1127 を解かせる"() {
        setup:
        InputStream stream = getClass().getClassLoader().getResourceAsStream("AOJ-1127.in")
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream))

        def line
        ArrayDeque<String> list = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) list.add((String) line)
        ArrayList<Double> answers = new ArrayList<>();
        while (true) {
            int N = Integer.parseInt(list.poll())
            if (N == 0) break;

            double[][] stations = new double[N][4];
            for (int i = 0; i < stations.length; i++) {
                String[] l = list.poll().split(" ")
                for (int j = 0; j < 4; j++) {
                    stations[i][j] = Double.parseDouble(l[j])
                }
            }

            PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
            for (int i = 0; i < N; i++) {
                for (int j = (i + 1); j < N; j++) {
                    double x1 = stations[i][0];
                    double x2 = stations[j][0];
                    double y1 = stations[i][1];
                    double y2 = stations[j][1];
                    double z1 = stations[i][2];
                    double z2 = stations[j][2];
                    double r1 = stations[i][3];
                    double r2 = stations[j][3];

                    double dist = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) + Math.pow((z1 - z2), 2)) - r1 - r2;
                    dist = Math.max(dist, 0);
                    edges.add(new Edge(dist, i, j));
                }
            }

            double ans = 0.0
            UnionFind unionFind = new UnionFind(N)
            int size = N
            while (!edges.isEmpty()) {
                Edge e = edges.poll()
                if (unionFind.isSame(e.to, e.from)) continue
                ans += e.weight

                int p1 = unionFind.partialSizeOf(e.to)
                int p2 = unionFind.partialSizeOf(e.from)

                boolean merged = unionFind.unite(e.to, e.from)
                assert merged

                int p12 = unionFind.partialSizeOf(e.from)
                assert p12 == p1 + p2

                size--
                assert unionFind.size() == size
            }

            answers.add(ans)
        }

        stream = getClass().getClassLoader().getResourceAsStream("AOJ-1127.out")
        reader = new BufferedReader(new InputStreamReader(stream))
        ArrayDeque<String> check = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) check.add((String) line)
        assert answers.size() == check.size()
        for (int i = 0; i < answers.size(); i++) {
            double ans = answers.get(i)
            double checker = Double.parseDouble(check.poll())
            assert Math.abs(ans - checker) < 0.001
        }


    }

    class Edge implements Comparable<Edge> {
        double weight;
        int from, to;

        Edge(double w, int f, int t) {
            this.weight = w;
            this.from = f;
            this.to = t;
        }

        @Override
        public int compareTo(Edge edge) {
            // 昇順
            if (this.weight > edge.weight) {
                return 1;
            } else if (this.weight == edge.weight) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
