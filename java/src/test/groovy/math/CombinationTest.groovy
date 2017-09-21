package math

import spock.lang.Specification

class CombinationTest extends Specification {

    def "ランダム試行"() {
        setup:
        int MOD = (int) 1e9 + 7
        Random random = new Random()


        100.times {
            int N = random.nextInt(100) + 5
            Combination combination = new Combination(N, MOD)

            for (int m = 0; m <= N; m++) {
                long[] up = new long[m]
                for (int i = 0; i < m; i++) {
                    up[i] = N - i
                    long k = i + 1
                    for (int j = 0; j <= i; j++) {
                        long gcd = BigInteger.valueOf(up[j]).gcd(BigInteger.valueOf(k)).longValue()
                        up[j] /= gcd
                        k /= gcd
                    }
                }
                long check = 1L
                for (long u : up) {
                    check *= u
                    check %= MOD
                }

                assert combination.get(N, m) == check
            }

        }
    }
}
