package neu.cs6650.model;

import java.util.List;

public class ThreadRecord {
  private int nSuccessRequest;
  private int nFailedRequest;
  private List<LatencyRecord> latencyList;

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

  public List<LatencyRecord> getLatencyList() {
    return latencyList;
  }
}
