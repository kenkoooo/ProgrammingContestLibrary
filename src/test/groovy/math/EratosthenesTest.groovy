package math

import spock.lang.Specification

class EratosthenesTest extends Specification {
    def "素数列挙"() {
        setup:
        int N = 10000

        when:
        int[] primes = Eratosthenes.getPrimes(N)

        then:
        for (int p : primes) {
            for (int i = 2; i < p; i++) {
                assert p % i != 0
            }
        }
    }
}
