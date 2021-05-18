package neu.cs6650.model;

public class ThreadInput {

  private String ipAddress;
  private String port;
  private Integer startTime;
  private Integer endTime;
  private Integer numOfTask;

  public ThreadInput(String ipAddress, String port, Integer startTime, Integer endTime,
      Integer numOfTask) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.startTime = startTime;
    this.endTime = endTime;
    this.numOfTask = numOfTask;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public String getPort() {
    return port;
  }

  public Integer getStartTime() {
    return startTime;
  }

  public Integer getEndTime() {
    return endTime;
  }

  public Integer getNumOfTask() {
    return numOfTask;
  }
}