
/**
  * calculate nCm modulo mod
  *
  * @param max max number of n
  * @param mod modulo
  */
class Combination(max: Int, mod: Int) {
  private val inv = new Array[Long](max + 1)
  private val fact = new Array[Long](max + 1)
  private val invFact = new Array[Long](max + 1)
  inv(1) = 1
  for (i <- 2 to max) inv(i) = inv(mod % i) * (mod - mod / i) % mod
  fact(0) = 1
  invFact(0) = 1
  for (i <- 1 to max) fact(i) = (fact(i - 1) * i) % mod
  for (i <- 1 to max) invFact(i) = (invFact(i - 1) * inv(i)) % mod

  /**
    * get nCm
    */
  def get(n: Int, m: Int): Int = {
    fact(n) * invFact(m) % mod * invFact(n - m) % mod
  }.toInt
}
