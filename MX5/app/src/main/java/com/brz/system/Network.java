package com.brz.system;

/**
 * Created by macro on 16/4/11.
 */
public class Network {
    private String model;
    private String ip;
    private String mask;
    private String gw;
    private String dns;
    private String m3g;
    private String ssid;
    private String password;
    private String proxyserver;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getM3g() {
        return m3g;
    }

    public void setM3g(String m3g) {
        this.m3g = m3g;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProxyserver() {
        return proxyserver;
    }

    public void setProxyserver(String proxyserver) {
        this.proxyserver = proxyserver;
    }
}
