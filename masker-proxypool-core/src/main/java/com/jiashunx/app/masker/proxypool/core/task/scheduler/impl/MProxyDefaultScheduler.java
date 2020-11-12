package com.jiashunx.app.masker.proxypool.core.task.scheduler.impl;

import com.jiashunx.app.masker.proxypool.core.annotation.MScheduler;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyScheduleException;
import com.jiashunx.app.masker.proxypool.core.model.MProxy;
import com.jiashunx.app.masker.proxypool.core.model.MProxyPool;
import com.jiashunx.app.masker.proxypool.core.task.collector.AbstractMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.task.scheduler.AbstractMProxyScheduler;
import com.jiashunx.app.masker.proxypool.core.type.MProxyType;
import com.jiashunx.app.masker.proxypool.core.util.MProxyHelper;
import com.jiashunx.app.masker.proxypool.core.util.MProxyPoolHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author jiashunx
 */
@MScheduler
public class MProxyDefaultScheduler extends AbstractMProxyScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MProxyDefaultScheduler.class);

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void execute() throws MProxyScheduleException, ExecutionException, InterruptedException {
        Map<String, AbstractMProxyCollector> collectorMap = applicationContext.getBeansOfType(AbstractMProxyCollector.class);
        List<Future<List<MProxy>>> futures = new ArrayList<>(collectorMap.size());
        for (Map.Entry<String, AbstractMProxyCollector> entry: collectorMap.entrySet()) {
            AbstractMProxyCollector task = entry.getValue();
            futures.add(MProxyHelper.threadPoolExecutor.submit(task));
            if (logger.isInfoEnabled()) {
                logger.info("submit proxy collector task, proxySourceType: {}", task.getProxySourceType());
            }
        }
        List<MProxy> proxyList = new ArrayList<>();
        for (Future<List<MProxy>> future: futures) {
            proxyList.addAll(future.get());
        }
        if (logger.isInfoEnabled()) {
            logger.info("new proxy count: {}", proxyList.size());
        }
        Map<MProxyType, List<MProxy>> proxyMap = new HashMap<>();
        proxyList.forEach(proxy -> {
            if (proxy == null || proxy.getProxyType() == null) {
                return;
            }
            List<MProxy> list = proxyMap.computeIfAbsent(proxy.getProxyType(), k -> new ArrayList<>());
            list.add(proxy);
        });
        proxyMap.forEach((proxyType, list) -> {
            MProxyPoolHolder.setProxyPool(proxyType, new MProxyPool(proxyType, list));
        });
    }

    @Override
    public String getProxyScheduleType() {
        return "proxy-default-scheduler";
    }
}
