package neu.cs6650.client;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import neu.cs6650.InputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class.getName());
//  private static final String INPUT_PATH = "input/test.txt";
  private static final String INPUT_PATH = "input/bsds-summer-2021-testdata.txt";
  private static final String POISON_PILL = "-1";
  private static final String IP_ADD = "localhost";
  private static final String PORT = "8080";
  private static final String FUNCTION = "wordcount";

  public static void main(String[] args) throws IOException, InterruptedException {
    // Create a BlockingQ
    // Create a file reader -> put in Blocking Q
    // Start the thread pool -> pulling from the Blocking Q
    // Retrieve the results
    // Write to CSV
    // Process Statistics

    int maxThread = 5;
    int blockingQueueCapacity = 1000;
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(blockingQueueCapacity);

    long startTime, endTime;

    logger.info("*********** Parameters ***********");
    logger.info("Number of threads: " + maxThread);
    logger.info("BlockingQueue capacity: " + blockingQueueCapacity + "\n");

    logger.info("*********** Input Processor Starts ***********");
    InputProcessor inputProcessor = new InputProcessor(INPUT_PATH, blockingQueue, maxThread, POISON_PILL);
    new Thread(inputProcessor).start();

    logger.info("*********** Client Starts ***********");
    MultithreadedClient client = new MultithreadedClient(maxThread, blockingQueue, IP_ADD, PORT, FUNCTION, POISON_PILL);
    startTime = System.currentTimeMillis();
    client.start();
    endTime = System.currentTimeMillis();
    logger.info("*********** Client Ends ***********\n");

    logger.info("*********** Processing Statistics ***********");
    logger.info("Number of successful requests: " + client.getTotalSuccessfulRequests());
    logger.info("Number of failed requests: " + client.getTotalFailedRequests());
    logger.info("Total run time: " + (endTime - startTime) + " milliseconds");
    logger.info("Throughput (requests/second): " + ((client.getTotalSuccessfulRequests() + client.getTotalFailedRequests()) * 1000 / (endTime - startTime)));
  }
}
