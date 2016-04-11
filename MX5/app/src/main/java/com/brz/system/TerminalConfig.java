package com.brz.system;

import java.util.logging.Logger;

/**
 * Created by macro on 16/4/11.
 */
public class TerminalConfig {
    private Logger mLogger = Logger.getLogger(TerminalConfig.class.getSimpleName());

    private String termId;
    private String corpId;
    private String workId;
    private Network network;
    private String httpServer;
    private String fileServer;
    private String lanServer;
    private WorkingTime workingTime;
    private VolumeTime volumeTime;
    private DownloadTime downloadTime;
    private String asp;
    private String port;
    private String resolution;
    private String screenAngle;
    private String rate;
    private String storageType;
    private String language;
    private String remotecontrol;
    private String baudrate;
    private String videoSync;
    private String videoMaster;
    private String screenDurtion;
    private String reboot;

    @Override
    public String toString() {
        return "termId: " + termId +
                " corpId: " + corpId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getHttpServer() {
        return httpServer;
    }

    public void setHttpServer(String httpServer) {
        this.httpServer = httpServer;
    }

    public String getFileServer() {
        return fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public String getLanServer() {
        return lanServer;
    }

    public void setLanServer(String lanServer) {
        this.lanServer = lanServer;
    }

    public WorkingTime getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(WorkingTime workingTime) {
        this.workingTime = workingTime;
    }

    public VolumeTime getVolumeTime() {
        return volumeTime;
    }

    public void setVolumeTime(VolumeTime volumeTime) {
        this.volumeTime = volumeTime;
    }

    public DownloadTime getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(DownloadTime downloadTime) {
        this.downloadTime = downloadTime;
    }

    public String getAsp() {
        return asp;
    }

    public void setAsp(String asp) {
        this.asp = asp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getScreenAngle() {
        return screenAngle;
    }

    public void setScreenAngle(String screenAngle) {
        this.screenAngle = screenAngle;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRemotecontrol() {
        return remotecontrol;
    }

    public void setRemotecontrol(String remotecontrol) {
        this.remotecontrol = remotecontrol;
    }

    public String getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    public String getVideoSync() {
        return videoSync;
    }

    public void setVideoSync(String videoSync) {
        this.videoSync = videoSync;
    }

    public String getVideoMaster() {
        return videoMaster;
    }

    public void setVideoMaster(String videoMaster) {
        this.videoMaster = videoMaster;
    }

    public String getScreenDurtion() {
        return screenDurtion;
    }

    public void setScreenDurtion(String screenDurtion) {
        this.screenDurtion = screenDurtion;
    }

    public String getReboot() {
        return reboot;
    }

    public void setReboot(String reboot) {
        this.reboot = reboot;
    }
}
