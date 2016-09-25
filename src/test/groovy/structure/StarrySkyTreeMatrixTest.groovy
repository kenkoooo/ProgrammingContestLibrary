package structure

import spock.lang.Specification

class StarrySkyTreeMatrixTest extends Specification {
    def mul(long[] a, long[] b) {
        int MOD = Matrix.MOD
        long[] ret = new long[4]
        ret[0] = (a[0] * b[0] + a[1] * b[2]) % MOD
        ret[1] = (a[0] * b[1] + a[1] * b[3]) % MOD
        ret[2] = (a[2] * b[0] + a[3] * b[2]) % MOD
        ret[3] = (a[2] * b[1] + a[3] * b[3]) % MOD
        return ret
    }

    def fib(long N) {
        if (N <= 1) {
            return N;
        }

        long[] result = [1L, 0L, 0L, 1L]
        long[] matrix = [1L, 1L, 1L, 0L]

        while (N > 0L) {
            if (N % 2 == 1L) result = mul(matrix, result)
            matrix = mul(matrix, matrix)
            N /= 2
        }

        return result[2]
    }


    def "フィボナッチ数の区間和を取る愚直解と比較する"() {
        setup:
        Random random = new Random()
        long[] bf = [1, 1, 1, 0];
        Matrix base = new Matrix(bf)
        int MOD = Matrix.MOD

        10.times {
            int N = 25;
            long[] a = new long[N];
            long[] check = new long[N];
            for (int i = 0; i < N; i++) {
                a[i] = random.nextInt(1000000);
                check[i] = a[i]
            }

            Matrix[] ms = new Matrix[N];
            for (int i = 0; i < N; i++) {
                ms[i] = Matrix.powMatrix(base, a[i] - 1)
            }

            StarrySkyTreeMatrix seg = new StarrySkyTreeMatrix(ms);

            int Q = 100
            for (int q = 0; q < Q; q++) {
                // Add
                int l = 0, r = 0
                while (l >= r) {
                    l = random.nextInt(N)
                    r = random.nextInt(N)
                }
                int v = random.nextInt(100000)
                seg.add(l, r + 1, Matrix.powMatrix(base, v))
                for (int i = l; i <= r; i++) {
                    check[i] += v
                }

                for (int i = 0; i < N; i++) {
                    for (int j = i; j < N; j++) {
                        long sum = 0
                        for (int k = i; k <= j; k++) {
                            sum += fib(check[k])
                        }

                        sum %= MOD
                        assert seg.getSum(i, j + 1).mat[0] == sum
                    }
                }
            }
        }
    }
}
