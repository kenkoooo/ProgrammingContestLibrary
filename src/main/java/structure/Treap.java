package structure;

import java.util.Random;

public class Treap {
  Random random = new Random();

  private class Node {
    Node left, right;
    long key;
    int priority;
    int count;

    Node(long key) {
      this.key = key;
      priority = random.nextInt();
      left = null;
      right = null;
      count = 1;
    }
  }

  private Node root = null;

  public void clear() {
    root = null;
  }

  public boolean isEmpty() {
    return count(root) == 0;
  }

  private int count(Node n) {
    return n == null ? 0 : n.count;
  }

  private void update(Node c) {
    c.count = 1 + count(c.left) + count(c.right);
  }

  private Node leftRotate(Node c) {
    Node r = c.right;
    c.right = r.left;
    r.left = c;
    update(c);
    return r;
  }

  private Node rightRotate(Node c) {
    Node l = c.left;
    c.left = l.right;
    l.right = c;
    update(c);
    return l;
  }

  private Node insert(Node c, long key) {
    if (c == null) return new Node(key);
    if (c.key < key) {
      c.right = insert(c.right, key);
      if (c.right.priority < c.priority) c = leftRotate(c);
    } else {
      c.left = insert(c.left, key);
      if (c.left.priority < c.priority) c = rightRotate(c);
    }
    update(c);
    return c;
  }

  private Node getMinNode(Node c) {
    while (c.left != null) c = c.left;
    return c;
  }

  private Node erase(Node c, long key) {
    if (key == c.key) {
      if (c.left == null) return c.right;
      if (c.right == null) return c.left;

      Node minNode = getMinNode(c.right);
      c.key = minNode.key;
      c.right = erase(c.right, minNode.key);
    } else {
      if (c.key < key) c.right = erase(c.right, key);
      else c.left = erase(c.left, key);
    }
    update(c);
    return c;
  }

  public void insert(long key) {
    if (contains(key)) return;
    root = insert(root, key);
  }

  public void erase(long key) {
    root = erase(root, key);
  }

  public int size() {
    return count(root);
  }

  public boolean contains(long key) {
    return find(root, key) >= 0;
  }

  public int find(long key) {
    return find(root, key);
  }

  private int find(Node c, long key) {
    if (c == null) return -1;
    if (c.key == key) return count(c.left);
    if (key < c.key) return find(c.left, key);
    int pos = find(c.right, key);
    if (pos < 0) return pos;
    return count(c.left) + 1 + pos;
  }

  private Node rank(Node c, int rank) {
    while (c != null) {
      int leftCount = count(c.left);
      if (leftCount == rank) return c;
      if (leftCount < rank) {
        rank -= leftCount + 1;
        c = c.right;
      } else {
        c = c.left;
      }
    }
    return null;
  }

  public long rank(int rank) {
    if (root == null)
      throw new NullPointerException();
    Node r = rank(root, rank);
    if (r == null)
      throw new NullPointerException();
    return r.key;
  }

}
