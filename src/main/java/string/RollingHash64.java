package string;

public class RollingHash64 {
  private final long base = 1000000007;
  private long[] hash, pow;
  private int n;

  RollingHash64(String S) {
    n = S.length();
    hash = new long[n + 1];
    pow = new long[n + 1];
    hash[0] = 0;
    pow[0] = 1;
    for (int j = 0; j < n; j++) {
      pow[j + 1] = pow[j] * base;
      hash[j + 1] = (hash[j] * base + S.charAt(j));
    }
  }

  long getHash(int l, int r) {
    return (hash[r] - hash[l] * pow[r - l]);
  }

  boolean match(int l1, int r1, int l2, int r2) {
    return getHash(l1, r1) == getHash(l2, r2);
  }

  boolean match(int l1, int l2, int k) {
    return match(l1, l1 + k, l2, l2 + k);
  }

  int lcp(int i, int j) {
    int l = 0, r = Math.min(n - i, n - j) + 1;
    while (l + 1 < r) {
      int m = (l + r) / 2;
      if (match(i, i + m, j, j + m))
        l = m;
      else
        r = m;
    }
    return l;
  }
}
