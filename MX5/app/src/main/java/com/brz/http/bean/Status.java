package com.brz.http.bean;

/**
 * Created by macro on 16/5/16.
 */
public class Status {
    private String systemState;
    private Hardware hardware;
    private DiskInfo diskInfo;
    private String downloadStatue;
    private Transmission transmission;

    public String getSystemState() {
        return systemState;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public DiskInfo getDiskInfo() {
        return diskInfo;
    }

    public void setDiskInfo(DiskInfo diskInfo) {
        this.diskInfo = diskInfo;
    }

    public String getDownloadStatue() {
        return downloadStatue;
    }

    public void setDownloadStatue(String downloadStatue) {
        this.downloadStatue = downloadStatue;
    }

    public void setSystemState(String systemState) {
        this.systemState = systemState;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
}
