package math;

/**
 * nCm % MOD を求める。O(n)
 */
public class Combination {
  private long[] fact;
  private long[] invFact;
  private int MOD;

  public Combination(int max, int MOD) {
    this.MOD = MOD;
    long[] inv = new long[max + 1];
    fact = new long[max + 1];
    invFact = new long[max + 1];
    inv[1] = 1;
    for (int i = 2; i <= max; i++) inv[i] = inv[MOD % i] * (MOD - MOD / i) % MOD;
    fact[0] = invFact[0] = 1;
    for (int i = 1; i <= max; i++) fact[i] = fact[i - 1] * i % MOD;
    for (int i = 1; i <= max; i++) invFact[i] = invFact[i - 1] * inv[i] % MOD;
  }

  public long get(int x, int y) {
    return fact[x] * invFact[y] % MOD * invFact[x - y] % MOD;
  }
}
