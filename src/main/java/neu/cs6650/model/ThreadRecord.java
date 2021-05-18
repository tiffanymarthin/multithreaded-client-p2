package neu.cs6650.model;

public class ThreadRecord {
  private int nSuccessRequest;
  private int nFailedRequest;

  public ThreadRecord(int nSuccessRequest, int nFailedRequest) {
    this.nSuccessRequest = nSuccessRequest;
    this.nFailedRequest = nFailedRequest;
  }

  public int getNSuccessRequest() {
    return nSuccessRequest;
  }

  public int getNFailedRequest() {
    return nFailedRequest;
  }
}
