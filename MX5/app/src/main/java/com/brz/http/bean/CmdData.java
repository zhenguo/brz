package com.brz.http.bean;

import com.brz.programme.Programme;
import com.brz.programme.Theme;
import java.util.List;

/**
 * Created by macro on 16/5/16.
 */
public class CmdData {

    private String termId;
    private String versions;
    private String error;
    private String errorMessage;
    private OTAInfo update;
    private byte[] data;
    private Theme theme;
    private List<Programme> programs;

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

    @Override public String toString() {
        return "termId: " + termId + " error: " + error + " errorMessage: " + errorMessage;
    }

    public List<Programme> getPrograms() {
        return programs;
    }

    public Theme getTheme() {
        return theme;
    }

    public OTAInfo getUpdate() {
        return update;
    }

    public String getVersion() {
        return versions;
    }

    public void setVersion(String version) {
        this.versions = version;
    }
}
