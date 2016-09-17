package math

import spock.lang.Specification

class ModPowTest extends Specification {
    def "BigInteger の ModPow と比較するテスト"() {
        setup:
        int MOD = (int) 1e9 + 7
        BigInteger mod = BigInteger.valueOf(MOD)
        Random random = new Random()

        1000.times {
            long x = random.nextInt(MOD)
            long e = random.nextInt(MOD)
            long p = ModPow.modPow(x, e, MOD)
            BigInteger X = BigInteger.valueOf(x)
            BigInteger E = BigInteger.valueOf(e)
            BigInteger check = X.modPow(E, mod)
            assert check.longValue() == p
        }
    }
}
