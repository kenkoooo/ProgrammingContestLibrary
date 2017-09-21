/**
  * 1-indexed 2-dimensional Fenwick's tree
  *
  * @param h height
  * @param w width
  */
class Fenwick2D(h: Int, w: Int) {
  val N: Int = h + 1
  val M: Int = w + 1
  val data: Array[Array[Long]] = Array.fill[Long](N + 1, M + 1)(0)

  def add(x: Int, y: Int, v: Long): Unit = {
    var i = x
    while (i <= N) {
      var j = y
      while (j <= M) {
        data(i)(j) += v
        j += j & -j
      }
      i += i & -i
    }
  }

  def update(x: Int, y: Int, v: Long): Unit = add(x, y, v - sum(x, y, x, y))

  def get(x: Int, y: Int): Long = sum(x, y, x, y)

  def sum(x0: Int, y0: Int, x1: Int, y1: Int): Long = {

    def partialSum(x: Int, y: Int): Long = {
      var res = 0L
      var i = x
      while (i > 0) {
        var j = y
        while (j > 0) {
          res += data(i)(j)
          j -= j & -j
        }
        i -= i & -i
      }
      res
    }

    partialSum(x1, y1) - partialSum(x0 - 1, y1) - partialSum(x1, y0 - 1) + partialSum(x0 - 1, y0 - 1)
  }
}