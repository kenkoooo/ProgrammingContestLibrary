import scala.collection.mutable.ArrayBuffer

object StronglyConnectedComponents {
  def decompose(graph: IndexedSeq[Seq[Int]]): Array[Int] = {
    val vs = new ArrayBuffer[Int]()
    val vertexCount = graph.size
    val components = new Array[Int](vertexCount)

    val reversedGraph = graph.indices.map(_ => new ArrayBuffer[Int]())
    for {
      from <- graph.indices
      to <- graph(from)
    } reversedGraph(to).append(from)

    var used = Array.fill[Boolean](vertexCount)(false)
    var stack = List[Int]()
    val added = Array.fill[Boolean](vertexCount)(false)
    for {
      i <- used.indices
      if !used(i)
    } {
      stack = i :: stack
      while (stack.nonEmpty) {
        val v = stack.head
        used(v) = true
        var pushed = false
        for {
          u <- graph(v).reverse
          if !used(u)
        } {
          stack = u :: stack
          pushed = true
        }
        if (!pushed) {
          stack = stack.tail
          if (!added(v)) {
            vs.append(v)
            added(v) = true
          }
        }
      }
    }

    used = Array.fill[Boolean](vertexCount)(false)
    var componentId = 0
    for {
      i <- vs.reverse
      if !used(i)
    } {
      stack = i :: stack
      used(i) = true
      components(i) = componentId
      while (stack.nonEmpty) {
        val v = stack.head
        var pushed = false
        for {
          u <- reversedGraph(v)
          if !used(u)
        } {
          used(u) = true
          components(u) = componentId
          stack = u :: stack
          pushed = true
        }
        if (!pushed) {
          stack = stack.tail
        }
      }
      componentId += 1
    }
    components
  }
}
