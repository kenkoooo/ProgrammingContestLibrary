package structure;

import java.util.ArrayList;
import java.util.List;

public class LongFibonacciHeap {

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

  /**
   * Add node x to linked root list
   *
   * @param x node to add
   */
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

  /**
   * Remove node x from linked root list
   *
   * @param x node to remove
   */
  private void removeRoot(Node x) {
    Node r = x.right;
    Node l = x.left;
    r.left = l;
    l.right = r;
  }

  /**
   * merge h1 and h2 to generate new one
   *
   * @param h1 heap1 to merge
   * @param h2 heap2 to merge
   * @return merged heap
   */
  public static LongFibonacciHeap unite(LongFibonacciHeap h1, LongFibonacciHeap h2) {
    LongFibonacciHeap h = new LongFibonacciHeap();
    h.min = h1.min;
    if ((h1.min == null) || (h2.min != null && h2.min.key < h1.min.key)) {
      h.min = h2.min;
    }

    // link h1's root list & h2's root list
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

    for (Node child : z.children) {
      addRoot(child);
      child.p = null;
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

  public int size() {
    return size;
  }

  private void consolidate() {
    //list up rooted nodes
    ArrayList<Node> rootList = new ArrayList<>();
    rootList.add(min);
    while (rootList.get(rootList.size() - 1).right != rootList.get(0)) {
      Node lastRight = rootList.get(rootList.size() - 1).right;
      rootList.add(lastRight);
    }

    Node[] rootOfDegree = new Node[size + 1];
    for (Node parent : rootList) {
      int d = parent.degree;
      while (rootOfDegree[d] != null) {
        Node child = rootOfDegree[d];
        if (parent.key > child.key) {
          Node t = parent;
          parent = child;
          child = t;
        }
        link(child, parent);
        rootOfDegree[d] = null;
        d++;
      }
      rootOfDegree[d] = parent;
    }

    min = null;
    for (Node node : rootOfDegree) {
      if (node == null) {
        continue;
      }

      if (min == null) {
        node.right = node;
        node.left = node;
        min = node;
      } else {
        addRoot(node);
        if (node.key < min.key) {
          min = node;
        }
      }
    }
  }

  private void link(Node child, Node parent) {
    removeRoot(child);
    parent.degree++;
    parent.children.add(child);
    child.p = parent;
    child.mark = false;
  }

  public void insert(long key) {
    Node x = new Node();
    x.key = key;
    insert(x);
  }
}