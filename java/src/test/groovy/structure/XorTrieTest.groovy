package structure

import spock.lang.Specification

class XorTrieTest extends Specification {
    def "ナイーブと比較して確認するテスト"() {
        setup:
        Random random = new Random();
        XorTrie trie = new XorTrie();
        MultiHashSet<Integer> set = new MultiHashSet<>();

        when:
        int queryNum = 10000

        then:
        queryNum.times {
            int x = random.nextInt((int) 1e9)
            int type = random.nextInt(3)

            if (type == 0) {
                //追加クエリ
                trie.add(0, 30, x, 1)
                set.add(x)
            }
            if (type == 1 && set.containsKey(x)) {
                //削除クエリ
                trie.add(0, 30, x, -1)
                set.removeOne(x)
            }
            if (type == 2 && !set.isEmpty()) {
                //質問クエリ
                int ans = trie.getXorMax(0, 30, x, 0)
                int max = 0
                for (int s : set.keySet()) {
                    max = Math.max(max, (s ^ x))
                }

                assert ans == max
            }
        }


    }
}
