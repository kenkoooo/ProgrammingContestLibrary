package structure

import spock.lang.Specification

class FenwickTreeTest extends Specification {
    def "ランダムなデータとナイーブを比較する"() {
        setup:
        Random random = new Random()
        100.times {
            int N = 200
            FenwickTree tree = new FenwickTree(N)
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

    def "set として使った時に k 番目を取得する"() {
        setup:
        Random random = new Random()
        int N = 200000
        TreeSet<Integer> set = new TreeSet<>();
        FenwickTree tree = new FenwickTree(N)
        1000.times {
            int kind = random.nextInt(2)
            int r = random.nextInt(N)
            switch (kind) {
                case 0:
                    if (!set.contains(r)) {
                        set.add(r)
                        tree.add(r, 1)
                    }
                    break
                case 1:
                    if (set.contains(r)) {
                        set.remove(r)
                        tree.add(r, -1)
                        assert tree.get(r) == 0
                    }
                    break
                default:
                    break
            }
            int k = 0
            for (int s : set) {
                assert s == tree.getAsSetOf(k)
                k++
            }
        }
    }
}
