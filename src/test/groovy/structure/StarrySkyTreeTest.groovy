package structure

import spock.lang.Specification

class StarrySkyTreeTest extends Specification {
    def "ランダム試行"() {
        setup:
        Random random = new Random()
        StarrySkyTree tree = new StarrySkyTree()

        int N = 10000
        long[] array = new long[N]
        for (int i = 0; i < array.length; i++) {
            tree.update(i, 0)
        }

        for (int q = 0; q < 10000; q++) {
            int l = random.nextInt(N)
            int r = random.nextInt(N)
            while (r < l) {
                l = random.nextInt(N)
                r = random.nextInt(N)
            }

            int x = random.nextInt()

            if (random.nextBoolean()) {
                // Add
                tree.add(l, r + 1, x)
                for (int i = l; i <= r; i++) {
                    array[i] += x
                }
            } else {
                long min = Long.MAX_VALUE
                long max = -Long.MAX_VALUE
                for (int i = l; i <= r; i++) {
                    min = Math.min(min, array[i])
                    max = Math.max(max, array[i])
                }
                assert min == tree.getMin(l, r + 1)
                assert max == tree.getMax(l, r + 1)
            }
        }
    }
}
