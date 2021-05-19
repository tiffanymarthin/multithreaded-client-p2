package neu.cs6650.client;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import neu.cs6650.InputProcessor;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    // Create a BlockingQ
    // Create a file reader -> put in Blocking Q
    // Start the thread pool -> pulling from the Blocking Q
    // Retrieve the results
    // Write to CSV
    // Process Statistics
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);
    String inputFile = "input/test.txt";
    String eof = "-1";

    // One thread to read the input file
    InputProcessor inputProcessor = new InputProcessor(inputFile, blockingQueue);
    new Thread(inputProcessor).start();

    String ipAdd = "localhost";
    String port = "8080";
    String funct = "wordcount";
//    MultithreadedClient client = new MultithreadedClient(3, blockingQueue, ipAdd, port, funct);

//    ExecutorService executor = Executors.newFixedThreadPool(4);
//    CompletionService<ThreadRecord> completionService = new ExecutorCompletionService<>(executor);
//    Consumer consumer = new Consumer(blockingQueue);
//    for (int i = 0; i < 4; i++) {
//      completionService.submit(consumer);
//    }
//
//    int totalSucc = 0, totalFail = 0;
//    try {
//      for (int i = 0; i < 4; i++) {
//        Future f = completionService.take();
//        ThreadRecord tr = (ThreadRecord) f.get();
//        totalSucc += tr.getNSuccessRequest();
//        totalFail += tr.getNFailedRequest();
//      }
//    } catch (InterruptedException e) {
//      Thread.currentThread().interrupt();
//    } catch (ExecutionException | CancellationException e) {
//      System.out.println("ee or cancel e");
//    }
//
//    System.out.println(totalSucc);
//    System.out.println(totalFail);
//    executor.shutdown();

  }
}
