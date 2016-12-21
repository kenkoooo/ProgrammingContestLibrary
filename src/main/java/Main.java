import java.util.Iterator;
import java.util.LinkedList;

public class Main {
  public static void main(String[] args) {
    LinkedList<Integer> list = new LinkedList<>();
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }
    System.out.println(list);

    for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
      int x = it.next();
      if (x == 5) it.remove();
    }
    System.out.println(list);

  }
}
