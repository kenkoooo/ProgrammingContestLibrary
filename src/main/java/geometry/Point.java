package geometry;

class Point implements Comparable<Point> {
  double x, y;
  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int compareTo(Point o) {
    if (this.x == o.x) return Double.compare(this.y, o.y);
    return Double.compare(this.x, o.x);
  }

  /**
   * 外戚を計算して返す
   *
   * @param p
   * @return
   */
  public double det(Point p) {
    return x * p.y - y * p.x;
  }

  /**
   * 内積
   *
   * @param p
   * @return
   */
  public double dot(Point p) {
    return x * p.x + y * p.y;
  }

  public Point sub(Point p) {
    return new Point(x - p.x, y - p.y);
  }

  public Point add(Point p) {
    return new Point(x + p.x, y + p.y);
  }

  public Point multiply(double d) {
    return new Point(x * d, y * d);
  }

}
