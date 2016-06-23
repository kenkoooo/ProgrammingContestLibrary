package structure

import spock.lang.Specification

class MultiSetTest extends Specification {
    def "MultiSet ランダムケース"() {
        setup:
        Random random = new Random()
        MultiSet<Integer> multiSet = new MultiSet<>()
        int N = 100000
        int[] cnt = new int[N]
        1000.times {
            int r = random.nextInt(N)
            if (random.nextInt(2) == 0) {
                // Add
                cnt[r]++;
                multiSet.add(r)
            } else {
                //Remove
                if (cnt[r] > 0) {
                    cnt[r]--
                    multiSet.sub(r)
                }
            }
            assert cnt[r] == multiSet.get(r)
        }
    }

    def "OrderedMultiSet ランダムケース"() {
        setup:
        Random random = new Random()
        OrderedMultiSet<Integer> multiSet = new OrderedMultiSet<>()
        int N = 100000
        int[] cnt = new int[N]
        1000.times {
            int r = random.nextInt(N)
            if (random.nextInt(2) == 0) {
                // Add
                cnt[r]++;
                multiSet.add(r)
            } else {
                //Remove
                if (cnt[r] > 0) {
                    cnt[r]--
                    multiSet.sub(r)
                }
            }
            assert cnt[r] == multiSet.get(r)
        }
    }
}
