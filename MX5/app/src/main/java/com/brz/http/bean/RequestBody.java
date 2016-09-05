package com.brz.http.bean;

/**
 * Created by macro on 16/7/20.
 */
public class RequestBody {
    private String termId;
    private Status status;
    private Cmd cmd;

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
