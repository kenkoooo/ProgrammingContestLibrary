package string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuffixArray {
  String S;
  int N, K;
  ArrayList<Integer> sa = new ArrayList<>();
  int[] rank;
  public SuffixArray(String S) {
    this.S = S;
    build();
  }

  private void build() {
    N = S.length();
    rank = new int[N + 1];
    for (int i = 0; i <= N; i++) {
      sa.add(i);
      rank[i] = i < N ? S.charAt(i) : -1;
    }

    int[] dp = new int[N + 1];
    for (K = 1; K <= N; K *= 2) {
      Collections.sort(sa, new Comparator<Integer>() {
        @Override
        public int compare(Integer i, Integer j) {
          if (compareSA(i, j)) return -1;
          return 1;
        }
      });

      dp[sa.get(0)] = 0;
      for (int i = 1; i <= N; i++) {
        dp[sa.get(i)] = dp[sa.get(i - 1)] + (compareSA(sa.get(i - 1), sa.get(i)) ? 1 : 0);
      }
      for (int i = 0; i <= N; i++) {
        rank[i] = dp[i];
      }
    }
  }

  private boolean compareSA(int i, int j) {
    if (rank[i] != rank[j]) return rank[i] < rank[j];
    int ri = i + K <= N ? rank[i + K] : -1;
    int rj = j + K <= N ? rank[j + K] : -1;
    return ri < rj;
  }

  public int lowerBound(String t) {
    int a = -1, b = S.length() - 1;
    while (b - a > 1) {
      int c = (a + b) / 2;
      String sub = S.substring(sa.get(c), Math.min(t.length() + sa.get(c), S.length()));
      if (sub.compareTo(t) < 0)
        a = c;
      else
        b = c;
    }
    String sub = S.substring(sa.get(b), Math.min(t.length() + sa.get(b), S.length()));
    return sub.compareTo(t) == 0 ? b : -1;
  }

  public int upperBound(String t) {
    int a = -1, b = S.length() - 1;
    while (b - a > 1) {
      int c = (a + b) / 2;
      String sub = S.substring(sa.get(c), Math.min(t.length() + sa.get(c), S.length()));
      if (sub.compareTo(t) <= 0)
        a = c;
      else
        b = c;
    }
    return b;
  }
}