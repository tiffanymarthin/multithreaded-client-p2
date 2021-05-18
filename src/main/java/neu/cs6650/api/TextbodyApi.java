package neu.cs6650.api;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import neu.cs6650.Configuration;
import neu.cs6650.client.ApiClient;
import neu.cs6650.model.ResultVal;
import neu.cs6650.model.TextLine;
import neu.cs6650.model.ThreadRecord;
import okhttp3.Call;

public class TextbodyApi implements Callable<ThreadRecord> {

  private ApiClient apiClient;
  private TextLine body;
  private String function;

//  public TextbodyApi() {
//    this(Configuration.getDefaultApiClient());
//  }

  public TextbodyApi(ApiClient apiClient, TextLine body, String function) {
    this.apiClient = apiClient;
    this.body = body;
    this.function = function;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public TextLine getBody() {
    return body;
  }

  public String getFunction() {
    return function;
  }

  @Override
  public ThreadRecord call() throws ApiException {
    // verify the required parameter 'body' is set
    if (this.body == null) {
      throw new ApiException("Missing the required parameter 'body'");
    }
    // verify the required parameter 'function' is set
    if (this.function == null) {
      throw new ApiException("Missing the required parameter 'function'");
    }

    int totalSuccessCall = 0, totalFailedCall = 0;
    Object localVarPostBody = this.body;

    // create path and map variables
    final String localVarPath = "/textbody/" + this.function + "/";
    final String localVarContentType = "application/json; charset=utf-8";

    if (apiClient.postRequest(localVarPath, localVarPostBody, localVarContentType)) {
      totalSuccessCall++;
    } else {
      totalFailedCall++;
    }
    return new ThreadRecord(totalSuccessCall, totalFailedCall);
  }


//  public ResultVal analyzeNewLine() throws ApiException {
//    Call call = analyzeValidateNewCall();
//    Type localVarReturnType = new TypeToken<ResultVal>(){}.getType();
////    ApiResponse<ResultVal> resp = apiClient.execute(call, localVarReturnType);
//    return resp.getData();
//  }
}
