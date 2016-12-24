package com.brz.http.bean;

/**
 * Created by macro on 16/7/20.
 */
public class RequestBody {
  public String getPublishid() {
    return publishid;
  }

  public void setPublishid(String publishid) {
    this.publishid = publishid;
  }

  public static class Builder {
    private RequestBody out = new RequestBody();

    public Builder setTermId(String id) {
      out.setTermId(id);

      return this;
    }

    public Builder setStatus(Status status) {
      out.setStatus(status);

      return this;
    }

    public Builder setCmd(Cmd cmd) {
      out.setCmd(cmd);

      return this;
    }

    public RequestBody build() {
      return out;
    }
  }

  private String termId;
  private Status status;
  private String publishid;
  private Cmd cmd;

  public RequestBody() {

  }

  public RequestBody(String t, Status s, Cmd c) {
    termId = t;
    status = s;
    cmd = c;
  }

  public String getTermId() {
    return termId;
  }

  public void setTermId(String termId) {
    this.termId = termId;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Cmd getCmd() {
    return cmd;
  }

  public void setCmd(Cmd cmd) {
    this.cmd = cmd;
  }
}
