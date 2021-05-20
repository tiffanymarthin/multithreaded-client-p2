package neu.cs6650;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputProcessor implements Runnable {
  private static final Logger logger = LogManager.getLogger(InputProcessor.class.getName());

  private BlockingQueue<String> lineQueue;
  private transient BufferedReader bufferedReader;
  private int consumerMaxThread;
  private String poisonPill;

  public InputProcessor(String inputFile, BlockingQueue<String> lineQueue, int consumerMaxThread, String poisonPill) {
    this.lineQueue = lineQueue;
//    try {
//        bufferedReader = new BufferedReader(new FileReader(inputFile));
        try {
          InputStream inputStream = this.getClass().getResourceAsStream("/" + inputFile);
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
          bufferedReader = new BufferedReader(inputStreamReader);
        }
        catch (NullPointerException e) {
          logger.info("File not found");
        };
//    } catch (FileNotFoundException e) {
//      logger.fatal(e.getMessage());
//    }
    this.consumerMaxThread = consumerMaxThread;
    this.poisonPill = poisonPill;
  }

  @Override
  public void run() {
    String line = null;
    try {
      while ((line = bufferedReader.readLine()) != null) {
        if (line.length() == 0) continue;
        lineQueue.put(line);
      }
      for (int i = 0; i < this.consumerMaxThread; i++) {
        lineQueue.put(poisonPill);
      }
      logger.info("*********** File Processing Ends ***********");
    } catch (IOException e) {
      logger.info(e.getMessage());
    } catch (InterruptedException e) {
      logger.info("Thread interrupted");
      Thread.currentThread().interrupt();
    }
  }
}
