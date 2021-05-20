package neu.cs6650.client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import neu.cs6650.model.ThreadInput;
import neu.cs6650.model.ThreadRecord;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiClient implements Callable<ThreadRecord> {

  private static final Logger logger = LogManager.getLogger(ApiClient.class.getName());

  private BlockingQueue<String> lineQueue;
  private ThreadInput threadInput;
  private String apiRoute;
  private String function;
  private String poisonPill;

  private final static String WEB_APP = "java-servlet";
  private final static String API_PATH = "textbody";
  private final static String CONTENT_TYPE = "application/json; charset=utf-8";


  private final OkHttpClient client = new OkHttpClient();

  public ApiClient(BlockingQueue<String> lineQueue, ThreadInput threadInput, String function,
      String poisonPill) {
    this.lineQueue = lineQueue;
    this.threadInput = threadInput;
    this.apiRoute =
        "http://" + this.threadInput.getIpAddress() + ":" + this.threadInput.getPort() + "/"
            + WEB_APP;
    this.function = function;
    this.poisonPill = poisonPill;
  }

  @Override
  public ThreadRecord call() {
    int totalSuccessCall = 0, totalFailedCall = 0;
    String localVarPostBody = null;

    // create path and map variables
    final String localVarPath = "/" + API_PATH + "/" + this.function + "/";

    while (true) {
      try {
        localVarPostBody = this.lineQueue.take();
        if (localVarPostBody.equals(this.poisonPill)) {
          break;
        } else {
          if (postRequest(localVarPath, localVarPostBody, CONTENT_TYPE)) {
            totalSuccessCall++;
          } else {
            totalFailedCall++;
          }
        }
      } catch (InterruptedException e) {
        logger.info("Thread interrupted");
        Thread.currentThread().interrupt();
      }
    }
    return new ThreadRecord(totalSuccessCall, totalFailedCall);
  }

  public boolean postRequest(String path, String postBody, String contentType) {
    Request request = buildPostCall(path, postBody, contentType);
//    System.out.println(Thread.currentThread());
    try (Response response = client.newCall(request).execute()) {
      return response.code() == 200;
    } catch (IOException e) {
      logger.info(e.getMessage());
      return false;
    }
  }

  private Request buildPostCall(String path, String postBody, String contentType) {
    String url = buildUrl(path);
    RequestBody reqBody = RequestBody.create(postBody, MediaType.parse(contentType));
    return new Builder()
        .url(url)
        .post(reqBody)
        .build();
  }

  private String buildUrl(String path) {
    return this.apiRoute + path;
  }

}