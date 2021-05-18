package neu.cs6650.api;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import neu.cs6650.Configuration;
import neu.cs6650.client.ApiClient;
import neu.cs6650.model.ResultVal;
import neu.cs6650.model.TextLine;
import okhttp3.Call;

public class TextbodyApi {

  private ApiClient apiClient;

//  public TextbodyApi() {
//    this(Configuration.getDefaultApiClient());
//  }

  public TextbodyApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public Call analyzeNewCall(TextLine body, String function) {
    Object localVarPostBody = body;

    // create path and map variables
    final String localVarPath = "/textbody/" + function + "/";
    final String localVarContentType = "application/json; charset=utf-8";

    return apiClient.buildPostCall(localVarPath, localVarPostBody, localVarContentType);
  }

  private Call analyzeValidateNewCall(TextLine body, String function) throws ApiException {
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException("Missing the required parameter 'body'");
    }
    // verify the required parameter 'function' is set
    if (function == null) {
      throw new ApiException("Missing the required parameter 'function'");
    }
    return analyzeNewCall(body, function);
  }

  public ResultVal analyzeNewLine(TextLine body, String function) throws ApiException {
    Call call = analyzeValidateNewCall(body, function);
    Type localVarReturnType = new TypeToken<ResultVal>(){}.getType();
//    ApiResponse<ResultVal> resp = apiClient.execute(call, localVarReturnType);
    return resp.getData();
  }
}
