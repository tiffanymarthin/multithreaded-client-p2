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

    String ipAdd = "localhost";
    String port = "8080";
    String funct = "wordcount";
    int maxThread = 100;

    // One thread to read the input file
    InputProcessor inputProcessor = new InputProcessor(inputFile, blockingQueue, maxThread);
    new Thread(inputProcessor).start();
    MultithreadedClient client = new MultithreadedClient(maxThread, blockingQueue, ipAdd, port, funct);
    client.start();

    System.out.println(client.getTotalSuccessfulRequests());
    System.out.println(client.getTotalFailedRequests());
  }
}
