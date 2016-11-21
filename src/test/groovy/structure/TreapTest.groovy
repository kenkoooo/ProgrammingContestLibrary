package structure

import spock.lang.Specification


class TreapTest extends Specification {
    def "ランダムな整数をたくさん挿入して TreeMap と比較する"() {
        setup:
        Treap treap = new Treap()
        TreeSet<Integer> set = new TreeSet<>();
        Random random = new Random()
        int N = 10000

        when:
        for (int i = 0; i < N; i++) {
            int x = random.nextInt()
            treap.insert(x)
            set.add(x)
            assert treap.size() == set.size()
        }

        then:
        int i = 0
        for (int s : set) {
            assert treap.contains(s)
            int pos = treap.find(s)
            assert pos == i
            i++
        }

        while (treap.size() > 0) {
            int s = treap.size()
            int pos = random.nextInt(s)
            long key = treap.rank(pos)
            assert treap.contains(key)
            treap.erase(key)
            assert !treap.contains(key)
            assert treap.size() == s - 1
        }
    }
}
