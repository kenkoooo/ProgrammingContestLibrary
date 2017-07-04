package string

import spock.lang.Specification
import structure.RMQ
import utils.TestUtils

class SuffixArrayTest extends Specification {
    def "lowerBound 関数を contains として使う"() {
        setup:
        Random random = new Random()
        ArrayList<String> list = new ArrayList<>()
        int N = 10
        for (int n = 0; n < N; n++) {
            String x = ""
            for (int i = 0; i < N; i++) {
                char c = 'a'
                c += random.nextInt(26)
                x += c
            }
            list.add(x)
        }

        String large = ""
        for (String s : list) large += s

        SuffixArray array = new SuffixArray(large)

        for (String s : list) {
            assert array.lowerBound(s) != -1
        }
        for (int n = 0; n < N; n++) {
            String x = ""
            for (int i = 0; i < N; i++) {
                char c = 'a'
                c += random.nextInt(26)
                x += c
            }
            if (large.indexOf(x) == -1) {
                assert array.lowerBound(x) == -1
            }
        }
    }

    def "lowerBound & upperBound のテスト"() {
        setup:
        Random random = new Random()
        ArrayList<String> list = new ArrayList<>()
        int N = 10
        for (int n = 0; n < N; n++) {
            String x = ""
            for (int i = 0; i < N; i++) {
                char c = 'a'
                c += random.nextInt(26)
                x += c
            }
            list.add(x)
        }

        String large = ""
        for (String s : list) large += s
        large += large

        SuffixArray suffixArray = new SuffixArray(large)

        for (String s : list) {
            int lower = suffixArray.lowerBound(s)
            int upper = suffixArray.upperBound(s)
            int l = suffixArray.sa[lower]
            int u = suffixArray.sa[upper - 1]
            assert lower < upper
            assert Math.abs(l - u) == N * N
            String sub1 = large.subSequence(l, l + s.length())
            String sub2 = large.subSequence(u, u + s.length())
            assert sub1 == s
            assert sub2 == s
        }
    }

    def "abracadabra"() {
        setup:
        SuffixArray array = new SuffixArray("abracadabra")

        assert array.lowerBound("braca") != -1
    }

    def "abcde"() {
        setup:
        SuffixArray array = new SuffixArray("abcdeabcde")
        for (int i = 0; i < 5; i++) {
            char c = 'a'
            c += i
            String s = "" + c
            assert array.lowerBound(s) == i * 2 + 1
            assert array.upperBound(s) == i * 2 + 3
        }
    }

    def "bzbzbbayyazbybyyybbzzyyyyyzzbazbaabbzzzzzbzazbayayzbayyyazyzabbbzzzybbayazyb"() {
        setup:
        String S = "bzbzbbayyazbybyyybbzzyyyyyzzbazbaabbzzzzzbzazbayayzbayyyazyzabbbzzzybbayazyb"
        SuffixArray array = new SuffixArray(S)
        for (int i = 0; i < array.sa.length; i++) {
            int c = array.sa[i]
            println(S.substring(c))
        }
    }

    def "AOJ2644を解かせる2"() {
        setup:
        def inQ = new ArrayDeque<String>(TestUtils.loadResourceFiles("AOJ2644/in", getClass()))
        def outQ = new ArrayDeque<String>(TestUtils.loadResourceFiles("AOJ2644/out", getClass()))

        def testcase = 0
        while (!inQ.isEmpty()) {
            String S = inQ.poll()

            SuffixArray sa = new SuffixArray(S)
            SuffixArray reverseSA = new SuffixArray(new StringBuilder(S).reverse().toString())

            int N = S.length()
            RMQ rmq = new RMQ(N + 1)
            RMQ reverseRMQ = new RMQ(N)
            for (int i = 0; i <= N; i++) {
                rmq.update(i, sa.sa[i])
                reverseRMQ.update(i, reverseSA.sa[i])
            }

            int Q = Integer.parseInt(inQ.poll())
            def ans = new ArrayList<Long>()
            for (int q = 0; q < Q; q++) {
                String x = inQ.poll()
                String y = new StringBuilder(inQ.poll()).reverse().toString()


                int low = sa.lowerBound(x)
                if (low == -1) {
                    ans.add(0)
                    continue
                }
                int up = sa.upperBound(x)

                int reverseLow = reverseSA.lowerBound(y)
                if (reverseLow == -1) {
                    ans.add(0)
                    continue
                }
                int reverseUp = reverseSA.upperBound(y)

                long s = rmq.query(low, up)
                long t = N - reverseRMQ.query(reverseLow, reverseUp)
                if (s + x.length() <= t && s <= t - y.length()) {
                    ans.add(t - s)
                } else {
                    ans.add(0)
                }
            }

            for (long a : ans) {
                assert String.valueOf(a) == outQ.poll()
            }

            println("Test ${testcase}: Accepted")
            testcase++
        }
    }
}
