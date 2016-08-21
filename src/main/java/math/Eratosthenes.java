package math;

import java.util.ArrayList;

/**
 * エラトステネスの篩
 */
public class Eratosthenes {
  public static int[] getPrimes(int N) {
    ArrayList<Integer> primes = new ArrayList<>();
    boolean[] isComposite = new boolean[N + 1];
    primes.add(2);
    for (int i = 3; i <= N; i += 2)
      if (!isComposite[i]) {
        primes.add(i);
        for (int j = i * 2; j <= N; j += i) {
          isComposite[j] = true;
        }
      }
    int[] array = new int[primes.size()];
    for (int i = 0; i < primes.size(); i++) {
      array[i] = primes.get(i);
    }
    return array;
  }
}
