package neu.cs6650.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import neu.cs6650.model.ThreadInput;
import neu.cs6650.model.ThreadRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultithreadedClient {
  private static final Logger logger = LogManager.getLogger(ApiClient.class.getName());

  private Integer maxThreads;
  private BlockingQueue<String> lineQueue;

  private String ipAddress;
  private String port;
  private String function;
  private String poisonPill;

  private long totalSuccessfulRequests;
  private long totalFailedRequests;

  private ExecutorService executor;
  private CompletionService<ThreadRecord> completionService;

  private long endTime;

  public MultithreadedClient(Integer maxThreads,
      BlockingQueue<String> textInput, String ipAddress, String port, String function, String poisonPill) {
    this.maxThreads = maxThreads;
    this.lineQueue = textInput;
    this.ipAddress = ipAddress;
    this.port = port;
    this.function = function;
    this.poisonPill = poisonPill;
    this.executor = Executors.newFixedThreadPool(maxThreads);
    this.completionService = new ExecutorCompletionService<>(this.executor);
  }

  public void start() throws InterruptedException {
    submitThreads();
    updateRequestResults();
    shutdownExecutor();
  }

  private void submitThreads() {
    ThreadInput threadInput = new ThreadInput(this.ipAddress, this.port);
    //TODO check here, create new apiClient
    ApiClient apiClient = new ApiClient(lineQueue, threadInput, function, poisonPill);
    for (int i = 0; i < maxThreads; i++) {
      completionService.submit(apiClient);
    }
  }

  private void updateRequestResults() {
    try {
      for (int i = 0; i < this.maxThreads; i++) {
        Future<ThreadRecord> f = completionService.take();
        ThreadRecord record = f.get();
        totalSuccessfulRequests += record.getNSuccessRequest();
        totalFailedRequests += record.getNFailedRequest();
      }
    } catch (InterruptedException e) {
      logger.info("Thread interrupted");
      Thread.currentThread().interrupt();
    } catch (ExecutionException | CancellationException e) {
      logger.info(e.getMessage());
    }
  }

  private void shutdownExecutor() {
    try {
      int tries = 0;
      executor.shutdown();
      while (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow();
        if (++tries > 3) {
          logger.fatal("Unable to shutdown thread executor. Calling System.exit()...");
          System.exit(0);
        }
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  public long getTotalSuccessfulRequests() {
    return totalSuccessfulRequests;
  }

  public long getTotalFailedRequests() {
    return totalFailedRequests;
  }

  public long getEndTime() {
    return endTime;
  }
}

