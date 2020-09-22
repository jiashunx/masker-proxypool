package com.jiashunx.app.masker.proxypool.core.collector;

import com.jiashunx.app.masker.proxypool.core.type.MProxySourceType;
import com.jiashunx.app.masker.proxypool.core.util.MHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 代理采集调度.
 * @author jiashunx
 * @date 2020/09/20
 */
public class MProxyCollectScheduler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MProxyCollectScheduler.class);

    @Override
    public void run() {
        if (logger.isInfoEnabled()) {
            logger.info("采集开始");
        }
        Map<MProxySourceType, IProxyCollector> collectorMap =  MHelper.COLLECTOR_MAP;
        for (Map.Entry<MProxySourceType, IProxyCollector> entry: collectorMap.entrySet()) {
            if (logger.isInfoEnabled()) {
                logger.info("开始采集: {}", entry.getKey());
            }
        }
    }
}
