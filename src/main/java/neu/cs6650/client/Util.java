package neu.cs6650.client;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;
import neu.cs6650.model.LatencyRecord;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;


public class Util {
  public static double meanResponseTime(LinkedList<LatencyRecord> latencyList) {
    return latencyList.stream()
        .mapToDouble(LatencyRecord::getLatency)
        .average()
        .orElseThrow(NoSuchElementException::new);
  }

  public static double medianResponseTime(LinkedList<LatencyRecord> latencyList) {
    DoubleStream sortedLatencies = latencyList.stream().mapToDouble(LatencyRecord::getLatency).sorted();
    double median = latencyList.size() % 2 == 0 ?
        sortedLatencies.skip(latencyList.size() / 2 - 1).limit(2).average().getAsDouble() :
        sortedLatencies.skip(latencyList.size() / 2).findFirst().getAsDouble();
    return median;
  }

  public static double requestsPerSecond(int totalRequests, long wallTime) {
    return totalRequests * 1000.0 / wallTime;
  }

  public static double p99ResponseTime(LinkedList<LatencyRecord> latencyList) {
    Percentile p99 = new Percentile();
    DoubleStream sortedLatencies = latencyList.stream().mapToDouble(LatencyRecord::getLatency).sorted();

    return p99.evaluate(sortedLatencies.toArray(), 99);
  }

  public static double maxResponseTime(LinkedList<LatencyRecord> latencyList) {
    return latencyList.stream()
        .mapToDouble(LatencyRecord::getLatency)
        .max()
        .orElseThrow(NoSuchElementException::new);
  }
}
