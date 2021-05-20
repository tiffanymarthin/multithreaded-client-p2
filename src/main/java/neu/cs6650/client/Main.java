package neu.cs6650.client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import neu.cs6650.Configuration;
import neu.cs6650.InputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class.getName());
//  private static final String INPUT_PATH = "test.txt";
  private static final String INPUT_PATH = "bsds-summer-2021-testdata.txt";
  private static final String POISON_PILL = "-1";
  private static final String AWS_API_ROUTE = "ec2-3-84-197-20.compute-1.amazonaws.com";

  public static void main(String[] args) throws IOException, InterruptedException {
    // Create a BlockingQ
    // Create a file reader -> put in Blocking Q
    // Start the thread pool -> pulling from the Blocking Q
    // Retrieve the results
    // Write to CSV
    // Process Statistics

    String ipAddress = Configuration.IS_LOCAL ? "localhost" : AWS_API_ROUTE;
    String port = "8080";
    String function = "wordcount";

    int maxThread = 32;
    BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    long startTime, endTime;

    // Command line arguments for easier tests
    //TODO validates the inputs
    if (args.length != 0) {
      ipAddress = args[0];
      port = args[1];
      function = args[2];
      maxThread = Integer.parseInt(args[3]);
    }

    logger.info("*********** Parameters ***********");
    logger.info("Number of threads: " + maxThread);

    logger.info("*********** Input Processor Starts ***********");
    InputProcessor inputProcessor = new InputProcessor(INPUT_PATH, blockingQueue, maxThread, POISON_PILL);
    new Thread(inputProcessor).start();

    logger.info("*********** Client Starts ***********");
    MultithreadedClient client = new MultithreadedClient(maxThread, blockingQueue, ipAddress, port, function, POISON_PILL);
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
