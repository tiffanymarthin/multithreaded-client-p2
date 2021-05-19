package neu.cs6650.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import neu.cs6650.api.TextbodyApi;
import neu.cs6650.model.TextLine;
import neu.cs6650.model.ThreadInput;
import neu.cs6650.model.ThreadRecord;

public class MultithreadedClient {
  private Integer maxThreads;
  private BlockingQueue<String> textInput;

  private String ipAddress;
  private String port;
  private String function;

  private ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
  private CompletionService<ThreadRecord> completionService = new ExecutorCompletionService<>(executor);

  public MultithreadedClient(Integer maxThreads,
      BlockingQueue<String> textInput, String ipAddress, String port, String function) {
    this.maxThreads = maxThreads;
    this.textInput = textInput;
    this.ipAddress = ipAddress;
    this.port = port;
    this.function = function;
  }

  private void createThreads() throws InterruptedException {
    ThreadInput threadInput = new ThreadInput(this.ipAddress, this.port);
    for (int i = 0; i < maxThreads; i++) {
      TextLine body = new TextLine();
      body.message(this.textInput.take());
      if (!body.getMessage().equals("-1") || body.getMessage() != null) {
        completionService.submit(new ApiClient(threadInput, body, this.function));
      } else {
        break;
      }
    }
  }
}
