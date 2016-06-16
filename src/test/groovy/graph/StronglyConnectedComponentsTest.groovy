package graph

import spock.lang.Specification

class StronglyConnectedComponentsTest extends Specification {
    def "ランダムにグラフを作って到達可能かどうかで判断"() {
        setup:
        Random random = new Random()
        10.times {
            int N = 500
            boolean[][] wf = new boolean[N][N]
            ArrayList<ArrayList<Integer>> g = new ArrayList<>(N)
            for (i in 0..<N) g.add(new ArrayList<Integer>())
            (N * 5).times {
                int from = random.nextInt(N)
                int to = random.nextInt(N)
                g.get(from).add(to)
                wf[from][to] = true
            }

            int[] cmp = StronglyConnectedComponents.decompose(g)
            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (wf[i][k] && wf[k][j]) wf[i][j] = true
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (wf[i][j] && wf[j][i]) {
                        assert cmp[i] == cmp[j]
                    } else if (wf[i][j]) {
                        assert cmp[i] < cmp[j]
                    } else if (wf[j][i]) {
                        assert cmp[j] < cmp[i]
                    }
                }
            }
        }
    }
}
