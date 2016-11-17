package string

import spock.lang.Specification

class SuffixArrayTest extends Specification {
    def "lowerBound 関数を contains として使う"() {
        setup:
        Random random = new Random();
        ArrayList<String> list = new ArrayList<>()
        int N = 10
        for (int n = 0; n < N; n++) {
            String x = ""
            for (int i = 0; i < N; i++) {
                char c = 'a'
                c += random.nextInt(26)
                x += c
            }
            list.add(x);
        }

        String large = ""
        for (String s : list) large += s;

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
        Random random = new Random();
        ArrayList<String> list = new ArrayList<>()
        int N = 10
        for (int n = 0; n < N; n++) {
            String x = ""
            for (int i = 0; i < N; i++) {
                char c = 'a'
                c += random.nextInt(26)
                x += c
            }
            list.add(x);
        }

        String large = ""
        for (String s : list) large += s;
        large += large

        SuffixArray suffixArray = new SuffixArray(large)

        for (String s : list) {
            int lower = suffixArray.lowerBound(s)
            int upper = suffixArray.upperBound(s)
            int l = suffixArray.sa.get(lower)
            int u = suffixArray.sa.get(upper - 1)
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
}
