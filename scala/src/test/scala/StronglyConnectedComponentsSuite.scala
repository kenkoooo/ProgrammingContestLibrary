import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class StronglyConnectedComponentsSuite extends FunSuite with Matchers {
  test("solve GRL_3_C") {
    /**
      * validate by solving http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_3_C
      */
    for (testCase <- 1 to 24) {
      val in = Source.fromResource(s"GRL_3_C/$testCase.in").getLines()
      val ans = new ArrayBuffer[String]()

      val (vertexCount, edgeCount) = {
        val tmp = in.next().split(" ").map(_.toInt)
        (tmp(0), tmp(1))
      }
      val graph = (0 until vertexCount).map(_ => new ArrayBuffer[Int]())
      for (_ <- 0 until edgeCount) {
        val tmp = in.next().split(" ").map(_.toInt)
        val from = tmp(0)
        val to = tmp(1)
        graph(from).append(to)
      }

      val components = StronglyConnectedComponents.decompose(graph)

      val queryCount = in.next().toInt
      for (_ <- 0 until queryCount) {
        val tmp = in.next().split(" ").map(_.toInt)
        val vertex1 = tmp(0)
        val vertex2 = tmp(1)
        if (components(vertex1) == components(vertex2)) {
          ans.append("1")
        } else {
          ans.append("0")
        }
      }

      val out = Source.fromResource(s"GRL_3_C/$testCase.out").getLines()
      ans.foreach(_ shouldBe out.next())
    }
  }
}
