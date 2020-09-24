package com.jiashunx.app.masker.proxypool.core.task.collector;

import com.jiashunx.app.masker.proxypool.core.bean.MProxyInitializer;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyException;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyScheduleException;
import com.jiashunx.app.masker.proxypool.core.task.IMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代理采集抽象类.
 * @author jiashunx
 * @date 2020/09/16
 */
public abstract class AbstractMProxyCollector<T> implements IMProxyCollector<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMProxyCollector.class);

    @Override
    public T call() {
        if (!MProxyInitializer.isInitialized()) {
            throw new MProxyScheduleException("masker-proxypool doesn't initialized.");
        }
        MProxySourceType sourceType = getProxySourceType();
        if (logger.isInfoEnabled()) {
            logger.info("proxy collector start, proxySourceType: {}", sourceType);
        }
        try {
            return get();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("proxy collector failed, proxySourceType: {}", sourceType, e);
            }
        }
        return null;
    }

    /**
     * 代理采集返回结果.
     * @return
     * @throws MProxyException
     */
    protected abstract T get() throws MProxyException;

}
