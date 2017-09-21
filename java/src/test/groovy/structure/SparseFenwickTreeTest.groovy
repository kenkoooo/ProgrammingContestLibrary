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

    def "2 次元 BIT として使う"() {
        setup:
        Random random = new Random()
        10.times {
            int N = 200
            int[] data = new int[N]
            TreeSet<Integer> set = new TreeSet<>()
            HashMap<Integer, SparseFenwickTree> bits = new HashMap<>()
            for (int i = 0; i < N; i++) {
                data[i] = random.nextInt(N)
                set.add(data[i])

                SparseFenwickTree bit = bits.get(data[i])
                if (bit == null) bits.put(data[i], bit = new SparseFenwickTree(N))
                bit.add(i, 1)
            }

            for (int s : set) {
                SparseFenwickTree bit = bits.get(s)
                assert bit != null
                for (int i = 0; i < N; i++) {
                    for (int j = i + 1; j <= N; j++) {
                        int ans = bit.sum(i, j)
                        int check = 0
                        for (int k = i; k < j; k++) {
                            if (data[k] == s) check++;
                        }
                        assert ans == check
                    }
                }
            }
        }
    }

}
