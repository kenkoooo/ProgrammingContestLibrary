package string;

import java.util.Arrays;
import java.util.Comparator;

public class SuffixArray {
  String S;
  int N, K;
  Integer[] sa;
  int[] rank;
  public SuffixArray(String S) {
    this.S = S;
    build();
  }

  private void build() {
    N = S.length();
    rank = new int[N + 1];
    sa = new Integer[N + 1];
    for (int i = 0; i <= N; i++) {
      sa[i] = i;
      rank[i] = i < N ? S.charAt(i) : -1;
    }

    int[] tmp = new int[N + 1];
    for (int _k = 1; _k <= N; _k *= 2) {
      final int k = _k;
      Arrays.sort(sa, new Comparator<Integer>() {
        @Override
        public int compare(Integer i, Integer j) {
          return compareNode(i, j, k);
        }
      });
      tmp[sa[0]] = 0;
      for (int i = 1; i <= N; i++) {
        tmp[sa[i]] = tmp[sa[i - 1]] + ((compareNode(sa[i - 1], sa[i], k) < 0) ? 1 : 0);
      }
      for (int i = 0; i <= N; i++) {
        rank[i] = tmp[i];
      }
    }
  }

  private int compareNode(int i, int j, int k) {
    if (rank[i] != rank[j]) {
      return rank[i] - rank[j];
    } else {
      int ri = i + k <= N ? rank[i + k] : -1;
      int rj = j + k <= N ? rank[j + k] : -1;
      return ri - rj;
    }
  }

  public int lowerBound(String t) {
    int a = -1, b = S.length();
    while (b - a > 1) {
      int c = (a + b) / 2;
      String sub = S.substring(sa[c], Math.min(t.length() + sa[c], S.length()));
      if (sub.compareTo(t) < 0)
        a = c;
      else
        b = c;
    }
    String sub = S.substring(sa[b], Math.min(t.length() + sa[b], S.length()));
    return sub.compareTo(t) == 0 ? b : -1;
  }

  public int upperBound(String t) {
    int a = -1, b = S.length() + 1;
    while (b - a > 1) {
      int c = (a + b) / 2;
      String sub = S.substring(sa[c], Math.min(t.length() + sa[c], S.length()));
      if (sub.compareTo(t) <= 0)
        a = c;
      else
        b = c;
    }
    return b;
  }
}