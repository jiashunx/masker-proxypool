package com.jiashunx.app.masker.proxypool.core.model;

import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;

import java.util.Objects;

/**
 * 代理模型.
 * @author jiashunx
 * @date 2020/09/16
 */
public class MProxy {

    /**
     * 代理数据源类型.
     */
    private MProxySourceType sourceType;
    /**
     * 代理类型.
     */
    private MProxyType proxyType;
    /**
     * 代理ip.
     */
    private String ip;
    /**
     * 代理端口.
     */
    private int port;
    /**
     * 代理所属国.
     */
    private String country;
    /**
     * 代理位置.
     */
    private String location;
    /**
     * 代理运营商.
     */
    private String isp;

    public MProxy() {}

    public MProxy(MProxySourceType sourceType, MProxyType proxyType, String ip, int port) {
        this.sourceType = sourceType;
        this.proxyType = proxyType;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return "MProxy{" +
                "sourceType=" + sourceType +
                ", proxyType=" + proxyType +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", isp='" + isp + '\'' +
                '}';
    }

    public MProxySourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(MProxySourceType sourceType) {
        this.sourceType = sourceType;
    }

    public MProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(MProxyType proxyType) {
        this.proxyType = Objects.requireNonNull(proxyType);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = Objects.requireNonNull(ip);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
