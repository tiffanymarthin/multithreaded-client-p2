package neu.cs6650;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import neu.cs6650.model.TextLine;
import neu.cs6650.model.ThreadRecord;

public class Consumer implements Callable<ThreadRecord> {

//  private String body;
  private BlockingQueue<String> blockingQueue;
  public Consumer(BlockingQueue<String> b) {
//    this.body = body;
    this.blockingQueue = b;
  }

  @Override
  public ThreadRecord call() {
//    System.out.println("Consumer class");
    int totalSuccessCall = 0, totalFailedCall = 0;
    while (true) {
      try {
        String localVarPostBody = this.blockingQueue.take();
        if (localVarPostBody.equals("-1 poison pill")) {
//          return new ThreadRecord(totalSuccessCall, totalFailedCall);
          break;
        }
        totalSuccessCall++;
        System.out.println("c: " + localVarPostBody);
      } catch (InterruptedException e) {
        totalFailedCall++;
        System.out.println("Consumer Interrupted");
//        Thread.currentThread().interrupt();
        e.printStackTrace();
      }
    }
    // create path and map variables
//    final String localVarPath = "/textbody/" + this.function + "/";
//    final String localVarContentType = "application/json; charset=utf-8";

//    if (localVarPostBody != null) {
//      totalSuccessCall++;
//      System.out.println(localVarPostBody);
//    } else {
//      totalFailedCall++;
//    }
    return new ThreadRecord(totalSuccessCall, totalFailedCall);
  }
}
