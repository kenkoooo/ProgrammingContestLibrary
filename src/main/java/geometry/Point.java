package geometry;

class Point implements Comparable<Point> {
  int x, y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int compareTo(Point o) {
    if (this.x == o.x) return this.y - o.y;
    return this.x - o.x;
  }

  /**
   * 外戚を計算して返す
   *
   * @param p
   * @return
   */
  public int det(Point p) {
    return x * p.y - y * p.x;
  }

  /**
   * 内積
   *
   * @param p
   * @return
   */
  public int dot(Point p) {
    return x * p.x + y * p.y;
  }
}
