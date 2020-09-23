package com.jiashunx.app.masker.proxypool.core.exception;

/**
 * 代理采集异常.
 * @author jiashunx
 * @date 2020/09/23
 */
public class MProxyCollectorException extends MProxyException {

    public MProxyCollectorException() {
        super();
    }

    public MProxyCollectorException(String message) {
        super(message);
    }

    public MProxyCollectorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MProxyCollectorException(Throwable throwable) {
        super(throwable);
    }

}
