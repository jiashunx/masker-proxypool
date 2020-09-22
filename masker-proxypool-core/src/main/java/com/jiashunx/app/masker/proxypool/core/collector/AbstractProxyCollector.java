package com.jiashunx.app.masker.proxypool.core.collector;

import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import com.jiashunx.app.masker.proxypool.core.util.MHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiashunx
 * @date 2020/09/16
 */
public abstract class AbstractProxyCollector implements IProxyCollector {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProxyCollector.class);

    @Override
    public void register() {
        MProxySourceType sourceType = getProxySourceType();
        MHelper.COLLECTOR_MAP.put(sourceType, this);
        if (logger.isInfoEnabled()) {
            logger.info("register proxy collector, sourceType={}", sourceType);
        }
    }

}
