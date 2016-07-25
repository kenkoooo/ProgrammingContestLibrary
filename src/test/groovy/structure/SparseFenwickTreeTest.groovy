package structure

import spock.lang.Specification

class SparseFenwickTreeTest extends Specification {
    def "ランダムなデータとナイーブを比較する"() {
        setup:
        Random random = new Random()
        100.times {
            int N = 200
            SparseFenwickTree tree = new SparseFenwickTree(N)
            int[] data = new int[N]
            for (int i = 0; i < N; i++) {
                data[i] = random.nextInt((int) 1e6)
                tree.add(i, data[i])
            }
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j <= N; j++) {
                    long ans = tree.sum(i, j) // sum of [i,j)
                    long check = 0
                    for (int k = i; k < j; k++) {
                        check += data[k]
                    }
                    assert check == ans
                }
            }
        }
    }

}
