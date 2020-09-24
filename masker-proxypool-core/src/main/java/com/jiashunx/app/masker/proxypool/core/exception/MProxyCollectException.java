package com.jiashunx.app.masker.proxypool.core.exception;

/**
 * 代理采集异常.
 * @author jiashunx
 * @date 2020/09/23
 */
public class MProxyCollectException extends MProxyException {

    public MProxyCollectException() {
        super();
    }

    public MProxyCollectException(String message) {
        super(message);
    }

    public MProxyCollectException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MProxyCollectException(Throwable throwable) {
        super(throwable);
    }

}
