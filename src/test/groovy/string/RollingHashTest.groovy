package string

import spock.lang.Specification

class RollingHashTest extends Specification {
    def "ランダムに文字列を生成させてナイーブ解と一致すれば true を返す"() {
        setup:
        Random random = new Random()
        100.times {
            ArrayList<String> list = new ArrayList<>();
            int length = 0
            while (length < 200) {
                int l = random.nextInt(100);
                String s = ""
                l.times {
                    char c = 'a'
                    c += random.nextInt(26)
                    s += c
                }
                list.add(s)
                list.add(s)
                length += l * 2
            }
            Collections.shuffle(list)
            def randomString = ""
            list.each {
                randomString += it
            }

            RollingHash rollingHash = new RollingHash(randomString)
            for (int i in 0..<randomString.length()) {
                for (int j in (i + 1)..<randomString.length()) {
                    int lcp = rollingHash.lcp(i, j)
                    int cnt = 0
                    while (j + cnt < randomString.length() && randomString.charAt(i + cnt) == randomString.charAt(j + cnt)) {
                        cnt++;
                    }
                    assert lcp == cnt
                }
            }
        }
    }
}
