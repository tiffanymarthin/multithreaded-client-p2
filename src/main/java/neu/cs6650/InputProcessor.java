package neu.cs6650;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class InputProcessor implements Runnable {
//TODO Add logging and modify exceptions
  private BlockingQueue<String> lineQueue;
  private transient BufferedReader bufferedReader;
  private int consumerMaxThread;

  public InputProcessor(String inputFile, BlockingQueue<String> lineQueue, int consumerMaxThread) {
    this.lineQueue = lineQueue;
    try {
        bufferedReader = new BufferedReader(new FileReader(inputFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.consumerMaxThread = consumerMaxThread;
  }

  @Override
  public void run() {
    String line = null;
    try {
      while ((line = bufferedReader.readLine()) != null) {
        if (line.length() == 0) continue;
        lineQueue.put(line);
        System.out.println("p: " + line);
      }
      for (int i = 0; i < this.consumerMaxThread; i++) {
        lineQueue.put("-1 poison pill");
      }
    } catch (IOException e) {
      System.out.println("IO problem");
    } catch (InterruptedException e) {
      System.out.println("Producer Interrupted");
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
  }
}
