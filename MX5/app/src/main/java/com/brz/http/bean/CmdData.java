package com.brz.http.bean;

/**
 * Created by macro on 16/5/16.
 */
public class CmdData {

    private String termId;
    private String error;
    private String errorMessage;
    private byte[] data;

    public String getTermId() {
        return termId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }
}
