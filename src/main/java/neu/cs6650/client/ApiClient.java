package neu.cs6650.client;

import java.util.concurrent.Callable;
import neu.cs6650.ApiException;
import neu.cs6650.model.TextLine;
import neu.cs6650.model.ThreadInput;
import neu.cs6650.model.ThreadRecord;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WorkerTask implements Callable<ThreadRecord> {
  private ThreadInput threadInput;
  private String apiRoute;

  private final static String API_PATH = "/textbody/";

  private final OkHttpClient client = new OkHttpClient();

  public WorkerTask(ThreadInput threadInput, String apiRoute) {
    this.threadInput = threadInput;
    this.apiRoute = "http://" + this.threadInput.getIpAddress() + ":" + this.threadInput.getPort() + API_PATH;
  }

  /**
   * Computes a result, or throws an exception if unable to do so.
   *
   * @return computed result
   * @throws Exception if unable to compute a result
   */
  @Override
  public ThreadRecord call() throws Exception {
    return null;
  }

  private boolean postRequest(String url);

  private Request buildRequest(String path, );

  private boolean validateLineBeforeCall(TextLine body, String function) throws ApiException {
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException("Missing the required parameter 'body'");
    }
    // verify the required parameter 'function' is set
    if (function == null) {
      throw new ApiException("Missing the required parameter 'function'");
    }

    Call call = analyzeNewLineCall(body, function);
    return call;




  }

}
