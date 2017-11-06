import org.scalatest.{FunSuite, Matchers}

class CombinationSuite extends FunSuite with Matchers {
  test("compare with small naive solution") {
    val n = 50
    val mod = (1e9 + 7).toInt
    val combination = new Combination(n, mod)
    for (m <- 0 to n) {
      var expected = 1L
      for (i <- 1 to m) {
        expected *= (n - i + 1)
        expected /= i
      }
      expected %= mod
      combination.get(n, m) shouldBe expected
    }
  }
}
