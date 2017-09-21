package structure

import spock.lang.Specification

class MultiHashSetTest extends Specification {
    def "MultiHashSet ランダムケース"() {
        setup:
        Random random = new Random()
        MultiHashSet<Integer> multiSet = new MultiHashSet<>()
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
                    multiSet.removeOne(r)
                }
            }
            assert cnt[r] == multiSet.get(r)
        }
    }

    def "MultiTreeSet ランダムケース"() {
        setup:
        Random random = new Random()
        MultiTreeSet<Integer> multiSet = new MultiTreeSet<>()
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
                    multiSet.removeOne(r)
                }
            }
            assert cnt[r] == multiSet.get(r)
        }
    }

    def "removeAll のテスト"() {
        setup:
        MultiHashSet<Integer> multiHashSet = new MultiHashSet()
        MultiTreeSet<Integer> multiTreeSet = new MultiTreeSet()
        Random random = new Random()
        TreeSet<Integer> set = new TreeSet<>()

        when:
        1000.times {
            int a = random.nextInt()
            set.add(a)
            multiHashSet.add(a)
            multiTreeSet.add(a)
        }

        then:
        for (int a : set) {
            assert multiHashSet.containsKey(a)
            assert multiTreeSet.containsKey(a)

            multiHashSet.removeAll(a)
            multiTreeSet.removeAll(a)

            assert !multiHashSet.containsKey(a)
            assert !multiTreeSet.containsKey(a)
        }
    }
}
