package structure;

class StarrySkyTreeMatrix {
  final int N = 1 << 17;
  private Matrix[] seg, segAdd;
  StarrySkyTreeMatrix(Matrix[] ms) {
    seg = new Matrix[N * 2];
    segAdd = new Matrix[N * 2];
    for (int i = 0; i < N * 2; i++) {
      seg[i] = Matrix.zero();
      segAdd[i] = Matrix.one();
    }
    for (int i = 0; i < ms.length; i++) {
      seg[i + N] = ms[i];
    }
    for (int i = N - 1; i > 0; i--) {
      seg[i] = Matrix.addMatrix(seg[i * 2], seg[i * 2 + 1]);
    }
  }

  void add(int a, int b, Matrix v) {
    add(a, b, v, 0, N, 1);
  }

  void add(int a, int b, Matrix v, int l, int r, int k) {
    if (r <= a || b <= l) return;
    if (a <= l && r <= b) {
      segAdd[k] = Matrix.productMatrix(segAdd[k], v);
      return;
    }
    add(a, b, v, l, (l + r) / 2, k * 2);
    add(a, b, v, (l + r) / 2, r, k * 2 + 1);
    seg[k] = Matrix.addMatrix(
      Matrix.productMatrix(seg[k * 2], segAdd[k * 2]),
      Matrix.productMatrix(seg[k * 2 + 1], segAdd[k * 2 + 1])
    );
  }

  Matrix getSum(int a, int b) {
    return getSum(a, b, 0, N, 1);
  }

  Matrix getSum(int a, int b, int l, int r, int k) {
    if (b <= l || r <= a) return Matrix.zero();
    if (a <= l && r <= b) return Matrix.productMatrix(seg[k], segAdd[k]);
    Matrix x = getSum(a, b, l, (l + r) / 2, k * 2);
    Matrix y = getSum(a, b, (l + r) / 2, r, k * 2 + 1);
    return Matrix.productMatrix(segAdd[k], Matrix.addMatrix(x, y));
  }
}

class Matrix {
  long[] mat;
  Matrix(long[] mat) {
    this.mat = mat;
  }

  final static int MOD = (int) 1e9 + 7;
  static Matrix addMatrix(Matrix a, Matrix b) {
    Matrix c = new Matrix(new long[4]);
    for (int i = 0; i < 4; i++) {
      c.mat[i] = a.mat[i] + b.mat[i];
      if (c.mat[i] > MOD) c.mat[i] -= MOD;
    }
    return c;
  }
  static Matrix productMatrix(Matrix a, Matrix b) {
    Matrix c = new Matrix(new long[4]);
    c.mat[0] = (a.mat[0] * b.mat[0] + a.mat[1] * b.mat[2]) % MOD;
    c.mat[1] = (a.mat[0] * b.mat[1] + a.mat[1] * b.mat[3]) % MOD;
    c.mat[2] = (a.mat[2] * b.mat[0] + a.mat[3] * b.mat[2]) % MOD;
    c.mat[3] = (a.mat[2] * b.mat[1] + a.mat[3] * b.mat[3]) % MOD;
    return c;
  }

  static Matrix powMatrix(Matrix a, long p) {
    Matrix ret = new Matrix(new long[]{1, 0, 0, 1});
    while (p > 0) {
      if (p % 2 == 1) ret = productMatrix(ret, a);
      a = productMatrix(a, a);
      p /= 2;
    }
    return ret;
  }

  static Matrix zero() {
    return new Matrix(new long[4]);
  }
  static Matrix one() {
    return new Matrix(new long[]{1, 0, 0, 1});
  }
}