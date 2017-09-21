import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

class Fenwick2DSuite extends FunSuite with Matchers {
  test("compare to naive 2D sum") {
    val random = new Random(71)
    val n = 20
    val m = 30
    val array = Array.fill[Long](n, m)(0)
    val fenwick2D = new Fenwick2D(n, m)
    for (_ <- 1 to 20) {
      val x = random.nextInt(n)
      val y = random.nextInt(m)
      val v = random.nextInt()

      array(x)(y) = v
      fenwick2D.update(x + 1, y + 1, v)

      for {
        i <- 0 until n
        j <- 0 until m
        k <- i until n
        l <- j until m
      } {
        val expected = (for {
          t <- i to k
          s <- j to l
        } yield array(t)(s)).sum
        val actual = fenwick2D.sum(i + 1, j + 1, k + 1, l + 1)
        actual shouldBe expected
      }
    }
  }
}
