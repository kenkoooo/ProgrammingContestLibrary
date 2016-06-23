package structure;

import java.util.TreeMap;

public class OrderedMultiSet<T> extends TreeMap<T, Integer> {
  @Override
  public Integer get(Object key) {
    return containsKey(key) ? super.get(key) : 0;
  }
  public void add(T key, int v) {
    put(key, get(key) + v);
  }
  public void add(T key) {
    put(key, get(key) + 1);
  }
  public void sub(T key) {
    if (!containsKey(key)) return;
    final int v = get(key);
    if (v == 1) remove(key);
    else put(key, v - 1);
  }
}