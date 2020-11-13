package io.github.jiashunx.masker.proxypool.core.exception;

/**
 * 异常基类.
 * @author jiashunx
 */
public class MProxyException extends RuntimeException {

    public MProxyException() {
        super();
    }

    public MProxyException(String message) {
        super(message);
    }

    public MProxyException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MProxyException(Throwable throwable) {
        super(throwable);
    }

}
