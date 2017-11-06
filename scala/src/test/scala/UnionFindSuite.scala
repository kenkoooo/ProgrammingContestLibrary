import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

class UnionFindSuite extends FunSuite with Matchers {
  test("compare witch naive array solution") {
    val n = 500
    val array = (0 until n).toArray
    val uf = new UnionFind(n)
    for (_ <- 0 until 1000) {
      val a = Random.nextInt(n)
      val b = Random.nextInt(n)

      val same = array(a) == array(b)
      uf.isSame(a, b) shouldBe same
      uf.unite(a, b) shouldBe !same
      val before = array(b)
      for {
        i <- array.indices
        if array(i) == before
      } array(i) = array(a)

      for {
        i <- array.indices
        j <- array.indices
      } {
        uf.isSame(i, j) shouldBe (array(i) == array(j))
      }
    }

  }
}
