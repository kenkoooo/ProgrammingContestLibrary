package string;

public class RollingHash {
  private final long mod[] = new long[]{999999937, 1000000009};
  private final long base = 1000000007;
  private long[][] hash, pow;
  private int n;

  RollingHash(String S) {
    n = S.length();
    hash = new long[mod.length][n + 1];
    pow = new long[mod.length][n + 1];
    for (int i = 0; i < mod.length; i++) {
      hash[i][0] = 0;
      pow[i][0] = 1;
      for (int j = 0; j < n; j++) {
        pow[i][j + 1] = pow[i][j] * base % mod[i];
        hash[i][j + 1] = (hash[i][j] * base + S.charAt(j)) % mod[i];
      }
    }
  }

  long getHash(int l, int r, int i) {
    return ((hash[i][r] - hash[i][l] * pow[i][r - l]) % mod[i] + mod[i]) % mod[i];
  }

  boolean match(int l1, int r1, int l2, int r2) {
    boolean ret = true;
    for (int i = 0; i < mod.length; i++)
      ret &= getHash(l1, r1, i) == getHash(l2, r2, i);
    return ret;
  }

  boolean match(int l1, int l2, int k) {
    return match(l1, l1 + k, l2, l2 + k);
  }

  int lcp(int i, int j) {
    int l = 0, r = Math.min(n - i, n - j) + 1;
    while (l + 1 < r) {
      int m = (l + r) / 2;
      if (match(i, j, m))
        l = m;
      else
        r = m;
    }
    return l;
  }
}
