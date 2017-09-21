package graph

import spock.lang.Specification

class LCATest extends Specification {
    def "ランダムにツリーを作って最短経路を求める"() {
        setup:
        Random random = new Random()
        10.times {
            int N = random.nextInt(300) + 10
            int[][] wf = new int[N][N];
            for (int i = 0; i < N; i++) {
                Arrays.fill(wf[i], N * N);
                wf[i][i] = 0
            }

            ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < N; i++) adj.add(new ArrayList<Integer>());

            for (int i = 1; i <= N - 1; i++) {
                int parent = random.nextInt(i)
                wf[i][parent] = 1
                wf[parent][i] = 1
                adj.get(i).add(parent)
                adj.get(parent).add(i)
            }

            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) wf[i][j] = Math.min(wf[i][j], wf[i][k] + wf[k][j])
            }

            LCA lca = new LCA(adj)
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    assert lca.getLength(i, j) == wf[i][j]
                }
            }


        }
    }
}
