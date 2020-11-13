package io.github.jiashunx.masker.proxypool.core.model;

import io.github.jiashunx.masker.proxypool.core.type.MProxySourceType;
import io.github.jiashunx.masker.proxypool.core.type.MProxyType;

import java.util.Objects;

/**
 * 代理模型.
 * @author jiashunx
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
     * 是否有效.
     */
    private boolean valid = true;

    public MProxy() {}

    public MProxy(boolean valid) {
        this.valid = valid;
    }

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
                ", valid=" + valid +
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
