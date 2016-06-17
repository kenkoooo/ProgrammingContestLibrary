package math

import spock.lang.Specification

class CumulativeSumTest extends Specification {
    def "ランダムな配列とナイーブの比較"() {
        setup:
        Random random = new Random();
        100.times {
            int N = 30;
            long[][] array = new long[N][N];
            for (long[] a : array) for (int i = 0; i < a.length; i++) a[i] = random.nextInt()

            CumulativeSum cumulativeSum = new CumulativeSum(array)
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int i2 = i; i2 < N; i2++) {
                        for (int j2 = j; j2 < N; j2++) {
                            long sum = 0;
                            for (int k = i; k <= i2; k++) {
                                for (int l = j; l <= j2; l++) {
                                    sum += array[k][l];
                                }
                            }
                            assert cumulativeSum.getSum(i, j, i2, j2) == sum
                        }
                    }
                }
            }
        }
    }
}
