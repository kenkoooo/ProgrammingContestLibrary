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

    def "abracadabra"() {
        setup:
        SuffixArray array = new SuffixArray("abracadabra")

        assert array.lowerBound("braca") != -1
    }
}
