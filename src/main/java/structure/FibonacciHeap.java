package structure;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap {

  class Node {

    long key = -1;
    int degree = 0;
    Node p = null;
    Node right = null;
    Node left = null;
    List<Node> children = new ArrayList<>();
    boolean mark = false;
  }

  private Node min = null;
  private int size = 0;

  public void insert(Node x) {
    if (min == null) {
      min = x;
      x.right = x;
      x.left = x;
    } else {
      addRoot(x);
      if (x.key < min.key) {
        min = x;
      }
    }
    size++;
  }

  private void addRoot(Node x) {
    if (min == null) {
      min = x;
      min.right = x;
      min.left = x;
      return;
    }

    Node l = min.left;
    l.right = x;
    min.left = x;

    x.right = min;
    x.left = l;
  }

  private void removeRoot(Node x) {
    Node r = x.right;
    Node l = x.left;
    r.left = l;
    l.right = r;
  }

  public static FibonacciHeap unite(FibonacciHeap h1, FibonacciHeap h2) {
    FibonacciHeap h = new FibonacciHeap();
    h.min = h1.min;
    if ((h1.min == null) || (h2.min != null && h2.min.key < h1.min.key)) {
      h.min = h2.min;
    }

    if (h1.min != null && h2.min != null) {
      Node n1 = h1.min;
      Node n2 = h2.min;
      Node n1l = n1.left;
      Node n2r = n2.right;

      n1.left = n2;
      n2.right = n1;
      n2r.left = n1l;
      n1l.right = n2r;
    }

    h.size = h1.size + h2.size;
    return h;
  }

  public Node extractMinNode() {
    Node z = min;
    if (z == null) {
      return null;
    }

    for (Node x : z.children) {
      addRoot(x);
      x.p = null;
    }
    removeRoot(z);
    if (z == z.right) {
      min = null;
    } else {
      min = z.right;
      consolidate();
    }
    size--;
    return z;
  }

  private void consolidate() {
    Node[] A = new Node[size + 1];
    ArrayList<Node> rootList = new ArrayList<>();
    rootList.add(min);
    while (rootList.get(rootList.size() - 1).right != rootList.get(0)) {
      Node lastRight = rootList.get(rootList.size() - 1).right;
      rootList.add(lastRight);
    }

    for (Node x : rootList) {
      int d = x.degree;
      while (A[d] != null) {
        Node y = A[d];
        if (x.key > y.key) {
          Node t = x;
          x = y;
          y = t;
        }
        link(y, x);
        A[d] = null;
        d++;
      }
      A[d] = x;

      x = x.right;
    }
    min = null;
    for (Node a : A) {
      if (a != null) {
        if (min == null) {
          a.right = a;
          a.left = a;
          min = a;
        } else {
          addRoot(a);
          if (a.key < min.key) {
            min = a;
          }
        }
      }
    }
  }

  private void link(Node y, Node x) {
    removeRoot(y);
    x.degree++;
    x.children.add(y);
    y.p = x;
    y.mark = false;
  }

  public void insert(long key) {
    Node x = new Node();
    x.key = key;
    insert(x);
  }
}