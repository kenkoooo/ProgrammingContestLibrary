package microbench;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import structure.LongFibonacciHeap;

@State(Scope.Thread)
public class Microbench {

  private static final int TEST_UNIT = 100000;
  private List<Long> list;
  private Random random;
  private PriorityQueue<Long> queue;
  private LongFibonacciHeap heap;

  @Setup
  public void setup() {
    list = new ArrayList<>();
    random = new Random();
    random.setSeed(71);

    queue = new PriorityQueue<>();
    heap = new LongFibonacciHeap();
    for (int i = 0; i < TEST_UNIT; i++) {
      list.add(random.nextLong());
    }
  }

  @Benchmark
  public void fibonacciInsert() {
    heap = new LongFibonacciHeap();
    for (long l : list) {
      heap.insert(l);
    }
  }

  @Benchmark
  public void binaryInsert() {
    queue = new PriorityQueue<>();
    for (long l : list) {
      queue.add(l);
    }
  }

  @Benchmark
  public void fibonacciExtract() {
    heap = new LongFibonacciHeap();
    for (int i = 0; i < TEST_UNIT; i++) {
      if (random.nextBoolean() && heap.size() > 0) {
        heap.extractMinNode();
      } else {
        heap.insert(random.nextLong());
      }
    }
  }

  @Benchmark
  public void binaryExtract() {
    queue = new PriorityQueue<>();
    for (int i = 0; i < TEST_UNIT; i++) {
      if (random.nextBoolean() && queue.size() > 0) {
        queue.poll();
      } else {
        queue.add(random.nextLong());
      }
    }
  }

  public static void main(String[] args) throws RunnerException {
    Options options = new OptionsBuilder()
        .include(Microbench.class.getName())
        .warmupIterations(5)
        .measurementIterations(5)
        .mode(Mode.Throughput)
        .warmupTime(TimeValue.milliseconds(500))
        .measurementTime(TimeValue.milliseconds(500))
        .jvmArgs("-server", "-Xmx1G", "-Xms1G")
        .forks(1)
        .build();
    new Runner(options).run();
  }
}
