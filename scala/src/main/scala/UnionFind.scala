class UnionFind(n: Int) {
  private val parent = (0 until n).toArray
  private val sizes = Array.fill[Int](n)(1)
  private var _size = n

  def find(x: Int): Int = {
    if (x == parent(x)) {
      x
    } else {
      parent(x) = find(parent(x))
      parent(x)
    }
  }

  def unite(a: Int, b: Int): Boolean = {
    val fa = find(a)
    val fb = find(b)
    if (fa == fb) {
      false
    } else {
      val (x, y) = if (sizes(fa) >= sizes(fb)) {
        (fa, fb)
      } else {
        (fb, fa)
      }

      parent(y) = x
      sizes(x) += sizes(y)
      sizes(y) = 0

      _size -= 1
      true
    }
  }

  def isSame(x: Int, y: Int): Boolean = find(x) == find(y)

  def partialSizeOf(x: Int): Int = sizes(find(x))

  def size(): Int = _size
}
