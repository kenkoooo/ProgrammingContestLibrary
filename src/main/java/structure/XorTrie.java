package structure;

import java.util.ArrayList;

/**
 * リストの中でクエリ x に対して xor が最大になるものを返す。
 */
public class XorTrie {
  class Node {
    private int right, left, size;

    Node(int right, int left, int size) {
      this.right = right;
      this.left = left;
      this.size = size;
    }
  }

  private ArrayList<Node> nodes = new ArrayList<>();
  public XorTrie() {
    nodes.add(new Node(-1, -1, 0));
  }

  public void add(int pos, int bit, int mask, int size) {
    nodes.get(pos).size += size;
    if (bit == -1) return;

    if ((mask & (1 << bit)) == 0) {
      // mask の bit ビット目が 0 の時、左の方に入れる

      // 左の子が空ならば、新しくノードを作る
      if (nodes.get(pos).left == -1) {
        nodes.add(new Node(-1, -1, 0));
        nodes.get(pos).left = nodes.size() - 1;
      }

      // 1 つ左側に潜る
      add(nodes.get(pos).left, bit - 1, mask, size);
    } else {
      // mask の bit ビット目が 0 の時、右の方に入れる

      // 右の子が空ならば、新しくノードを作る
      if (nodes.get(pos).right == -1) {
        nodes.add(new Node(-1, -1, 0));
        nodes.get(pos).right = nodes.size() - 1;
      }

      // 1 つ右側に潜る
      add(nodes.get(pos).right, bit - 1, mask, size);
    }
  }

  public int getXorMax(int pos, int bit, int x, int ans) {
    if (bit == -1) return ans;
    if (((1 << bit) & x) == 0) {
          /*
            x の bit ビット目が 0 の時, Trie 木の中に bit ビット目が 1 のものがあれば, それとの Xor を取れば確実に大きい値が作れる。
            bit ビット目が 1 のものを求めて右側に潜ろうとする
           */

      if (nodes.get(pos).right != -1 && nodes.get(nodes.get(pos).right).size > 0) {
            /*
            右側に子がいれば潜る
             */
        ans |= (1 << bit);
        ans = getXorMax(nodes.get(pos).right, bit - 1, x, ans);
      } else {
            /*
            右側に子がいなければしかたがないので左に潜る
             */
        ans = getXorMax(nodes.get(pos).left, bit - 1, x, ans);
      }
    } else {
          /*
          x の bit ビット目が 1 の時, Trie 木の中に bit ビット目が 0 のものがあれば, それとの Xor を取れば確実に大きい値が作れる。
            bit ビット目が 0 のものを求めて左側に潜ろうとする
           */
      if (nodes.get(pos).left != -1 && nodes.get(nodes.get(pos).left).size > 0) {
        ans |= (1 << bit);
        ans = getXorMax(nodes.get(pos).left, bit - 1, x, ans);
      } else {
        ans = getXorMax(nodes.get(pos).right, bit - 1, x, ans);
      }
    }
    return ans;
  }
}