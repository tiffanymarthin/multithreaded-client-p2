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

  public InputProcessor(String inputFile, BlockingQueue<String> lineQueue) {
    this.lineQueue = lineQueue;
    try {
        bufferedReader = new BufferedReader(new FileReader(inputFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    String line = null;
    try {
      while ((line = bufferedReader.readLine()) != null) {
        lineQueue.put(line);
        System.out.println("p: " + line);
      }
      for (int i = 0; i < 4; i++) {
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
