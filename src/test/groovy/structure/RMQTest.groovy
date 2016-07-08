package structure

import spock.lang.Specification

class RMQTest extends Specification {
    def "ランダムな配列でナイーブと比較する"() {
        setup:
        Random random = new Random()
        100.times {
            int N = 500
            long[] array = new long[N]
            for (int i = 0; i < array.length; i++) array[i] = random.nextInt()

            RMQ rmq = new RMQ(array)
            for (int i = 0; i < N; i++) {
                long min = Long.MAX_VALUE
                for (int j = i; j < N; j++) {
                    for (int k = i; k <= j; k++) {
                        min = Math.min(min, array[j])
                    }
                    assert min == rmq.query(i, j + 1)
                }
            }
        }
    }
}
