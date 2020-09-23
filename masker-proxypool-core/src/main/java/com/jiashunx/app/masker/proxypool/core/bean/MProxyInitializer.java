package com.jiashunx.app.masker.proxypool.core.bean;

import com.jiashunx.app.masker.proxypool.core.collector.IMProxyCollector;
import com.jiashunx.app.masker.proxypool.core.collector.MProxyCollectScheduler;
import com.jiashunx.app.masker.proxypool.core.exception.MProxyInitializeException;
import com.jiashunx.app.masker.proxypool.core.util.MDefaultThreadFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiashunx
 * @date 2020/09/23
 */
@Component
public class MProxyInitializer {

    @Resource
    private ApplicationContext applicationContext;

    private ThreadPoolExecutor threadPoolExecutor = null;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = null;

    private boolean initialized = false;

    public synchronized void init() {
        if (initialized) {
            throw new MProxyInitializeException("masker-proxypool has initialized.");
        }
        initialized = true;
        System.out.println(applicationContext);
        Map<String, IMProxyCollector> collectorMap = applicationContext.getBeansOfType(IMProxyCollector.class);
        for (Map.Entry<String, IMProxyCollector> entry: collectorMap.entrySet()) {
            IMProxyCollector collector = entry.getValue();
            collector.register();
        }
        int poolSize = collectorMap.size() + 1;
        threadPoolExecutor = new ThreadPoolExecutor(1, 5, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new MDefaultThreadFactory());
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new MDefaultThreadFactory());
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new MProxyCollectScheduler(), 10L, 60L, TimeUnit.SECONDS);
        // 创建采集线程池
        // 定时触发采集任务，多线程并发采集数据，然后合并采集结果。
        //
    }

}
