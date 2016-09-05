package com.brz.http.bean;

/**
 * Created by macro on 16/5/16.
 */
public class Cmd {
    private String cmdType;
    private CmdData cmdData;

    public String getCmdType() {
        return cmdType;
    }

    public CmdData getCmdData() {
        return cmdData;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public void setCmdData(CmdData cmdData) {
        this.cmdData = cmdData;
    }
}
