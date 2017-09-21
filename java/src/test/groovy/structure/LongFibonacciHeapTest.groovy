package structure

import spock.lang.Specification

class LongFibonacciHeapTest extends Specification {
    def "random test"() {
        given:
        LongFibonacciHeap heap = new LongFibonacciHeap()
        PriorityQueue<Long> queue = new PriorityQueue<>()
        Random random = new Random()

        10000.times {
            if (random.nextBoolean() && queue.size() > 0) {
                long q = queue.poll()
                long h = heap.extractMinNode().key
                assert q == h
            } else {
                def r = random.nextInt()
                queue.add(r)
                heap.insert(r)
            }
        }
    }

    def "merge test"() {
        given:
        LongFibonacciHeap heap1 = new LongFibonacciHeap()
        LongFibonacciHeap heap2 = new LongFibonacciHeap()
        PriorityQueue<Long> queue = new PriorityQueue<>()
        Random random = new Random()

        10000.times {
            def r = random.nextInt()
            queue.add(r)
            if (random.nextBoolean()) {
                heap1.insert(r)
            } else {
                heap2.insert(r)
            }
        }

        LongFibonacciHeap heap = LongFibonacciHeap.unite(heap1, heap2)
        while (!queue.isEmpty()) {
            def q = queue.poll()
            def h = heap.extractMinNode().key
            assert q == h
        }
    }
}
