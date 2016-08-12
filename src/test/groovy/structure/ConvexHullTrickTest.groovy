package structure

import spock.lang.Specification

class ConvexHullTrickTest extends Specification {
    def "ナイーブな方法と比較する"() {
        setup:
        ConvexHullTrick deq = new ConvexHullTrick();
        Random random = new Random();
        ArrayList<long[]> list = new ArrayList<>();

        int addQuery = 1000
        int checkQuery = 10

        long a = random.nextInt((int) 1e6)
        addQuery.times {
            a += random.nextInt((int) 1e6)
            long b = random.nextInt((int) 1e6)
            deq.add(a, b)
            long[] ab = new long[2]
            ab[0] = a
            ab[1] = b
            list.add(ab)
            for (int i = 0; i < checkQuery; i++) {
                long x = random.nextInt((int) 1e6)
                long m = Long.MAX_VALUE
                int k = 0
                for (int j = 0; j < list.size(); j++) {
                    long[] l = list.get(j)
                    long y = l[0] * x + l[1]
                    if (m > y) {
                        m = y
                        k = j
                    }
                }

                long q = deq.query(x)
                if (q != m) {
                    println(list.get(k))
                }
                assert deq.query(x) == m
            }

        }
    }
}
