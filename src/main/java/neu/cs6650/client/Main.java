package neu.cs6650.client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import neu.cs6650.Configuration;
import neu.cs6650.InputProcessor;
import neu.cs6650.ReportWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class.getName());
//  private static final String INPUT_PATH = "test.txt";
  private static final String INPUT_PATH = "bsds-summer-2021-testdata.txt";
  private static final String OUTPUT_PATH = "/Users/tmarthin/Code/tiffanymarthin/distributed-systems/assignment1/multithreaded-client-p2/output/latencyOutput.csv";
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

    int maxThread = 64;
    BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
//    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1000);

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
//    logger.info("Input processing run time: " + (inputProcessor.getEndTime() - inputProcessor.getStartTime()));
    logger.info("Number of successful requests: " + client.getTotalSuccessfulRequests());
    logger.info("Number of failed requests: " + client.getTotalFailedRequests());
    logger.info("Client run time: " + (endTime - startTime) + " milliseconds");
//    logger.info("Throughput (requests/second): " + ((client.getTotalSuccessfulRequests() + client.getTotalFailedRequests()) * 1000 / (endTime - startTime)));
    logger.info("Throughput (requests/second): " + (Util.requestsPerSecond((client.getTotalSuccessfulRequests() + client.getTotalFailedRequests()), (endTime - startTime))) + "\n");

//    logger.info("*********** Write CSV Output for Latency Records ***********");
    ReportWriter reportWriter = new ReportWriter(OUTPUT_PATH, client.getLatencyList());
    reportWriter.start();
//    logger.info("*********** CSV Writer Output for Latency Records ***********");


    logger.info("*********** Thread Aggregate Statistics ***********");
    logger.info("Mean response time for POSTs (milliseconds): " + Util.meanResponseTime(client.getLatencyList()));
    logger.info("Median response time for POSTs (milliseconds): " + Util.medianResponseTime(client.getLatencyList()));
    logger.info("99th percentile response time for POSTs (milliseconds): " + Util.p99ResponseTime(client.getLatencyList()));
    logger.info("Max response time for POSTs (milliseconds): " + Util.maxResponseTime(client.getLatencyList()));
  }
}
