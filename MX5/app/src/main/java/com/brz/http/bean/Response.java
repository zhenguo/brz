package com.brz.http.bean;

/**
 * Created by macro on 16/5/16.
 */
public class Response {
    private String message;
    private String seq;
    private Cmd cmd;

    public String getMessage() {
        return message;
    }

    public String getSeq() {
        return seq;
    }

    public Cmd getCmd() {
        return cmd;
    }
}
