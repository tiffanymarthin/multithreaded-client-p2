package neu.cs6650.client;

import com.google.gson.Gson;
import java.io.File;
import java.util.concurrent.Callable;
import neu.cs6650.api.ApiException;
import neu.cs6650.model.TextLine;
import neu.cs6650.model.ThreadInput;
import neu.cs6650.model.ThreadRecord;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;

public class ApiClient implements Callable<ThreadRecord> {

  private ThreadInput threadInput;
  private String apiRoute;

//  private final static String API_PATH = "/textbody/";

  private final OkHttpClient client = new OkHttpClient();

  public ApiClient(ThreadInput threadInput, String apiRoute) {
    this.threadInput = threadInput;
    this.apiRoute = "http://" + this.threadInput.getIpAddress() + ":" + this.threadInput.getPort();
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

  private Request buildPostCall(String path, Object postBody, String contentType)
      throws ApiException {
    final String url = buildUrl(path);
    final RequestBody reqBody;

    if (isJsonMime(contentType)) {
      String json = postBody.toString();
      reqBody = RequestBody.create(json, MediaType.parse(contentType));
    } else {
      throw new ApiException("Content type \"" + contentType + "\" is not supported");
    }

    final Request reqBuilder = new Builder()
        .url(url)
        .post(reqBody)
        .build();

    return reqBuilder;
  }

  private String buildUrl(String path) {
    return this.apiRoute + path;
  }

  /**
   * Check if the given MIME is a JSON MIME.
   * JSON MIME examples:
   *   application/json
   *   application/json; charset=UTF8
   *   APPLICATION/JSON
   *   application/vnd.company+json
   * "* / *" is also default to JSON
   * @param mime MIME (Multipurpose Internet Mail Extensions)
   * @return True if the given MIME is JSON, false otherwise.
   */
  public boolean isJsonMime(String mime) {
    String jsonMime = "(?i)^(application/json|[^;/ \t]+/[^;/ \t]+[+]json)[ \t]*(;.*)?$";
    return mime != null && (mime.matches(jsonMime) || mime.equals("*/*"));
  }

}





  }

}
