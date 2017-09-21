package graph

import spock.lang.Specification

class PrunedLandmarkLabelingTest extends Specification {
    def "構築チェック"() {
        setup:
        int N = 500
        int INF = 1000
        Random random = new Random()
        int[][] dist = new int[N][N]
        ArrayList<PrunedLandmarkLabeling.Edge>[] G = new ArrayList<PrunedLandmarkLabeling.Edge>[N];
        for (int i = 0; i < N; i++) {
            G[i] = new ArrayList();
            Arrays.fill(dist[i], INF)
        }

        when:
        1000.times {
            int from = random.nextInt(N)
            int to = random.nextInt(N)
            int d = random.nextInt(INF - 2) + 2
            dist[from][to] = d
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) continue
                if (dist[i][j] == INF) continue
                PrunedLandmarkLabeling.Edge edge = new PrunedLandmarkLabeling.Edge(j, dist[i][j])
                G[i].add(edge)
            }
        }

        then:
        PrunedLandmarkLabeling pll = new PrunedLandmarkLabeling(G)
        for (int i = 0; i < N; i++) {
            dist[i][i] = 0
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j])
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (dist[i][j] == INF) continue
                assert dist[i][j] == pll.queryDistance(i, j)
            }
        }
    }
}
