package neu.cs6650.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    // Create a BlockingQ
    // Create a file reader -> put in Blocking Q
    // Start the thread pool -> pulling from the Blocking Q
    // Retrieve the results
    // Write to CSV
    // Process Statistics
    BufferedReader bufferedReader;
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(100);
    String inputFile ="location.txt";
    String eof = "-1";
    bufferedReader = new BufferedReader(new FileReader(inputFile));
    String line = null;
    while ((line = bufferedReader.readLine()) != null) {
      blockingQueue.put(line);
    }
    blockingQueue.put(eof);
  }

}
