package structure

import spock.lang.Specification


class TreapTest extends Specification {
    def "ランダムな整数をたくさん挿入して TreeSet と比較する"() {
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

        while (treap.size() > 10) {
            int s = treap.size()
            int pos = random.nextInt(s)
            long key = treap.rank(pos)
            assert treap.contains(key)
            treap.erase(key)
            assert !treap.contains(key)
            assert treap.size() == s - 1
        }

        assert treap.size() != 0
        assert !treap.isEmpty()
        treap.clear()
        assert treap.size() == 0
        assert treap.isEmpty()
    }

    def "TreeSet と速さ比べ"() {
        setup:
        Treap treap = new Treap()
        TreeSet<Integer> set = new TreeSet<>()
        Random random = new Random()

        when:
        int N = 2000000

        long treapTime = 0
        long setTime = 0
        TreeSet<Integer> list = new TreeSet<>()
        for (int i = 0; i < N; i++) {
            int x = random.nextInt()
            list.add(x)
        }

        for (int x : list) {
            setTime -= System.currentTimeMillis()
            set.add(x)
            setTime += System.currentTimeMillis()

            treapTime -= System.currentTimeMillis()
            treap.insert(x)
            treapTime += System.currentTimeMillis()
        }

        println(treapTime / 1000.0)
        println(setTime / 1000.0)

        for (int x : list) {
            setTime -= System.currentTimeMillis()
            set.remove(x)
            setTime += System.currentTimeMillis()

            treapTime -= System.currentTimeMillis()
            treap.erase(x)
            treapTime += System.currentTimeMillis()
        }


        then:
        println(treapTime / 1000.0)
        println(setTime / 1000.0)


    }
}
