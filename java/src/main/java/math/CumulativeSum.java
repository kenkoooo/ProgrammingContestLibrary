package math;

public class CumulativeSum {
  int nx, ny;
  long[][] sum;

  CumulativeSum(final long[][] a) {
    ny = a.length;
    nx = a[0].length;
    sum = new long[ny + 1][nx + 1];
    for (int i = 0; i < ny; i++)
      for (int j = 0; j < nx; j++) sum[i + 1][j + 1] = a[i][j] + sum[i][j + 1] + sum[i + 1][j] - sum[i][j];
  }

  long getSum(int y1, int x1, int y2, int x2) {
    if (y1 > y2 || x1 > x2) return 0;
    y1 = Math.max(y1, 0);
    x1 = Math.max(x1, 0);
    y2 = Math.min(y2, ny - 1);
    x2 = Math.min(x2, nx - 1);
    return sum[y2 + 1][x2 + 1] - sum[y1][x2 + 1] - sum[y2 + 1][x1] + sum[y1][x1];
  }
}
