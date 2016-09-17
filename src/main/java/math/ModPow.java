package math;

public class ModPow {
  public static long modPow(long x, long e, long MOD) {
    long ret = 1;
    long cur = x;
    while (e > 0) {
      if ((e & 1) != 0) ret = (ret * cur) % MOD;
      cur = (cur * cur) % MOD;
      e /= 2;
    }
    return ret;
  }
}
